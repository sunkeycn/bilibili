package tech.sunkey.bilibili.ws.utils;

import io.netty.buffer.ByteBuf;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Sunkey
 * @since 2021-01-08 8:24 下午
 **/
public class DataView {

    private final byte[] data;
    private final int start, end;

    public int start() {
        return this.start;
    }

    public int end() {
        return this.end;
    }

    public short getShort(int index) {
        return makeShort(getByte(index),
                getByte(index + 1));
    }

    public int getInt(int index) {
        return makeInt(getByte(index),
                getByte(index + 1),
                getByte(index + 2),
                getByte(index + 3));
    }

    public int size() {
        return this.end - this.start + 1;
    }

    public byte[] source() {
        return this.data;
    }

    public byte[] array() {
        byte[] res = new byte[size()];
        System.arraycopy(this.data, this.start, res, 0, res.length);
        return res;
    }

    public InputStream stream() {
        return new StreamImpl();
    }

    public String base64() {
        return Base64.getEncoder().encodeToString(array());
    }

    public String string(Charset charset) {
        return new String(array(), charset);
    }

    public String string() {
        return string(StandardCharsets.UTF_8);
    }

    public byte getByte(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int idx = index + this.start;
        if (idx > this.end) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[idx];
    }

    public DataView slice(int start) {
        return slice(start, this.end);
    }

    public DataView slice(int start, int end) {
        if (start < this.start || end > this.end || start > end) {
            throw new IndexOutOfBoundsException();
        }
        return new DataView(this.data, start, end);
    }

    private DataView(byte[] data, int start, int end) {
        if (data == null) {
            throw new NullPointerException("data");
        }
        if (start < 0 || start >= data.length) {
            throw new IndexOutOfBoundsException("start");
        }
        if (end < 0 || end >= data.length || end < start) {
            throw new IndexOutOfBoundsException("end");
        }
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public static DataView of(byte[] data) {
        return new DataView(data, 0, data.length - 1);
    }

    public static DataView fromBase64(String base64) {
        return of(Base64.getDecoder().decode(base64));
    }

    public static DataView fromByteArray(ByteArray array) {
        return of(array.flip());
    }

    public static DataView fromStream(InputStream in) {
        return fromByteArray(new ByteArray().write(in));
    }

    public static DataView fromByteBuf(ByteBuf bb) {
        byte[] data = new byte[bb.readableBytes()];
        bb.readBytes(data);
        return of(data);
    }

    private static short makeShort(byte b1, byte b0) {
        return (short) ((b1 << 8) | (b0 & 0xff));
    }

    private static int makeInt(byte b3, byte b2, byte b1, byte b0) {
        return (b3 << 24) |
                ((b2 & 0xff) << 16) |
                ((b1 & 0xff) << 8) |
                ((b0 & 0xff));
    }

    class StreamImpl extends InputStream {
        int pos = start;

        @Override
        public int read() {
            if (pos < end) {
                return data[pos++];
            }
            return -1;
        }
    }

}
