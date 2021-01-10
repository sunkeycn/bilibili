package tech.sunkey.bilibili.api;

import tech.sunkey.bilibili.api.dto.danmu.DanmuInfoResult;
import tech.sunkey.bilibili.api.utils.BaseAPI;

/**
 * @author Sunkey
 * @since 2021-01-10 7:00 下午
 **/
public class LiveAPI extends BaseAPI {

    public DanmuInfoResult getDanmuInfo(int roomid) {
        return get(Config.getRoomInfoPath, DanmuInfoResult.class, roomid);
    }

}
