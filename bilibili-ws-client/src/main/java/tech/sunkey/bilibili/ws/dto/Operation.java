package tech.sunkey.bilibili.ws.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Sunkey
 * @since 2021-01-10 6:22 下午
 **/
@RequiredArgsConstructor
@Getter
public enum Operation {

    Unknown(-1),
    HeartBeat(Constants.WS_OP_HEARTBEAT),
    HeartBeatReply(Constants.WS_OP_HEARTBEAT_REPLY),
    Message(Constants.WS_OP_MESSAGE),
    UserAuth(Constants.WS_OP_USER_AUTHENTICATION),
    ConnectSuccess(Constants.WS_OP_CONNECT_SUCCESS),

    ;
    private final int code;

    public static Operation valueOf(int opcode) {
        for (Operation value : values()) {
            if (value.code == opcode) {
                return value;
            }
        }

        return Unknown;
    }

}
