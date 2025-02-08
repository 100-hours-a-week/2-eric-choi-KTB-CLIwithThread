package utill;

public class ThreadedText {
    private static int defaultDelay = Constants.DEFAULT_DELAY;

    // 비동기 출력 - 바로 반환
    public static void printText(String text) {
        new TextyOutput(text, defaultDelay).start();
    }

    // 동기 출력 - 필요한 경우에만 사용 (예: 입력 받기 전)
    public static void printTextSync(String text) {
        TextyOutput thread = new TextyOutput(text, defaultDelay);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printTextSync(String format, Object... args) {
        printTextSync(String.format(format, args));
    }
}