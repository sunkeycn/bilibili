package tech.sunkey.bilibili.ws.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.DataView;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-09 5:38 下午
 **/
public class BiliWsCodec extends ByteToMessageCodec<BiliWsPackage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BiliWsPackage msg, ByteBuf out) throws Exception {
        Protocol.write(msg, out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.addAll(Protocol.read(in));
    }

}
