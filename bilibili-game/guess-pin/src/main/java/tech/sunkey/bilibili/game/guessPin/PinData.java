package tech.sunkey.bilibili.game.guessPin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.*;

/**
 * @author Sunkey
 * @since 2021-01-14 3:51 下午
 **/

@Getter
@AllArgsConstructor
public class PinData {

    private Colour color;

    @Getter
    @RequiredArgsConstructor
    public enum Colour {
        WHITE("白", new Color(255, 255, 255)),
        YELLOW("黄", new Color(229, 230, 0)),
        BLUE("蓝", new Color(0, 102, 255)),
        GREEN("绿", new Color(0, 204, 0)),
        PURPLE("紫", new Color(153, 0, 204)),
        BLACK("黑", new Color(0, 0, 0)),
        ;
        private final String name;
        private final Color color;

        public static Colour forName(String name) {
            for (Colour value : values()) {
                if (value.name.equals(name)) {
                    return value;
                }
            }
            return null;
        }

    }

}
