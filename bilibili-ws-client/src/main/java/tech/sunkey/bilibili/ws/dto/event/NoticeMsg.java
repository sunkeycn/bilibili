package tech.sunkey.bilibili.ws.dto.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-11 2:46 下午
 **/
@Getter
@Setter
@ToString
public class NoticeMsg extends BaseEvent {

    private String cmd;
    private Full full;
    private Half half;
    private Side side;
    private Integer roomid;
    private Integer real_roomid;
    private String msg_common;
    private String msg_self;
    private String link_url;
    private Integer msg_type;
    private Integer shield_uid;
    private String business_id;
    private Scatter scatter;

    @Getter
    @Setter
    @ToString
    public static class Side {
        private String head_icon;
        private String background;
        private String color;
        private String highlight;
        private String border;
    }

    @Getter
    @Setter
    @ToString
    public static class Half {
        private String head_icon;
        private String tail_icon;
        private String background;
        private String color;
        private String highlight;
        private Integer time;
    }

    @Getter
    @Setter
    @ToString
    public static class Scatter {
        private Integer min;
        private Integer max;
    }

    @Getter
    @Setter
    @ToString
    public static class Full {
        private String head_icon;
        private String tail_icon;
        private String head_icon_fa;
        private String tail_icon_fa;
        private Integer head_icon_fan;
        private Integer tail_icon_fan;
        private String background;
        private String color;
        private String highlight;
        private Integer time;
    }
}




