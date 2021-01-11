package tech.sunkey.bilibili.ws.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sunkey.bilibili.ws.client.ClientHandler;
import tech.sunkey.bilibili.ws.client.WsClient;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;

/**
 * @author Sunkey
 * @since 2021-01-11 2:43 下午
 **/

public interface EventHandler extends ClientHandler {

    Logger log = LoggerFactory.getLogger(EventHandler.class);

    void handleEvent(EventType type, Object event);

    @Override
    default void message(WsClient client, BiliWsPackage message) {
        if (message.getData() != null) {
            JSONObject json = JSON.parseObject(message.getData().toString());
            String cmd = json.getString("cmd");
            try {
                EventType eventType = EventType.valueOf(cmd);
                Object event = json.getObject("data", eventType.getType());
                handleEvent(eventType, event);
            } catch (Exception ex) {
                log.info("[WebSocket] UnknownEventType[{}] Message: {}", cmd, message);
            }
        } else {
            log.info("[WebSocket] Unknown Message: {}", message);
        }
    }

    @Override
    default void connected(WsClient client) {
        log.info("[WebSocket] Connected.");

    }

    @Override
    default void disconnected(WsClient client) {
        log.info("[WebSocket] Disconnected.");
    }

}
