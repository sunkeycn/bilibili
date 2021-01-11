package tech.sunkey.bilibili.ws.tests;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.Operation;
import tech.sunkey.bilibili.ws.dto.UserAuth;
import tech.sunkey.bilibili.ws.utils.DataView;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.Base64;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-08 12:54 下午
 **/
public class BiliBinaryDataTests {


    public static void main(String[] args) {
        testGroup();
    }


    public static void testGroup() {
        String s1 = "AAABXgAQAAIAAAAFAAAAAHjaZFCxauswFDUP3vaG9w131mDHTvKi7dF26NJCCHQoRSjSjSOwJSPJhRIytWuhUyj9iU6BflDT/kaRrdCUeDD3nnt07rknSX79Sf4m4fsdfisQtQQK5xezs+n/kxm7upyeAgHJPQe6glZJoPm/fFKMi3RMoNW8RqDw/vb48fy622x3m+3ny0Msnu4hUpgwlbFAAQgoidorr9ABvc5uCNSuZP6uQaAZAWtMHZYMsmKUD/IhAa9qdJ7XDdBslKV5XkxGKQEnjMVDKB0WBBZcO1aj5FWw67kt0bOglxLoYFbhLVYHfbwA9n10mv7omfPc+iMUtTzC5sZK7AWUY5Uqlx57VtlyKw/2uwaFCka7VITRe6Nci6WxbB/F97HpuhN1jUUeB13JlF6YXkcY7a2at14ZHSIoLZfxZeRKdKLneq4qFhaH+forAAD//w24q3A=";
        test("S1", s1);
        String r1 = "AAAAGgAQAAEAAAAIAAAAAXsiY29kZSI6MH0=";
        test("R1", r1);
        String s2 = "AAAAHwAQAAEAAAACAAAAAVtvYmplY3QgT2JqZWN0XQ==";
        test("S2", s2);
        String r2 = "AAAAFAAQAAEAAAADAAAAAQFbrps=";
        test("R2", r2);
    }

    public static void testCompare() {
        String s1 = "AAABDwAQAAEAAAAHAAAAAXsidWlkIjoyNDA4NDExNjYsInJvb21pZCI6MjE0NjMyMzUsInByb3RvdmVyIjoyLCJwbGF0Zm9ybSI6IndlYiIsImNsaWVudHZlciI6IjIuNi4wIiwidHlwZSI6Miwia2V5IjoiU2ZadFNCb0ozZ3BKb3JzNV9zbHhJTFJYTkRUc3JSYTVKS0lLOE1rejI4TnZhVTRJTlBrWk1tYVNyR3g4VVdBYVJQbi1VX2Fzdi1mZDZNcll0NVhSYUs5ckZfZkg4bEptSmZMd2dEcjU1ckJ1VkY2VlMxbmZzMlZDNUpDX2JJLTl0aDdpV1h0SWNLdUIzTVNlNEpaRDM1SnIifQ==";

        ByteBuf buf = Unpooled.copiedBuffer(Base64.getDecoder().decode(s1));
        StringBuilder sb = new StringBuilder();
        ByteBufUtil.appendPrettyHexDump(sb, buf);
        System.out.println(sb.toString());

        UserAuth ua = new UserAuth();
        ua.setUid(240841166);
        ua.setRoomid(21463235);
        ua.setProtover(2);
        ua.setPlatform("web");
        ua.setClientver("2.6.0");
        ua.setType(2);
        ua.setKey("SfZtSBoJ3gpJors5_slxILRXNDTsrRa5JKIK8Mkz28NvaU4INPkZMmaSrGx8UWAaRPn-U_asv-fd6MrYt5XRaK9rF_fH8lJmJfLwgDr55rBuVF6VS1nfs2VC5JC_bI-9th7iWXtIcKuB3MSe4JZD35Jr");
        BiliWsPackage pkg = Protocol.newPackage(Operation.UserAuth, ua);
        ByteBuf buffer = Unpooled.buffer();
        Protocol.write(pkg.flip(), buffer);
        sb = new StringBuilder();
        ByteBufUtil.appendPrettyHexDump(sb, buf);
        System.out.println(sb.toString());
    }


    //{"clientver":"2.5.11",
    // "key":"WhF2RBb8sF1zLwNJlROW7sIaYLaylYx91HO2MUNJ3Z5r8OUOsu0QetWx99nAqJCpzo6CYgow2fPlS2YblD3cI0N2BbHrfgCTlO0l05jhHYw2N_DNriM7NRoysr1TvaPAbDieHF5E5ba42wTLMxY=",
    // "platform":"web",
    // "protover":2,
    // "roomid":7734200,
    // "type":2,
    // "uid":0}

    public static void test(String name, String base64) {
        System.out.println("Message : " + name);
        System.out.println("===========================");
        List<BiliWsPackage> list = Protocol.read(DataView.fromBase64(base64));
        for (BiliWsPackage pkg : list) {
            System.out.println(pkg);
        }
        System.out.println("===========================");
    }

}
