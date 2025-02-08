package game;

import utill.Constants;
import utill.ThreadedText;

import java.util.Scanner;

public class InputManager {
    private static Scanner scanner = new Scanner(System.in);


    public static String getStringInput(String prompt) {
        while (true) {
            ThreadedText.printTextSync(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                ThreadedText.printTextSync("값을 입력해주세요!");
                continue;
            }
            return input;
        }
    }

    // 숫자 범위 체크가 필요한 입력
    public static int getIntInputInRange(String prompt, int min, int max) {
        while (true) {
            try {
                ThreadedText.printTextSync(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    ThreadedText.printTextSync("값을 입력해주세요!");
                    continue;
                }

                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    ThreadedText.printTextSync("입력값은 %d에서 %d 사이여야 합니다!\n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                ThreadedText.printTextSync("숫자만 입력해주세요!");
            }
        }
    }


    public static int getPositiveIntInput(String prompt) {
        while (true) {
            try {
                ThreadedText.printTextSync(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    ThreadedText.printTextSync("값을 입력해주세요!");
                    continue;
                }

                int value = Integer.parseInt(input);
                if (value <= 0) {
                    ThreadedText.printTextSync("0보다 큰 값을 입력해주세요!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                ThreadedText.printTextSync("숫자만 입력해주세요!");
            }
        }
    }


    // 방문객 수 입력 (도시 인구 제한)
    public static int getVisitorInput(String prompt, int maxPopulation) {
        while (true) {
            int value = getPositiveIntInput(prompt);
            if (value <= maxPopulation) {
                return value;
            }
            ThreadedText.printTextSync("방문객 수가 도시 인구보다 많을 수 없습니다!");
            ThreadedText.printTextSync("현재 도시 인구: " + maxPopulation);
        }
    }

    // 공장 크기 입력 (오염도 제한)
    public static int getFactorySizeInput(String prompt, int currentPollution) {
        while (true) {
            int size = getPositiveIntInput(prompt);
            int futurePollution = currentPollution + (size * 2);
            if (futurePollution <= Constants.MAX_POLLUTION) {
                return size;
            }
            ThreadedText.printTextSync("이 크기의 공장을 건설하면 오염도가 너무 높아집니다!");
            ThreadedText.printTextSync("현재 오염도: " + currentPollution);
            ThreadedText.printTextSync("건설 후 예상 오염도: " + futurePollution);
            ThreadedText.printTextSync("더 작은 크기의 공장을 건설해주세요.");
        }
    }
}