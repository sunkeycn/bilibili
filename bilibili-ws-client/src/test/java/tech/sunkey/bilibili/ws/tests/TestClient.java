package tech.sunkey.bilibili.ws.tests;

import tech.sunkey.bilibili.ws.client.WsClient;

/**
 * @author Sunkey
 * @since 2021-01-09 5:55 下午
 **/
public class TestClient {

    public static void main(String[] args) {
        WsClient.Config config = new WsClient.Config()
                .url("wss://tx-sh-live-comet-02.chat.bilibili.com/sub");
        System.out.println(config.getPort());
    }

}
