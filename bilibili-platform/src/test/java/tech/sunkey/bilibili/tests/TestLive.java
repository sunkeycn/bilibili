package tech.sunkey.bilibili.tests;

import com.alibaba.fastjson.JSON;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.ws.client.ClientHandler;
import tech.sunkey.bilibili.ws.client.DefaultWsClient;
import tech.sunkey.bilibili.ws.client.WsClient;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.Operation;
import tech.sunkey.bilibili.ws.dto.UserAuth;
import tech.sunkey.bilibili.ws.utils.Protocol;

/**
 * @author Sunkey
 * @since 2021-01-10 7:20 下午
 **/

@Slf4j
public class TestLive {

    public static void main(String[] args) {
        LiveAPI api = new LiveAPI();
        RoomInitResult roomInfo = api.roomInit(21463235);
        System.out.println(roomInfo);
        Integer roomId = roomInfo.getData().getRoom_id();
        System.out.println("RoomId=" + roomId);
        DanmuInfoResult danmuInfo = api.getDanmuInfo(roomId);
        System.out.println(danmuInfo);
        UserAuth userAuth = UserAuth.userAuth(0, roomId, danmuInfo.getData().getToken());
        //int uid = 240841166;
        //String token = "SfZtSBoJ3gpJors5_slxILRXNDTsrRa5JKIK8Mkz28NvaU4INPkZMmaSrGx8UWAaRPn-U_asv-fd6MrYt5XRaK9rF_fH8lJmJfLwgDr55rBuVF6VS1nfs2VC5JC_bI-9th7iWXtIcKuB3MSe4JZD35Jr";
        //UserAuth userAuth = UserAuth.userAuth(uid, roomId, token);
        log.info("UserAuth: {}", JSON.toJSONString(userAuth));
        client().connect(new WsClient.Config()
                .url(getWssUrl(danmuInfo.getData()))
                .logLevel(LogLevel.INFO)
                .handler(new HandlerImpl(userAuth)));
    }

    public static WsClient client() {
        return new DefaultWsClient();
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
        // return "wss://" + host.getHost() + "/sub";
        // return "ws://" + host.getHost() + ":" + host.getWs_port() + "/sub";
        return "wss://" + host.getHost() + ":" + host.getWss_port() + "/sub";
    }

    @RequiredArgsConstructor
    public static class HandlerImpl implements ClientHandler {

        private final UserAuth userAuth;

        @Override
        public void connected(WsClient client) {
            log.info("[WebSocket] Connected.");
            client.delay(() -> {
                log.info("[WebSocket] Send UserAuth.");
                client.send(Protocol.userAuth(userAuth).flip());
            }, 2);
        }

        @Override
        public void message(WsClient client, BiliWsPackage message) {
            log.info("[WebSocket] Recv Message: {}", message);

            switch (Operation.valueOf(message.getOperation())) {
                case ConnectSuccess:
                    client.startHeartBeatTask(0);
                    break;

                default:
                    log.info("[WebSocket] not handler for opcode={}", message.getOperation());
            }
        }

        @Override
        public void disconnected(WsClient client) {
            log.info("[WebSocket] Disconnected.");
        }
    }

}
