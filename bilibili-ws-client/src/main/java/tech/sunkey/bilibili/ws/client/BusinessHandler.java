package tech.sunkey.bilibili.ws.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 11:52 上午
 **/
@RequiredArgsConstructor
public class BusinessHandler extends SimpleChannelInboundHandler<Object> {

    @NonNull
    private final WsClient client;
    @NonNull
    private final WebSocketClientHandshaker handshaker;
    @NonNull
    private final ClientHandler handler;

    private ChannelPromise handshakePromise;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakePromise = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ctx.channel(), ((FullHttpResponse) msg));
                handler.connected(client);
                handshakePromise.setSuccess();
            } catch (WebSocketHandshakeException ex) {
                handshakePromise.setFailure(ex);
            }
        }
        if (msg instanceof WebSocketFrame) {
            readWebSocketFrame(ctx, ((WebSocketFrame) msg));
        }
    }

    protected void readBinaryWebSocketFrame(BinaryWebSocketFrame frame) {
        List<BiliWsPackage> list = Protocol.read(frame.content());
        for (BiliWsPackage pkg : list) {
            handler.message(client, pkg);
        }
    }

    protected void readTextWebSocketFrame(TextWebSocketFrame frame) {
        System.out.println(frame.text());
    }

    protected void readWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof BinaryWebSocketFrame) {
            readBinaryWebSocketFrame(((BinaryWebSocketFrame) frame));
        } else if (frame instanceof TextWebSocketFrame) {
            readTextWebSocketFrame(((TextWebSocketFrame) frame));
        } else if (frame instanceof CloseWebSocketFrame) {
            ctx.channel().close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        handler.disconnected(client);
    }

}
