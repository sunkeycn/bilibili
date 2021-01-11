package tech.sunkey.bilibili.ws.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tech.sunkey.bilibili.ws.dto.event.InteractWord;
import tech.sunkey.bilibili.ws.dto.event.NoticeMsg;

/**
 * @author Sunkey
 * @since 2021-01-11 2:44 下午
 **/
@Getter
@RequiredArgsConstructor
public enum EventType {

    INTERACT_WORD(InteractWord.class),
    NOTICE_MSG(NoticeMsg.class),
    ;

    private final Class<?> type;

}
