package tech.sunkey.bilibili.ws.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.Operation;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 11:52 上午
 **/

@Slf4j
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

    protected void handleHandshake() {
        log.info("[WebSocket] Send UserAuth.");
        client.send(Protocol.userAuth(client.getConfig().getUserAuth()).flip());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ctx.channel(), ((FullHttpResponse) msg));
                handleHandshake();
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
            handleMessage(pkg);
        }
    }

    protected void handleMessage(BiliWsPackage pkg) {
        log.info("[WebSocket] Recv Message: {}", pkg);
        switch (Operation.valueOf(pkg.getOperation())) {
            case Message:
                handler.message(client, pkg);
                break;
            case ConnectSuccess:
                client.startHeartBeatTask(0);
                break;
            case HeartBeatReply:
                handler.connected(client);
                break;
            default:
                log.info("[WebSocket] not handler for opcode={}", pkg.getOperation());
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
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame));
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        handler.disconnected(client);
    }

}
