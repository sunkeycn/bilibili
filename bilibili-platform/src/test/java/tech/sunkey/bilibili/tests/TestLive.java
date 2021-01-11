package tech.sunkey.bilibili.tests;

import lombok.extern.slf4j.Slf4j;
import tech.sunkey.bilibili.business.LiveBusiness;

/**
 * @author Sunkey
 * @since 2021-01-10 7:20 下午
 **/

@Slf4j
public class TestLive {

    public static void main(String[] args) {
        new LiveBusiness().connectRoom(528);
    }

}
