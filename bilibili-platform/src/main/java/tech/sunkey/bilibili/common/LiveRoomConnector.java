package tech.sunkey.bilibili.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tech.sunkey.bilibili.business.LiveBusiness;

/**
 * @author Sunkey
 * @since 2021-01-14 20:34
 **/
@Slf4j
@Component
public class LiveRoomConnector implements ApplicationRunner {

    @Autowired
    private LiveBusiness liveBusiness;
    @Autowired
    private PlatformConfig platformConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            liveBusiness.connectRoom(platformConfig.getLive().getRoomId());
        }).start();
    }

}
