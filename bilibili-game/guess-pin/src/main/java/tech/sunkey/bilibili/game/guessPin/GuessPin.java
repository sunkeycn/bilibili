package tech.sunkey.bilibili.game.guessPin;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sunkey
 * @since 2021-01-14 3:45 下午
 **/
public class GuessPin {

    private final List<PinData> pins;
    private final GuessPinConfig config;
    private final AtomicInteger times = new AtomicInteger();

    @Getter
    private boolean gameWin;
    @Getter
    private boolean gameOver;

    public GuessPin(GuessPinConfig config) {
        this.config = config;
        this.pins = randomPins(config.getPinCount());
    }

    public int getTimes() {
        return times.get();
    }

    public GuessResult guess(List<PinData> guessPins) {
        if (guessPins == null || guessPins.size() != pins.size()) {
            throw new IllegalArgumentException();
        }
        GuessResult result = new GuessResult();
        int curTimes = times.incrementAndGet();
        if (curTimes > config.getGuessTimes()) {
            result.setGameOver(true);
            this.gameOver = true;
            return result;
        }
        Set<PinData.Colour> allColors = new HashSet<>();
        for (PinData pin : this.pins) {
            allColors.add(pin.getColor());
        }

        boolean win = true;
        for (int i = 0; i < pins.size(); i++) {
            GuessResult.Result r = guessPin(pins.get(i), guessPins.get(i), allColors);
            result.getResult().add(r);
            if (r != GuessResult.Result.MATCH_ALL) {
                win = false;
            }
        }
        result.setGameWin(win);
        this.gameWin = win;
        return result;
    }

    private GuessResult.Result guessPin(PinData real, PinData guess, Set<PinData.Colour> allColors) {
        if (real.getColor() == guess.getColor()) {
            return GuessResult.Result.MATCH_ALL;
        }
        if (allColors.contains(guess.getColor())) {
            return GuessResult.Result.MATCH_COLOR;
        }
        return GuessResult.Result.MATCH_NONE;
    }

    private List<PinData> randomPins(int count) {
        List<PinData> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(new PinData(randomColor()));
        }
        return list;
    }

    private PinData.Colour randomColor() {
        int bound = PinData.Colour.values().length;
        int randomIndex = ThreadLocalRandom.current().nextInt(bound);
        return PinData.Colour.values()[randomIndex];
    }

}
