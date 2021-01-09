package tech.sunkey.bilibili.ws.utils;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Sunkey
 * @since 2021-01-08 10:44 下午
 **/
public class ByteArray {

    private final int bufSize;
    private LinkedList<byte[]> list = new LinkedList<>();
    private int size;

    public ByteArray() {
        this(256);
    }

    public byte[] flip() {
        byte[] res = new byte[size];
        int offset = 0;
        for (byte[] bytes : list) {
            System.arraycopy(bytes, 0, res, offset, bytes.length);
            offset += bytes.length;
        }
        return res;
    }

    public int size() {
        return size;
    }

    public ByteArray(int bufSize) {
        this.bufSize = bufSize;
    }

    public ByteArray write(byte[] bytes) {
        return write(bytes, 0, bytes.length);
    }

    public ByteArray write(byte[] bytes, int offset, int len) {
        byte[] tow = new byte[len];
        System.arraycopy(bytes, offset, tow, 0, len);
        list.addLast(tow);
        size += len;
        return this;
    }

    @SneakyThrows
    public ByteArray write(InputStream in) {
        byte[] buf = new byte[bufSize];
        int pos;
        int total = 0;
        while ((pos = in.read(buf)) != -1) {
            list.addLast(Arrays.copyOfRange(buf, 0, pos));
            total += pos;
        }
        size += total;
        return this;
    }

}
