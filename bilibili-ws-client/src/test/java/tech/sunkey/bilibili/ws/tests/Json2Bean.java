package tech.sunkey.bilibili.ws.tests;

import com.onway.common.utils.FileUtils;
import com.onway.common.utils.dev.JsonBean;

/**
 * @author Sunkey
 * @since 2021-01-10 7:38 下午
 **/
public class Json2Bean {

    public static void main(String[] args) {
        String gen = new JsonBean().generate("UserAuth", FileUtils.from("classpath:UserAuth.json").readToString());
        System.out.println(gen);
    }

}
