package tech.sunkey.bilibili.business;

import org.springframework.stereotype.Component;
import tech.sunkey.bilibili.game.guessPin.*;
import tech.sunkey.bilibili.vo.DanmuVO;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Sunkey
 * @since 2021-01-14 20:20
 **/
@Component
public class GameBusiness {

    private GuessPin guessPin;

    /**
     * 欢迎来到猜图钉游戏！
     * 游戏规则：游戏中共有4枚图钉，颜色共有[白、黑、黄、蓝、绿、紫]6种颜色，按照顺序摆放
     * 输入颜色和顺序的组合来进行图钉的猜测！
     */

    @PostConstruct
    public void init() {
       // restartGame();
    }

    public void restartGame() {
        GuessPinConfig config = GuessPinConfig.builder()
                .pinCount(4)
                .guessTimes(9)
                .build();
        guessPin = new GuessPin(config);
        System.out.println("欢迎来到猜图钉游戏！");
        System.out.println("游戏规则：游戏中共有4枚图钉，颜色共有[白、黑、黄、蓝、绿、紫]6种颜色，按照顺序摆放");
        System.out.println("弹幕发送颜色和顺序的组合来进行图钉的猜测！（例：红黄白绿） ");
        System.out.println("发送“重新开始”即可重新开始游戏!");
        System.out.println("开始新游戏~");
        System.out.println("[x][x][x][x]");
    }

    public void handleDanmu(DanmuVO danmu) {
        if ("重新开始".equals(danmu.getMsg())) {
            restartGame();
            return;
        }
        if (guessPin.isGameOver() || guessPin.isGameWin()) {
            restartGame();
        }
        System.out.printf("用户：%s 输入了：%s\n", danmu.getUname(), danmu.getMsg());
        try {
            List<PinData> cmd = CommandLinePlay.parseCmd(danmu.getMsg());
            GuessResult result = guessPin.guess(cmd);
            if (result.isGameWin()) {
                System.out.printf("用户：%s 仅用了%s次就获得了胜利！\n", danmu.getUname(), guessPin.getTimes());
                restartGame();
            } else if (result.isGameOver()) {
                System.out.printf("用户：%s猜错了~ 游戏结束！\n", danmu.getUname());
                restartGame();
            } else {
                CommandLinePlay.printResult(result);
            }
        } catch (Exception ex) {
            System.out.println("指令错误，请重试~");
        }
    }

}
