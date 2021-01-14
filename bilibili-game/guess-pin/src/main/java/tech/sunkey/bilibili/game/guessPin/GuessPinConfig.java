package tech.sunkey.bilibili.game.guessPin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunkey
 * @since 2021-01-14 4:00 下午
 **/
@Getter
@Setter
@ToString
@Builder
public class GuessPinConfig {

    private int pinCount;
    private int guessTimes;

}
