package tech.sunkey.bilibili.ws.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.concurrent.TimeUnit;

/**
 * @author Sunkey
 * @since 2021-01-09 5:23 下午
 **/

@Slf4j
public class WsClient {

    protected Channel channel;
    private boolean heartbeat = false;
    private final SslContext sslCtx = buildSslContext();
    @Getter
    private Config config;

    @SneakyThrows
    private SslContext buildSslContext() {
        return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    }

    protected DefaultHttpHeaders createHttpHeaders(Config config) {
        return new DefaultHttpHeaders();
    }

    protected WebSocketClientHandshaker createHandShaker(Config config) {
        return WebSocketClientHandshakerFactory.newHandshaker(config.getURI(),
                WebSocketVersion.V13,
                null,
                true,
                createHttpHeaders(config));
    }

    public Channel channel() {
        return channel;
    }

    protected void initChannel(SocketChannel channel, Config config, ClientHandler handler) {
        WebSocketClientHandshaker handshaker = createHandShaker(config);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(sslCtx.newHandler(channel.alloc()));
        pipeline.addLast(new HttpClientCodec());
        if (config.getLogLevel() != null) {
            pipeline.addLast(new LoggingHandler(config.getLogLevel()));
        }
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(WebSocketClientCompressionHandler.INSTANCE);
        pipeline.addLast(new BiliWsEncoder());
        pipeline.addLast(new BusinessHandler(this, handshaker, handler));
    }

    @SneakyThrows
    public Channel connect(Config config) {
        log.info("Prepare connect : {}", config.getUrl());
        this.config = config;
        NioEventLoopGroup group = new NioEventLoopGroup();
        this.channel = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        WsClient.this.initChannel(channel, config, config.getHandler());
                    }
                })
                .connect(config.getHost(), config.getPort())
                .addListener(a -> {
                    log.info("connect {} success.", config.getUrl());
                }).channel();
        channel.closeFuture().addListener(a -> {
            group.shutdownGracefully();
        });
        return channel;
    }

    public ScheduledFuture delay(Runnable task, int delaySec) {
        return channel.eventLoop().schedule(task, delaySec, TimeUnit.SECONDS);
    }

    public ScheduledFuture schedule(Runnable task, int delaySec, int periodSec) {
        return channel.eventLoop().scheduleAtFixedRate(task, delaySec, periodSec, TimeUnit.SECONDS);
    }

    public synchronized void startHeartBeatTask(int initDelaySeconds) {
        if (heartbeat) {
            return;
        }
        heartbeat = true;

        schedule(() -> send(Protocol.heartBeat().flip()), initDelaySeconds, 30);
    }

    public synchronized ChannelFuture send(BiliWsPackage message) {
        if (this.channel == null) {
            throw new IllegalStateException();
        }
        return channel.writeAndFlush(message);
    }

}
