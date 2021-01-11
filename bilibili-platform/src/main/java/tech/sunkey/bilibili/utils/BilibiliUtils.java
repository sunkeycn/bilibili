package tech.sunkey.bilibili.utils;

import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;

/**
 * @author Sunkey
 * @since 2021-01-11 2:54 下午
 **/
public class BilibiliUtils {

    public static String getWssUrl(DanmuInfoResult.DanmuInfo info) {
        DanmuInfoResult.Host host = info.getHost_list().get(0);
        return "wss://" + host.getHost() + ":" + host.getWss_port() + "/sub";
    }

}
