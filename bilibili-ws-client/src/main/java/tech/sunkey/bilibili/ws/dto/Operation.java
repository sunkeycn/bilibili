package tech.sunkey.bilibili.ws.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Sunkey
 * @since 2021-01-10 6:22 下午
 **/
@RequiredArgsConstructor
@Getter
public enum Operation implements Constants {

    HeartBeat(WS_OP_HEARTBEAT),
    HeartBeatReply(WS_OP_HEARTBEAT_REPLY),
    Message(WS_OP_MESSAGE),
    Auth(WS_OP_USER_AUTHENTICATION),
    ConnectSuccess(WS_OP_CONNECT_SUCCESS),

    ;
    private final int code;
}
