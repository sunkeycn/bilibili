package tech.sunkey.bilibili.api;

/**
 * @author Sunkey
 * @since 2021-01-10 7:01 下午
 **/
public class Config {

    public static final String rootPath = "https://api.live.bilibili.com";

    public static final String getDanmuInfoPath = "/xlive/web-room/v1/index/getDanmuInfo?id=%s&type=0";

    public static final String roomInitPath = "/room/v1/Room/room_init?id=%s";
}
