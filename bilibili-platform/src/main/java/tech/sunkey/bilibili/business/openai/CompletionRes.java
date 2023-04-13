package tech.sunkey.bilibili.business.openai;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CompletionRes {

    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;

    @Getter
    @Setter
    @ToString
    public static class Choice {

        private Delta delta;
        private int index;
        // stop
        private String reason;

    }

    @Getter
    @Setter
    @ToString
    public static class Delta {
        // assistant
        private String role;
        private String content;
    }

}
