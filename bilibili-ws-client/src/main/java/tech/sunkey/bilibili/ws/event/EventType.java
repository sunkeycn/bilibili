package tech.sunkey.bilibili.ws.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tech.sunkey.bilibili.ws.dto.event.*;

/**
 * @author Sunkey
 * @since 2021-01-11 2:44 下午
 **/
@Getter
@RequiredArgsConstructor
public enum EventType {

    Unknown(null),
    INTERACT_WORD(InteractWord.class),
    NOTICE_MSG(NoticeMsg.class),
    DANMU_MSG(DanmuMsg.class),
    WIDGET_BANNER(WidgetBanner.class),
    COMBO_SEND(ComboSend.class),
    ONLINE_RANK_COUNT(OnlineRankCount.class),
    ENTRY_EFFECT(EntryEffect.class),
    SEND_GIFT(SendGift.class),
    ROOM_REAL_TIME_MESSAGE_UPDATE(RoomRealTimeMessageUpdate.class),
    ROOM_RANK(RoomRank.class),
    ;

    private final Class<?> type;

    public static EventType forName(String cmd) {
        try {
            return EventType.valueOf(cmd);
        } catch (Exception ex) {
            return Unknown;
        }
    }

}
