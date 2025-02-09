package utill;

public class ThreadedText {
    private static int defaultDelay = Constants.DEFAULT_DELAY;


    public static void printText(String text) {
        new TextyOutput(text, defaultDelay).start();
    }


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