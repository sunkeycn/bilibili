package tech.sunkey.bilibili.ws.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.base64.Base64Decoder;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.ScheduledFuture;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.URI;
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

    protected void initChannel(SocketChannel channel, Config config) throws Exception {
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
        pipeline.addLast(new BusinessHandler(this, handshaker, config.getHandler()));
    }

    @SneakyThrows
    public Channel connect(Config config) {
        log.info("Prepare connect : {}", config.getUrl());
        NioEventLoopGroup group = new NioEventLoopGroup();
        this.channel = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        WsClient.this.initChannel(channel, config);
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

    public synchronized void send(BiliWsPackage message) {
        if (this.channel == null) {
            throw new IllegalStateException();
        }
        channel.writeAndFlush(message);
    }

    @ToString
    public static class Config {

        @Getter
        private ClientHandler handler;
        @Getter
        private String url;
        private String host;
        private int port;
        private URI uri;
        @Getter
        private LogLevel logLevel;

        public Config logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Config handler(ClientHandler handler) {
            this.handler = handler;
            return this;
        }

        public Config url(String url) {
            this.url = url;
            return this;
        }

        public Config host(String host) {
            this.host = host;
            return this;
        }

        public Config port(int port) {
            this.port = port;
            return this;
        }

        @SneakyThrows
        public URI getURI() {
            if (uri == null) {
                uri = new URI(url);
            }
            return uri;
        }

        public String getHost() {
            if (host == null) {
                host = getURI().getHost();
            }
            return host;
        }

        public int getPort() {
            if (port == 0) {
                port = _getPort();
            }
            return port;
        }

        private int _getPort() {
            URI uri = getURI();
            int port = uri.getPort();
            if (port == -1) {
                if ("wss".equals(uri.getScheme()) || "https".equals(uri.getScheme())) {
                    return 443;
                }
                return 80;
            }
            return port;
        }

    }

}
