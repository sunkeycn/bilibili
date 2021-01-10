package tech.sunkey.bilibili.api.dto.live;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tech.sunkey.bilibili.api.dto.BaseResult;

/**
 * @author Sunkey
 * @since 2021-01-10 7:30 下午
 **/
@Getter
@Setter
@ToString
public class RoomInitResult extends BaseResult {
    private RoomInit data;

    @Getter
    @Setter
    @ToString
    public static class RoomInit {
        private Integer room_id;
        private Integer short_id;
        private Integer uid;
        private Integer need_p2p;
        private Boolean is_hidden;
        private Boolean is_locked;
        private Boolean is_portrait;
        private Integer live_status;
        private Integer hidden_till;
        private Integer lock_till;
        private Boolean encrypted;
        private Boolean pwd_verified;
        private Integer live_time;
        private Integer room_shield;
        private Integer is_sp;
        private Integer special_type;
    }
}
