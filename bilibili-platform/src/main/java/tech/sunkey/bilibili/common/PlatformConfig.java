package tech.sunkey.bilibili.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Sunkey
 * @since 2021-01-14 20:36
 **/

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "sunkey.bilibili")
public class PlatformConfig {

    private Live live;

    @Getter
    @Setter
    @ToString
    public static class Live {
        private int roomId;
    }

}
