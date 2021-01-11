package tech.sunkey.bilibili.ws.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.DataView;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.Base64;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-09 5:38 下午
 **/
@Slf4j
public class BiliWsDecoder extends MessageToMessageDecoder<String> {

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.addAll(Protocol.read(DataView.fromBase64(msg)));
    }

}
