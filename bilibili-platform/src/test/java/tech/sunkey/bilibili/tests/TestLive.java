package tech.sunkey.bilibili.tests;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.api.LiveAPI;
import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.business.LiveBusiness;
import tech.sunkey.bilibili.utils.BilibiliUtils;
import tech.sunkey.bilibili.ws.client.Config;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
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
        new LiveBusiness().connectRoom(21463235);
    }

}
