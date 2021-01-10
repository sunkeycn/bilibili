package tech.sunkey.bilibili.api.tests;

import com.onway.common.utils.FileUtils;
import com.onway.common.utils.dev.JsonBean;

/**
 * @author Sunkey
 * @since 2021-01-10 7:12 下午
 **/
public class Json2Bean {

    public static void main(String[] args) {
        String gen = new JsonBean().generate("DanmuInfo", FileUtils.from("classpath:getRoomInfo.json").readToString());
        System.out.println(gen);
    }

}
