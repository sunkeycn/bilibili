package tech.sunkey.bilibili.ws.tests;

import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.UserAuth;
import tech.sunkey.bilibili.ws.utils.DataView;
import tech.sunkey.bilibili.ws.utils.Protocol;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-10 10:19 下午
 **/
public class ProtocolSerialTests {


    public static void main(String[] args) {
        UserAuth ua = UserAuth.userAuth(7734200, "gwouhHih6nCVvSL_clJ7p1F4HR8VL28TNfoIZp9sCyUa2UyhhUaQiVvdczU43keHtHfdBHwsCy9npFx_Fil6wyzwc3PXyZQ8rHEYYpXhnW3iZpV6LJCtqAHuvhI37Tafd5X8jt6fNaZpn1jLmw==");
        BiliWsPackage pkg = Protocol.userAuth(ua);
        System.out.println(pkg);
        String b64 = DataView.of(Protocol.serialize(pkg)).base64();
        System.out.println(b64);
        List<BiliWsPackage> read = Protocol.read(DataView.fromBase64(b64));

        for (BiliWsPackage pkg2 : read) {
            System.out.println(pkg2);
        }
    }
}
