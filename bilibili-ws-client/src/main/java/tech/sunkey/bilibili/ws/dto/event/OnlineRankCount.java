package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

@Getter
@Setter
@ToString
public class OnlineRankCount extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private Integer count;
    }
}