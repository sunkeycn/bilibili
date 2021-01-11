package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class OnlineRankV2 extends BaseEvent {

    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private List<Rank> list;
        private String rank_type;
    }

    @Getter
    @Setter
    @ToString
    public static class Rank {
        private Integer uid;
        private String score;
        private String face;
        private String uname;
        private Integer rank;
        private Integer guard_level;
    }
}