package tech.sunkey.bilibili.tests;

import com.onway.common.utils.FileUtils;
import com.onway.common.utils.dev.JsonBean;

/**
 * @author Sunkey
 * @since 2021-01-11 3:02 下午
 **/
public class BeanGen {

    public static void main(String[] args) {
        String gen = new JsonBean().generate("RoomRank",
                FileUtils.from("classpath:roomRank.json").readToString());
        System.out.println(gen);
    }

}
