package tech.sunkey.bilibili.ws.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sunkey.bilibili.ws.client.ClientHandler;
import tech.sunkey.bilibili.ws.client.WsClient;
import tech.sunkey.bilibili.ws.dto.BiliWsPackage;
import tech.sunkey.bilibili.ws.dto.event.BaseEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sunkey
 * @since 2021-01-11 2:43 下午
 **/

@Slf4j
public abstract class EventHandler implements ClientHandler {

    private final Map<EventType, Method> dispatches = new HashMap<>();

    public EventHandler() {
        for (Method method : getClass().getMethods()) {
            Event ann = method.getAnnotation(Event.class);
            if (ann != null) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                if (method.getParameterCount() != 1) {
                    throw new IllegalStateException("@Event not support!");
                }
                Class<?> paramType = method.getParameterTypes()[0];
                if (paramType == JSONObject.class) {
                } else if (BaseEvent.class.isAssignableFrom(paramType)) {
                } else {
                    throw new IllegalStateException("@Event not support!");
                }
                log.info("Mapping @Event({})=>{}", ann.value(), method);
                dispatches.put(ann.value(), method);
            }
        }
    }

    @SneakyThrows
    private void dispatch(EventType eventType, JSONObject json) {
        Method method = dispatches.get(eventType);
        if (method == null) {
            log.info("NoHandler for {}", json);
        } else {
            Object param = null;
            Class<?> type = method.getParameterTypes()[0];
            if (type == JSONObject.class) {
                param = json;
            } else if (BaseEvent.class.isAssignableFrom(type)) {
                param = json.toJavaObject(type);
            }
            method.invoke(this, param);
        }
    }

    @Override
    public void message(WsClient client, BiliWsPackage message) {
        if (message.getData() != null) {
            JSONObject json = JSON.parseObject(message.getData().toString());
            String cmd = json.getString("cmd");
            EventType eventType = EventType.forName(cmd);
            dispatch(eventType, json);
        } else {
            log.info("[WebSocket] Unknown Message: {}", message);
        }
    }

    @Override
    public void connected(WsClient client) {
        log.info("[WebSocket] Connected.");
    }

    @Override
    public void disconnected(WsClient client) {
        log.info("[WebSocket] Disconnected.");
    }

}
