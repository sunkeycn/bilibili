package tech.sunkey.bilibili.business;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.utils.BilibiliUtils;
import tech.sunkey.bilibili.ws.dto.event.*;
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
        //log.info("InteractWord=>{}", data);
    }

    @Event(EventType.NOTICE_MSG)
    public void handleNoticeMsg(NoticeMsg data) {
        //log.info("NoticeMsg=>{}", data);
    }

    @Event(EventType.WIDGET_BANNER)
    public void handleWidgetBanner(WidgetBanner data) {
        //log.info("WidgetBanner=>{}", data);
    }

    @Event(EventType.DANMU_MSG)
    public void handleDanmuMsg(DanmuMsg data) {
        // log.info("DanmuMsg=>{}", data);
        log.info("弹幕:{}", BilibiliUtils.convert(data));
    }

    @Event(EventType.COMBO_SEND)
    public void handleComboSend(ComboSend data) {
        //  log.info("ComboSend=>{}", data);
    }

    @Event(EventType.ONLINE_RANK_COUNT)
    public void handleOnlineRankCount(OnlineRankCount data) {
        // log.info("OnlineRankCount=>{}", data);
    }

    @Event(EventType.ENTRY_EFFECT)
    public void handleEntryEffect(EntryEffect data) {
        // log.info("EntryEffect=>{}", data);
    }

    @Event(EventType.SEND_GIFT)
    public void handleSendGift(SendGift data) {
        // log.info("SendGift=>{}", data);
    }

    @Event(EventType.ROOM_REAL_TIME_MESSAGE_UPDATE)
    public void handleRoomRealTimeMessageUpdate(RoomRealTimeMessageUpdate data) {
        // log.info("RoomRealTimeMessageUpdate=>{}", data);
    }

    @Event(EventType.ROOM_RANK)
    public void handleRoomRank(RoomRank data) {
        // log.info("RoomRank=>{}", data);
    }

    @Event(EventType.ONLINE_RANK_V2)
    public void handleOnlineRankV2(OnlineRankV2 data) {
        // log.info("OnlineRankV2=>{}", data);
    }

    @Event(EventType.HOT_RANK_CHANGED)
    public void handleHotRankChanged(HotRankChanged data) {
        // log.info("HotRankChanged=>{}", data);
    }


}
