package tech.sunkey.bilibili.utils;

import tech.sunkey.bilibili.api.dto.live.DanmuInfoResult;
import tech.sunkey.bilibili.vo.DanmuVO;
import tech.sunkey.bilibili.ws.dto.event.DanmuMsg;

import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-11 2:54 下午
 **/
public class BilibiliUtils {

    public static String getWssUrl(DanmuInfoResult.DanmuInfo info) {
        DanmuInfoResult.Host host = info.getHost_list().get(0);
        return "wss://" + host.getHost() + ":" + host.getWss_port() + "/sub";
    }


    public static DanmuVO convert(DanmuMsg msg) {
        DanmuVO vo = new DanmuVO();
        List list = msg.getInfo();
        vo.setMsg((String) list.get(1));
        List uList = (List) list.get(2);
        vo.setUid(Integer.parseInt(String.valueOf(uList.get(0))));
        vo.setUname((String) uList.get(1));
        Object list9 = list.get(9);
        System.out.println(list9.getClass());
        System.out.println(list9);
        //vo.setTimestamp();
        return vo;
    }

}
