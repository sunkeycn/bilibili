package tech.sunkey.bilibili.ws.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 2:45 下午
 **/
@Getter
@Setter
@ToString
public class InteractWord extends BaseEvent {

    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private Integer uid;
        private String uname;
        private String uname_color;
        private List<Integer> identities;
        private Integer msg_type;
        private Integer roomid;
        private Integer timestamp;
        private Integer score;
        private FansMedal fans_medal;
        private Integer is_spread;
        private String spread_info;
        private Contribution contribution;
        private String spread_desc;
        private Integer tail_icon;
    }

    @Getter
    @Setter
    @ToString
    public static class FansMedal {
        private Integer target_id;
        private Integer medal_level;
        private String medal_name;
        private Integer medal_color;
        private Integer medal_color_start;
        private Integer medal_color_end;
        private Integer medal_color_border;
        private Integer is_lighted;
        private Integer guard_level;
        private String special;
        private Integer icon_id;
        private Integer anchor_roomid;
        private Integer score;
    }

    @Getter
    @Setter
    @ToString
    public static class Contribution {
        private Integer grade;
    }

}