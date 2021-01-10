package tech.sunkey.bilibili.api.dto.live;

import lombok.*;
import tech.sunkey.bilibili.api.dto.BaseResult;

import java.util.List;

@Getter
@Setter
@ToString
public class DanmuInfoResult extends BaseResult {

    private DanmuInfo data;

    @Getter
    @Setter
    @ToString
    public static class DanmuInfo {
        private String group;
        private Integer business_id;
        private Integer refresh_row_factor;
        private Integer refresh_rate;
        private Integer max_delay;
        private String token;
        private List<Host> host_list;
    }

    @Getter
    @Setter
    @ToString
    public static class Host {
        private String host;
        private Integer port;
        private Integer wss_port;
        private Integer ws_port;
    }
}