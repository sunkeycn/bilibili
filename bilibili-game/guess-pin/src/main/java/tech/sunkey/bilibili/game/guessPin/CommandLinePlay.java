package tech.sunkey.bilibili.game.guessPin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sunkey
 * @since 2021-01-14 3:48 下午
 **/
public class CommandLinePlay {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        GuessPinConfig config = GuessPinConfig.builder()
                .pinCount(4)
                .guessTimes(9)
                .build();
        GuessPin game = new GuessPin(config);
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎来到猜图钉游戏！");
        System.out.printf("游戏规则：游戏中共有%s枚图钉，颜色共有[白、黑、黄、蓝、绿、紫]6种颜色，按照顺序摆放。\n", config.getPinCount());
        System.out.println("输入颜色和顺序的组合来进行图钉的猜测！");
        System.out.println();
        System.out.println("按Enter键开始!");
        scanner.nextLine();
        printUnknownPins(config.getPinCount(), "[x]");
        while (true) {
            String cmd = scanner.nextLine();
            try {
                GuessResult result = game.guess(parseCmd(cmd));
                if (result.isGameWin()) {
                    System.out.println("你赢了！！");
                    break;
                } else if (result.isGameOver()) {
                    System.out.println("游戏结束！");
                    break;
                } else {
                    printResult(result);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("输入错误,请重试");
            }
        }
    }

    public static void printResult(GuessResult result) {
        StringBuilder sb = new StringBuilder();
        for (GuessResult.Result r : result.getResult()) {
            switch (r) {
                case MATCH_ALL:
                    sb.append("[对]");
                    break;
                case MATCH_COLOR:
                    sb.append("[半]");
                    break;
                case MATCH_NONE:
                default:
                    sb.append("[错]");
            }
        }
        System.out.println(sb.toString());
    }

    public static List<PinData> parseCmd(String cmd) {
        char[] chars = cmd.toCharArray();
        List<PinData> list = new ArrayList<>(chars.length);
        for (char color : chars) {
            list.add(new PinData(PinData.Colour.forName(String.valueOf(color))));
        }
        return list;
    }

    public static void printUnknownPins(int pinCount, String str) {
        System.out.println(str.repeat(pinCount));
    }

}
