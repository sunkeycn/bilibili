package tech.sunkey.bilibili.ws.client;

import tech.sunkey.bilibili.ws.dto.BiliWsPackage;

/**
 * @author Sunkey
 * @since 2021-01-09 5:24 下午
 **/
public interface ClientHandler {

    void connected(WsClient client);

    void message(WsClient client, BiliWsPackage message);

    void disconnected(WsClient client);

}
