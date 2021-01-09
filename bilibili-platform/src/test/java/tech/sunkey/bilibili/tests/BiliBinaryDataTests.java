package tech.sunkey.bilibili.tests;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.sunkey.bilibili.ws.utils.DataView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.Inflater;

/**
 * @author Sunkey
 * @since 2021-01-08 12:54 下午
 **/
public class BiliBinaryDataTests {

    static final int WS_PACKAGE_HEADER_TOTAL_LENGTH = 16;

    static final int WS_PACKAGE_OFFSET = 0;
    static final int WS_HEADER_OFFSET = 4;
    static final int WS_VERSION_OFFSET = 6;
    static final int WS_OPERATION_OFFSET = 8;
    static final int WS_SEQUENCE_OFFSET = 12;

    static final int WS_AUTH_OK = 0;
    static final int WS_AUTH_TOKEN_ERROR = -101;

    static final int WS_BODY_PROTOCOL_VERSION_NORMAL = 0;
    static final int WS_BODY_PROTOCOL_VERSION_DEFLATE = 2;

    static final int WS_HEADER_DEFAULT_OPERATION = 1;
    static final int WS_HEADER_DEFAULT_SEQUENCE = 1;
    static final int WS_HEADER_DEFAULT_VERSION = 1;

    static final int WS_OP_HEARTBEAT = 2;
    static final int WS_OP_HEARTBEAT_REPLY = 3;
    static final int WS_OP_MESSAGE = 5;
    static final int WS_OP_USER_AUTHENTICATION = 7;
    static final int WS_OP_CONNECT_SUCCESS = 8;

    // PackageLen size=4 offset=0  value=
    // HeaderLen  size=2 offset=4  value=16
    // ProtoVers  size=2 offset=6  value=1
    // Operation  size=4 offset=8  value=7
    // SeqId      size=4 offset=12 value=1

    static String binary = "AAAFTgAQAAIAAAAFAAAAAHjavFZPbxTHEvdD7x2flGuudW7h7p7p6ZmRosiAQVZgkcAkB8ca9cz0rDvZ2VnN9BoZ5AOJUAhCwSgJSQQ5WAgkFBFkRP5BEn+Z7LI55StE3bNr7y5rIpQQW1r1VHVXV/3q11U1N3fojbnX5szf/8zPRUjyFEJYaiwvnlk4uhy9c/rMMUCQCi0gvAhdlUJIA+xSzHwE3bbIJYTQv7LV++nxb0+e9H549Ozp9d7j+/0nH8JQHyVFqyghBECgUtnWSitZQbjiILKKIK+akd7oSAgJgrIocnsFIa6LfYxAq1xWWuQdCIlHMMHY4RRBlRSlOeJ5mFDiOczxGIJMtKsol6loGWe1KJtSR8Ye8bnnBwFHYLVRS67LFoTUHQmGgQzu7g4efgEj6dBzwiljhE1Io0qLUh+gk+0UQuYSlwZ4UhMXZSpLCD0fBz5jCFQVtVRzTcvUAtDsijIduecgqDoyUSYci15StG04GIFoJ2tFGY0Acyj2GdkHhmFMCPWcTXtD1SmlqM/Vy0i1s6I2mhRtXaq4q1XRNqg1S5FKCPHm3t5UVkm9VwvViowXRr85N3eIvDx5uEsC7rn75Old/7H/ybXejV8Gu1v/DmcIJi4l1MWYv5gzBGMyRRmD8QRl+nc+HlzdGVzdmWZNQDl1fW8ma2bqLGtmakas2VO+iDX4JVjjOq6/B4xDOKOvljBzb84izOnGyaXGYnRmofFWdPT0ucbyGGmSotvWELquOf6f12cdP7bQOHUuOnX2hAnWurmyghFBlCHicc4pMYshDxyP+4hSxiimmCOMgHOfBTL2AGH7D7CKYLD7Vf/Lh/2Hnw2+u/XHz9f6j7Z7u5cH9y4Nvr0PaMUJMCGcOwGC3qf3BjuXzm+0LlwYGSAYY3O/MbSyilaMLPA9P2AYwbtdjB3JzBZAeBWtGJhg1R5sd1stdBF0NcZbB0GiIQRGOT1CFo7A5v5W+4NXDa7bs4A5ufT2YjR6jWZ9YuHU4hi29ROiY09xPBhoqkyPWL732c0tKzqlSqRd5VXTFM4X4GUz9/+/nzmfY+Qz5gSMEN8kigrfYVS6E5nrX3rQu3m7d/1+76OnJlXYY4xzF8Hg2ve/X94Z3N3tPdjqb994pdni3nHqY75wQLZu/iPZei6gl8rYBE4mRf9Vs7w6u9g4Fp1YOj7+JtNSnLeWmkVrWCRUa90UKIzA3ueYKlxoW8BU23xjjEBmmTTwYARxWYg0EZUeVaakVDrqlEVcG54qaGVSVNrWK58zHqBhL/GI6/kO4weWfIPA0rBGqkwvWwgZgqrbGbprV9EEUrUoFjpZm1SUMhcmmn00iQkrlsJeYiCP1YWoKrqlUcJJtS4BgUjqUgn9q5/3bn5gS6hqDxvYCLr9tK6JTmejd/mb/u0rYNqTNbWmdSecn1f48FpateLDSZHPx1k1b9TzLuZexgORxizgmZ/FNHYSwkkWp56TiIzFbkaI4x1+r9M0PtrIkiKPC4u+8bs0vQcIdUjg+4HrDtnTGDLt1zv9r7et4+ZQZTuV5fOEsXG5Fs1I5aI5pKIuOlFLmRzWarM1ykWlTRpqkaqiTJVmiy67EkEqc1EmosaO7t2txUZksm3Ho1o2zjTb/m0sezQgFHPzgh0YUTCKW0XyvqWFbXa2W9YZr8lozZayzmRVjwMIctFsq0yNfCIIpmeVumnXpWxymHAd7Hsu++ve3N1/v1PdGk+PIs7UKPLs7pXe1q3nplfqch8fMIiYGZFSNmsSma0ajSL72vFZ5Lmna4aC9aYYAY43N/8MAAD//yJ26Ow=";

    public static byte[] uncompress(byte[] inputByte) throws IOException {
        int len = 0;
        Inflater infl = new Inflater();
        infl.setInput(inputByte);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] outByte = new byte[1024];
        try {
            while (!infl.finished()) {
                // 解压缩并将解压缩后的内容输出到字节输出流bos中
                len = infl.inflate(outByte);
                if (len == 0) {
                    break;
                }
                bos.write(outByte, 0, len);
            }
            infl.end();
        } catch (Exception e) {
            //
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        DataView bb = DataView.fromBase64(binary);
        WsPkg pkg = c(0, bb);
        System.out.println(pkg);


        System.out.println(bb.size());

        byte[] array = bb.slice(pkg.headerLen).array();
        System.out.println("slice:" + array.length);
        byte[] unc = uncompress(array);
        System.out.println("unc:" + unc.length);
        DataView uncbb = DataView.of(unc);

        int offset = 0;
        while (offset < uncbb.size()) {
            WsPkg uncP = c(offset, uncbb);
            System.out.println(uncP);

            offset += uncP.packetLen;
        }


    }

    public static WsPkg c(int offset, DataView bb) {
        WsPkg pkg = new WsPkg();
        pkg.packetLen = bb.getInt(offset + WS_PACKAGE_OFFSET);
        pkg.headerLen = bb.getShort(offset + WS_HEADER_OFFSET);
        pkg.ver = bb.getShort(offset + WS_VERSION_OFFSET);
        pkg.op = bb.getInt(offset + WS_OPERATION_OFFSET);
        pkg.seq = bb.getInt(offset + WS_SEQUENCE_OFFSET);
        if (pkg.ver == 0) {
            pkg.data = bb.slice(offset + pkg.headerLen, offset + pkg.packetLen - 1).string();
        }
        return pkg;
    }

    public static void print(DataView bb) {
        int pkgLen = bb.getInt(WS_PACKAGE_OFFSET);
        int headerLen = bb.getShort(WS_HEADER_OFFSET);
        int ver = bb.getShort(WS_VERSION_OFFSET);
        int op = bb.getInt(WS_OPERATION_OFFSET);
        int seq = bb.getInt(WS_SEQUENCE_OFFSET);
        System.out.printf("[pkgLen=%s,headerLen=%s,ver=%s,op=%s,seq=%s]\n", pkgLen, headerLen, ver, op, seq);

    }

    public static WsPkg convertToObject(ByteBuf bb) {
        int len = bb.readableBytes();
        int offset = 0;
        int pkgLen;
        for (; offset < len; offset += pkgLen) {
            pkgLen = bb.getInt(offset + WS_PACKAGE_OFFSET);
            int headerLen = bb.getShort(WS_HEADER_OFFSET);
            int ver = bb.getShort(WS_VERSION_OFFSET);
            int op = bb.getInt(WS_OPERATION_OFFSET);
            int seq = bb.getInt(WS_SEQUENCE_OFFSET);
            System.out.printf("[pkgLen=%s,headerLen=%s,ver=%s,op=%s,seq=%s]\n", pkgLen, headerLen, ver, op, seq);
            if (ver == WS_BODY_PROTOCOL_VERSION_DEFLATE) {
                InputStream in;
                ByteBuf slice = bb.slice(offset + headerLen, pkgLen - headerLen);
            } else {
                ByteBuf data = bb.slice(offset + headerLen, pkgLen - headerLen);
                System.out.println(data.toString(StandardCharsets.UTF_8));
            }
        }

        return null;
    }


    @Getter
    @Setter
    @ToString
    public static class WsPkg {
        int packetLen;
        int headerLen;
        int ver;
        int op;
        int seq;
        String data;
    }

}
