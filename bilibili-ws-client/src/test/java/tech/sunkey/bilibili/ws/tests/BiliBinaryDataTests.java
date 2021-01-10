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
        String s1 = "AAABDwAQAAEAAAAHAAAAAXsidWlkIjoyNDA4NDExNjYsInJvb21pZCI6NzczNDIwMCwicHJvdG92ZXIiOjIsInBsYXRmb3JtIjoid2ViIiwiY2xpZW50dmVyIjoiMi41LjExIiwidHlwZSI6Miwia2V5IjoicnlrMFRXb3lfdXBPLVZpUzVzaHZOVXBOZ2czWkxuWFV0eXd5dWFkUGZiWXJOSXc1YUJ0Q2hRc2Q1eXB2U1k4ZzQyeXliM2V5NDR4ZGR1a2dYdEJOQV9NT0ZLci1ERjE0STNlS3JkSWR2Z1ZQaVlPWkZNWWtxRjRoTjJqVllRb1ZEOTI3eXhXOWtlZDlKdW5QVmhremxGRT0ifQ==";
        test("S1", s1);
        String r1 = "AAAAGgAQAAEAAAAIAAAAAXsiY29kZSI6MH0=";
        test("R1", r1);
        String s2 = "AAAAHwAQAAEAAAACAAAAAVtvYmplY3QgT2JqZWN0XQ==";
        test("S2", s2);
        String r2 = "AAAAFAAQAAEAAAADAAAAAQFbrps=";
        test("R2", r2);
    }

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
