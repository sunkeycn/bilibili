package tech.sunkey.bilibili.tests;

import com.alibaba.fastjson.JSON;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.ws.client.ClientHandler;
import tech.sunkey.bilibili.ws.client.Config;
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
        log.info("UserAuth: {}", JSON.toJSONString(userAuth));
        Config.config()
                .url(getWssUrl(danmuInfo.getData()))
                .userAuth(userAuth)
                .logLevel(LogLevel.INFO)
                .handler(new HandlerImpl())
                .connect();
    }

    public static String getWssUrl(DanmuInfoResult.DanmuInfo info) {
        DanmuInfoResult.Host host = info.getHost_list().get(0);
        return "wss://" + host.getHost() + ":" + host.getWss_port() + "/sub";
    }

    @RequiredArgsConstructor
    public static class HandlerImpl implements ClientHandler {

        @Override
        public void connected(WsClient client) {
            log.info("[WebSocket] Connected.");
        }

        @Override
        public void message(WsClient client, BiliWsPackage message) {
            log.info("[WebSocket] Recv Message: {}", message);
        }

        @Override
        public void disconnected(WsClient client) {
            log.info("[WebSocket] Disconnected.");
        }
    }

}
