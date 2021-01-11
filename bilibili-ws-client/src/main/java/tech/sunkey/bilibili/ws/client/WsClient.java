package tech.sunkey.bilibili.ws.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.base64.Base64Decoder;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

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

    protected void initChannel(SocketChannel channel, Config config) {
        WebSocketClientHandshaker handshaker = createHandShaker(config);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpClientCodec());
        if (config.getLogLevel() != null) {
            pipeline.addLast(new LoggingHandler(config.getLogLevel()));
        }
        pipeline.addLast(new Base64Decoder());
        pipeline.addLast(new BiliWsDecoder());
        pipeline.addLast(new BiliWsEncoder());
        pipeline.addLast(new HandlerImpl(handshaker, config.getHandler()));
    }

    @SneakyThrows
    public void connect(Config config) {
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
        channel.closeFuture().sync();
        group.shutdownGracefully();
    }

    public synchronized void startHeartBeatTask(int initDelaySeconds) {
        if (heartbeat) {
            return;
        }
        heartbeat = true;

        channel.eventLoop().scheduleAtFixedRate(() -> {
            send(Protocol.heartBeat());
        }, initDelaySeconds, 30, TimeUnit.SECONDS);
    }

    public synchronized void send(BiliWsPackage message) {
        if (this.channel == null) {
            throw new IllegalStateException();
        }
        channel.writeAndFlush(message);
    }

    @RequiredArgsConstructor
    class HandlerImpl extends SimpleChannelInboundHandler<Object> {

        @NonNull
        private final WebSocketClientHandshaker handshaker;
        @NonNull
        private final ClientHandler handler;

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            handshaker.handshake(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
            if (msg instanceof DefaultHttpResponse) {
                if (((DefaultHttpResponse) msg).status().code() == 101) {
                    handler.connected(WsClient.this);
                }
            } else if (msg instanceof BiliWsPackage) {
                handler.message(WsClient.this, ((BiliWsPackage) msg));
            } else if (msg instanceof LastHttpContent) {
                handler.wsprepared(WsClient.this);
            } else {
                log.info("recv unknown[{}]:{}", msg.getClass(), msg);
            }
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) {
            handler.disconnected(WsClient.this);
        }
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
