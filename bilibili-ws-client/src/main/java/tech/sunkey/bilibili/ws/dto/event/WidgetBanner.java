package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
public class WidgetBanner extends BaseEvent {

    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private Map<String, Widget> widget_list;
        private Integer timestamp;
    }

    @Getter
    @Setter
    @ToString
    public static class Widget {
        private Integer band_id;
        private String sub_key;
        private Integer type;
        private String sub_data;
    }
}