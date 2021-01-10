package tech.sunkey.bilibili.tests;

import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.ws.client.ClientHandler;
import tech.sunkey.bilibili.ws.client.DefaultWsClient;
import tech.sunkey.bilibili.ws.client.WsClient;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.UserAuth;
import tech.sunkey.bilibili.ws.utils.Protocol;

/**
 * @author Sunkey
 * @since 2021-01-10 7:20 下午
 **/
public class TestLive {

    public static void main(String[] args) {
        LiveAPI api = new LiveAPI();
        RoomInitResult roomInfo = api.roomInit(6);
        System.out.println(roomInfo);
        Integer roomId = roomInfo.getData().getRoom_id();
        System.out.println("RoomId=" + roomId);
        DanmuInfoResult danmuInfo = api.getDanmuInfo(roomId);
        System.out.println(danmuInfo);
        client().connect(new WsClient.Config()
                .url(getWssUrl(danmuInfo.getData()))
                .logLevel(LogLevel.INFO)
                .handler(new HandlerImpl(UserAuth.userAuth(roomId, danmuInfo.getData().getToken()))));
    }

    public static WsClient client() {
        return new WsClient();
    }


    //host: "tx-bj-live-comet-03.chat.bilibili.com"
    //port: 2243
    //ws_port: 2244
    //wss_port: 443
    //token
    //wss://tx-bj-live-comet-03.chat.bilibili.com/sub
    public static String getWssUrl(DanmuInfoResult.DanmuInfo info) {
        DanmuInfoResult.Host host = info.getHost_list().get(0);
        // return "wss://" + host.getHost() + ":" + host.getWss_port() + "/sub";
        return "wss://" + host.getHost() + "/sub";
    }

    @RequiredArgsConstructor
    public static class HandlerImpl implements ClientHandler {

        private final UserAuth userAuth;

        @Override
        public void connected(WsClient client) {
            System.out.println("Connected. Send UserAuth...");
            client.send(Protocol.userAuth(userAuth));
        }

        @Override
        public void message(WsClient client, BiliWsPackage message) {
            System.out.println("Recv Message:" + message);
        }

        @Override
        public void disconnected(WsClient client) {
            System.out.println("Disconnected.");
        }
    }

}
