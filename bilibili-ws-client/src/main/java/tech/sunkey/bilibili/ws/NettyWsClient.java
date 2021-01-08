package tech.sunkey.bilibili.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @author Sunkey
 * @since 2021-01-08 1:44 下午
 **/
@Slf4j
public class NettyWsClient {

    protected WebSocketClientHandshaker createHandshaker(URI uri) {
        DefaultHttpHeaders headers = new DefaultHttpHeaders();
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Accept-Language", "zh-CN,zh;q=0.9");
        headers.set("Cache-Control", "no-cache");
        //headers.set("Connection", "Upgrade");
        headers.set("Host", uri.getHost());
        headers.set("Origin", "https://live.bilibili.com");
        headers.set("Pragma", "no-cache");
        headers.set("Sec-WebSocket-Extensions", "permessage-deflate; client_max_window_bits");
        //headers.set("Sec-WebSocket-Key", "cpx8FCski16lqwGSq88V3A==");
        //headers.set("Sec-WebSocket-Version", "13");
        //headers.set("Upgrade", "websocket");
        headers.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        return WebSocketClientHandshakerFactory.newHandshaker(uri,
                WebSocketVersion.V13,
                null,
                true,
                headers);
    }

    public void connect(URI uri) {
        WebSocketClientHandshaker handshaker = createHandshaker(uri);
        new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new HttpClientCodec())
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new NettyWsHandler(handshaker));
                    }
                })
                .connect(uri.getHost(), uri.getPort())
                .addListener(a -> {
                    log.info("connect {} success.", uri);
                });
    }

    @RequiredArgsConstructor
    public class NettyWsHandler extends SimpleChannelInboundHandler<String> {

        @NonNull
        private final WebSocketClientHandshaker handshaker;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            handshaker.handshake(ctx.channel());
            System.out.println("active");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String s)
                throws Exception {
            System.out.println("read");
            System.out.println(s);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("closed");
        }
    }

}
