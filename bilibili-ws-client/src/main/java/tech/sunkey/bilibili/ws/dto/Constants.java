package tech.sunkey.bilibili.ws.dto;

/**
 * @author Sunkey
 * @since 2021-01-10 6:25 下午
 **/
public interface Constants {

    int WS_PACKAGE_HEADER_TOTAL_LENGTH = 16;

    int WS_PACKAGE_OFFSET = 0;
    int WS_HEADER_OFFSET = 4;
    int WS_VERSION_OFFSET = 6;
    int WS_OPERATION_OFFSET = 8;
    int WS_SEQUENCE_OFFSET = 12;

    int WS_AUTH_OK = 0;
    int WS_AUTH_TOKEN_ERROR = -101;

    int WS_BODY_PROTOCOL_VERSION_NORMAL = 0;
    int WS_BODY_PROTOCOL_VERSION_DEFLATE = 2;

    int WS_HEADER_DEFAULT_OPERATION = 1;
    int WS_HEADER_DEFAULT_SEQUENCE = 1;
    int WS_HEADER_DEFAULT_VERSION = 1;

    int WS_OP_HEARTBEAT = 2;
    int WS_OP_HEARTBEAT_REPLY = 3;
    int WS_OP_MESSAGE = 5;
    int WS_OP_USER_AUTHENTICATION = 7;
    int WS_OP_CONNECT_SUCCESS = 8;
}
