package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

@Getter
@Setter
@ToString
public class HotRankChanged extends BaseEvent {
    private Data data;

    public static class Data {
        private String area_name;
        private String live_link_url;
        private String web_url;
        private Integer trend;
        private Integer countdown;
        private String icon;
        private Integer rank;
        private String live_url;
        private String blink_url;
        private String pc_link_url;
        private Integer timestamp;
    }
}