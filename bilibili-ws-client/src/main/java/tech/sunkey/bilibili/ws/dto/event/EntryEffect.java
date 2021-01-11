package tech.sunkey.bilibili.ws.dto.event;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class EntryEffect extends BaseEvent {
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data {
        private String copy_writing;
        private Integer effective_time;
        private Integer privilege_type;
        private String basemap_url;
        private Integer business;
        private Integer mock_effect;
        private String highlight_color;
        private Integer target_id;
        private String copy_writing_v2;
        private Integer max_delay_time;
        private Integer priority;
        private Integer web_effective_time;
        private Integer uid;
        private String face;
        private Integer web_effect_close;
        private String copy_color;
        private Integer web_close_time;
        private Integer show_avatar;
        private List<Integer> icon_list;
        private Integer id;
        private String web_basemap_url;
    }
}