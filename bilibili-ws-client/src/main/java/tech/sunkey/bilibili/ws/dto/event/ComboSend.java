package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

@Getter
@Setter
@ToString
public class ComboSend extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String uname;
        private Integer gift_id;
        private String batch_combo_id;
        private Integer combo_total_coin;
        private Integer ruid;
        private String combo_id;
        private Integer is_show;
        private Integer uid;
        private Integer combo_num;
        private Integer gift_num;
        private String r_uname;
        private Integer total_num;
        private Integer batch_combo_num;
        private MedalInfo medal_info;
        private String action;
        private String gift_name;
        private String name_color;
    }

    @Getter
    @Setter
    @ToString
    public static class MedalInfo {
        private String medal_name;
        private String anchor_uname;
        private Integer medal_color_border;
        private Integer medal_color_end;
        private Integer target_id;
        private Integer icon_id;
        private String special;
        private Integer medal_color;
        private Integer medal_color_start;
        private Integer is_lighted;
        private Integer medal_level;
        private Integer anchor_roomid;
        private Integer guard_level;
    }
}