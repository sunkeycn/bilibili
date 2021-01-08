package tech.sunkey.bilibili.tests;

import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.ws.NettyWsClient;

import java.net.URI;

/**
 * @author Sunkey
 * @since 2021-01-08 2:45 下午
 **/
@Slf4j
public class DanmuLoadTests {

    //

//"{"cmd":"INTERACT_WORD","data":{"uid":424750845,"uname":"触手逸然","uname_color":"","identities":[1],"msg_type":1,"roomid":21144080,"timestamp":1610093837,"score":1610093837941,"fans_medal":{"target_id":0,"medal_level":0,"medal_name":"","medal_color":0,"medal_color_start":0,"medal_color_end":0,"medal_color_border":0,"is_lighted":0,"guard_level":0,"special":"","icon_id":0,"anchor_roomid":0,"score":0},"is_spread":0,"spread_info":"","contribution":{"grade":0},"spread_desc":"","tail_icon":0}}"

    /**
     * WS_AUTH_OK: 0
     * WS_AUTH_TOKEN_ERROR: -101
     * WS_BODY_PROTOCOL_VERSION_DEFLATE: 2
     * WS_BODY_PROTOCOL_VERSION_NORMAL: 0
     * WS_HEADER_DEFAULT_OPERATION: 1
     * WS_HEADER_DEFAULT_SEQUENCE: 1
     * WS_HEADER_DEFAULT_VERSION: 1
     * WS_HEADER_OFFSET: 4
     * WS_OPERATION_OFFSET: 8
     * WS_OP_CONNECT_SUCCESS: 8
     * WS_OP_HEARTBEAT: 2
     * WS_OP_HEARTBEAT_REPLY: 3
     * WS_OP_MESSAGE: 5
     * WS_OP_USER_AUTHENTICATION: 7
     * WS_PACKAGE_HEADER_TOTAL_LENGTH: 16
     * WS_PACKAGE_OFFSET: 0
     * WS_SEQUENCE_OFFSET: 12
     * WS_VERSION_OFFSET: 6
     *
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {
        String inf = "https://api.live.bilibili.com/room/v1/Room/room_init?id=5887574";
        NettyWsClient client = new NettyWsClient();
        client.connect(URI.create("wss://tx-sh-live-comet-02.chat.bilibili.com:443/sub"));
    }



}
