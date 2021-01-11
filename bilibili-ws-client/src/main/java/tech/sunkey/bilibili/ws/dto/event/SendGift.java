package tech.sunkey.bilibili.ws.dto.event;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Getter
@Setter
@ToString
public class SendGift extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private Integer total_coin;
        private Integer is_special_batch;
        private String coin_type;
        private String giftName;
        private Integer num;
        private Integer super_batch_gift_num;
        private Integer combo_total_coin;
        private String beatId;
        private Integer rcost;
        private Integer effect_block;
        private Integer super_gift_num;
        private String tid;
        private Integer gold;
        private Integer uid;
        private Integer giftId;
        private Integer crit_prob;
        private Integer price;
        private MedalInfo medal_info;
        private String action;
        private Integer svga_block;
        private String name_color;
        private Integer combo_resources_id;
        private Integer guard_level;
        private Integer timestamp;
        private Integer magnification;
        private String uname;
        private Integer remain;
        private Integer demarcation;
        private String batch_combo_id;
        private Boolean is_first;
        private String rnd;
        private Integer giftType;
        private Integer combo_stay_time;
        private Integer draw;
        @JSONField(name = "super")
        private Integer superValue;
        private String face;
        private Integer effect;
        private Integer broadcast_id;
        private String biz_source;
        private Integer silver;
        private String tag_image;
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