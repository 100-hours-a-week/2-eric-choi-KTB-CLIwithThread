package utill;

class TextyOutput extends Thread {
    private final String text;
    private final int delay;

    public TextyOutput(String text, int delay) {
        this.text = text;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            // 여러 줄의 출력이 섞이는 것을 막기 위해 한 줄 출력 자체는 동기화
            synchronized(System.out) {
                for (char letter : text.toCharArray()) {
                    System.out.print(letter);
                    Thread.sleep(delay);
                }
                System.out.println();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}