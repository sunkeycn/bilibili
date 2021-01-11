package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

@Getter
@Setter
@ToString
public class RoomRank extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String h5_url;
        private String color;
        private String web_url;
        private String rank_desc;
        private Integer roomid;
        private Integer timestamp;
    }
}