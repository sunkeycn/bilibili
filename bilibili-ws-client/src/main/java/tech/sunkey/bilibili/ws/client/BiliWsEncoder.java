package tech.sunkey.bilibili.ws.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.Protocol;

/**
 * @author Sunkey
 * @since 2021-01-11 1:40 下午
 **/
public class BiliWsEncoder extends MessageToByteEncoder<BiliWsPackage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BiliWsPackage msg, ByteBuf out) throws Exception {
        Protocol.write(msg, out);
    }

}
