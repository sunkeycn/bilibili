package tech.sunkey.bilibili.game.guessPin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-14 4:26 下午
 **/
@Getter
@Setter
@ToString
public class GuessResult {

    private List<Result> result = new ArrayList<>();
    private boolean gameWin;
    private boolean gameOver;

    public enum Result {
        MATCH_ALL,
        MATCH_COLOR,
        MATCH_NONE,
    }

}
