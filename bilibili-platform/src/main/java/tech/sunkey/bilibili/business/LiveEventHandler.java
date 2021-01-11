package tech.sunkey.bilibili.business;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.event.InteractWord;
import tech.sunkey.bilibili.ws.dto.event.NoticeMsg;
import tech.sunkey.bilibili.ws.event.Event;
import tech.sunkey.bilibili.ws.event.EventHandler;
import tech.sunkey.bilibili.ws.event.EventType;

/**
 * @author Sunkey
 * @since 2021-01-11 2:59 下午
 **/

@Slf4j
public class LiveEventHandler extends EventHandler {

    @Event(EventType.Unknown)
    public void handleEvent(JSONObject data) {
        log.info("HandleEvent=>{}", data);
    }

    @Event(EventType.INTERACT_WORD)
    public void handleInteractWord(InteractWord data) {
        log.info("InteractWord=>{}", data);
    }

    @Event(EventType.NOTICE_MSG)
    public void handleNoticeMsg(NoticeMsg data) {
        log.info("NoticeMsg=>{}", data);
    }

}
