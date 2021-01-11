package tech.sunkey.bilibili.ws.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 1:40 下午
 **/
public class BiliWsEncoder extends MessageToMessageEncoder<BiliWsPackage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BiliWsPackage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        Protocol.write(msg, buffer);
        BinaryWebSocketFrame frame = new BinaryWebSocketFrame(buffer);
        out.add(frame);
    }

}
