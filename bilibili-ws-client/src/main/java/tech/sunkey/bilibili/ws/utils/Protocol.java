package tech.sunkey.bilibili.ws.utils;

import io.netty.buffer.ByteBuf;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-09 4:59 下午
 **/
public class Protocol implements Constants {

    public static List<BiliWsPackage> read(ByteBuf in) {
        return read(DataView.fromByteBuf(in));
    }

    public static List<BiliWsPackage> read(DataView buffer) {
        LinkedList<BiliWsPackage> result = new LinkedList<>();
        collect(buffer, result);
        return result;
    }

    private static void collect(DataView buffer, List<BiliWsPackage> list) {
        BiliWsPackage pkg = parse(0, buffer);
        if (pkg.getVersion() == WS_BODY_PROTOCOL_VERSION_DEFLATE) {
            byte[] array = buffer.slice(pkg.getHeaderLength()).array();
            DataView inflateBuffer = DataView.of(InflateUtils.inflate(array));
            int offset = 0;
            while (offset < inflateBuffer.size()) {
                BiliWsPackage subPkg = parse(offset, inflateBuffer);
                list.add(subPkg);
                offset += subPkg.getPackageLength();
            }
        } else {
            list.add(pkg);
        }
    }

    public static byte[] processPackage(BiliWsPackage pkg) {
        pkg.setHeaderLength(16);
        pkg.setVersion(1);
        pkg.setSequence(1);
        int len = pkg.getHeaderLength();
        byte[] data = null;
        if (pkg.getData() != null) {
            data = pkg.getData().getBytes();
            len += data.length;
        }
        pkg.setPackageLength(len);
        return data;
    }

    public static void write(BiliWsPackage pkg, ByteBuf bb) {
        byte[] data = processPackage(pkg);
        bb.writeInt(pkg.getPackageLength());
        bb.writeShort(pkg.getHeaderLength());
        bb.writeShort(pkg.getVersion());
        bb.writeInt(pkg.getOperation());
        bb.writeInt(pkg.getSequence());
        if (data != null) {
            bb.writeBytes(data);
        }
    }

    private static BiliWsPackage parse(int offset, DataView buffer) {
        BiliWsPackage pkg = new BiliWsPackage();
        pkg.setPackageLength(buffer.getInt(offset + WS_PACKAGE_OFFSET));
        pkg.setHeaderLength(buffer.getShort(offset + WS_HEADER_OFFSET));
        pkg.setVersion(buffer.getShort(offset + WS_VERSION_OFFSET));
        pkg.setOperation(buffer.getInt(offset + WS_OPERATION_OFFSET));
        pkg.setSequence(buffer.getInt(offset + WS_SEQUENCE_OFFSET));
        if (pkg.getVersion() == WS_BODY_PROTOCOL_VERSION_NORMAL) {
            DataView dataBuffer =
                    buffer.slice(offset + pkg.getHeaderLength(),
                            offset + pkg.getPackageLength() - 1);
            pkg.setData(dataBuffer.string());
        }
        return pkg;
    }

}
