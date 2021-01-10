package tech.sunkey.bilibili.api;

import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.api.dto.live.RoomInitResult;
import tech.sunkey.bilibili.api.utils.BaseAPI;

/**
 * @author Sunkey
 * @since 2021-01-10 7:00 下午
 **/
public class LiveAPI extends BaseAPI {

    public DanmuInfoResult getDanmuInfo(int longRoomId) {
        return get(Config.getDanmuInfoPath, DanmuInfoResult.class, longRoomId);
    }

    public RoomInitResult roomInit(int roomId) {
        return get(Config.roomInitPath, RoomInitResult.class, roomId);
    }

}
