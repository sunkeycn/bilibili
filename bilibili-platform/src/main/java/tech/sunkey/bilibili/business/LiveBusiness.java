package tech.sunkey.bilibili.business;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.utils.BilibiliUtils;
import tech.sunkey.bilibili.ws.client.Config;
import tech.sunkey.bilibili.ws.client.WsClient;
import tech.sunkey.bilibili.ws.dto.UserAuth;

/**
 * @author Sunkey
 * @since 2021-01-08 2:45 下午
 **/

@Slf4j
@Component
public class LiveBusiness {

    @Autowired
    private LiveEventHandler liveEventHandler;

    public WsClient connectRoom(int shortRoomId) {
        LiveAPI api = new LiveAPI();
        RoomInitResult roomInfo = api.roomInit(shortRoomId);
        log.info("RoomInfo:{}", JSON.toJSONString(roomInfo));
        Integer roomId = roomInfo.getData().getRoom_id();
        DanmuInfoResult danmuInfo = api.getDanmuInfo(roomId);
        log.info("DanmuInfo:{}", JSON.toJSONString(danmuInfo));
        UserAuth userAuth = UserAuth.userAuth(0, roomId, danmuInfo.getData().getToken());
        log.info("UserAuth: {}", JSON.toJSONString(userAuth));
        return Config.config()
                .url(BilibiliUtils.getWssUrl(danmuInfo.getData()))
                .userAuth(userAuth)
                .logLevel(null)
                .handler(liveEventHandler)
                .connect();
    }

}
