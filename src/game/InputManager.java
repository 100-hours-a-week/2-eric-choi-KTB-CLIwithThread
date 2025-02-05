package game;

import utill.Constants;

import java.util.Scanner;

public class InputManager {
    private static Scanner scanner = new Scanner(System.in);


    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("값을 입력해주세요!");
                continue;
            }
            return input;
        }
    }

    // 숫자 범위 체크가 필요한 입력
    public static int getIntInputInRange(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("값을 입력해주세요!");
                    continue;
                }

                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    System.out.printf("입력값은 %d에서 %d 사이여야 합니다!\n", min, max);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요!");
            }
        }
    }


    public static int getPositiveIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("값을 입력해주세요!");
                    continue;
                }

                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("0보다 큰 값을 입력해주세요!");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요!");
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
            System.out.println("방문객 수가 도시 인구보다 많을 수 없습니다!");
            System.out.println("현재 도시 인구: " + maxPopulation);
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
            System.out.println("이 크기의 공장을 건설하면 오염도가 너무 높아집니다!");
            System.out.println("현재 오염도: " + currentPollution);
            System.out.println("건설 후 예상 오염도: " + futurePollution);
            System.out.println("더 작은 크기의 공장을 건설해주세요.");
        }
    }
}