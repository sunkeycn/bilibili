package tech.sunkey.bilibili.ws.tests;

import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.utils.DataView;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-08 12:54 下午
 **/
public class BiliBinaryDataTests {


    public static void main(String[] args) {

        String s1 = "AAABDwAQAAEAAAAHAAAAAXsidWlkIjoyNDA4NDExNjYsInJvb21pZCI6NzczNDIwMCwicHJvdG92ZXIiOjIsInBsYXRmb3JtIjoid2ViIiwiY2xpZW50dmVyIjoiMi41LjExIiwidHlwZSI6Miwia2V5IjoicEEtRUpNaGJycTJYTTdLVHE4YnVkUkRsRmhZRmJqREszWGpTQWJBTUlkZVVfRXA5dTljQm1TVkc5eW9fNzd6ZEpEcXNFUzdoQUpHLWxFS2V4cTdCWnFkNVI0WEJJM1M3R2VqWTRJWUtZb0RjTk0xQnBBNnZyRE9JMlpnZlIyX19jdUdVaUJwQXFfVFhvamo0LUI0b3R2azEifQ==";
        test("S1", s1);
        String r1 = "AAAAGgAQAAEAAAAIAAAAAXsiY29kZSI6MH0=";
        test("R1", r1);
        String s2 = "AAAAHwAQAAEAAAACAAAAAVtvYmplY3QgT2JqZWN0XQ==";
        test("S2", s2);
        String r2 = "AAAAFAAQAAEAAAADAAAAAQFbrps=";
        test("R2", r2);
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
