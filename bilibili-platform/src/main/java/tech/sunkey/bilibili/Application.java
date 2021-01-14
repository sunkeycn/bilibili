package tech.sunkey.bilibili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tech.sunkey.bilibili.common.PlatformConfig;

/**
 * @author Sunkey
 * @since 2021-01-08 1:03 下午
 **/

@EnableConfigurationProperties(PlatformConfig.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
