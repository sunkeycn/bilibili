package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

@Getter
@Setter
@ToString
public class RoomRealTimeMessageUpdate extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private Integer red_notice;
        private Integer roomid;
        private Integer fans;
        private Integer fans_club;
    }
}