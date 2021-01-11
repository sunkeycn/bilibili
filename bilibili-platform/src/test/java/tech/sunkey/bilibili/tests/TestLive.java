package tech.sunkey.bilibili.tests;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.utils.BilibiliUtils;
import tech.sunkey.bilibili.ws.client.Config;
import tech.sunkey.bilibili.ws.dto.UserAuth;
import tech.sunkey.bilibili.ws.event.EventHandler;
import tech.sunkey.bilibili.ws.event.EventType;

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
                .url(BilibiliUtils.getWssUrl(danmuInfo.getData()))
                .userAuth(userAuth)
                .logLevel(null)
                .handler(new HandlerImpl())
                .connect();
    }

    @RequiredArgsConstructor
    public static class HandlerImpl implements EventHandler {
        @Override
        public void handleEvent(EventType type, Object event) {
            log.info("HandleEvent[{}]=>{}", type, event);
        }
    }

}
