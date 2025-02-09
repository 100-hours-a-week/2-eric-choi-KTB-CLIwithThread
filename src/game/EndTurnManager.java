package game;

import utill.ThreadedText;
import utill.Constants;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class EndTurnManager {
    private City city;
    private EconomyManager economyManager;
    private Random random;

    public EndTurnManager(City city, EconomyManager economyManager) {
        this.city = city;
        this.economyManager = economyManager;
        this.random = new Random();
    }

    private class CollectTaxRunnable implements Runnable {
        private final CountDownLatch latch;

        public CollectTaxRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                economyManager.collectTaxes();
            } finally {
                latch.countDown();
            }
        }
    }

    private class MaintenanceCostRunnable implements Runnable {
        private final CountDownLatch latch;

        public MaintenanceCostRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                economyManager.maintenanceCost();
            } finally {
                latch.countDown();
            }
        }
    }

    private class PopulationGrowRunnable implements Runnable {
        private final CountDownLatch latch;

        public PopulationGrowRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                int growth = (int)(city.getPopulation() * 0.05);
                if (growth > 0) {
                    city.addPopulation(growth);
                    ThreadedText.printText(String.format("\n인구가 %d명 증가했습니다.\n", growth));
                }
            } finally {
                latch.countDown();
            }
        }
    }

    private class RandomEventRunnable implements Runnable {
        private final CountDownLatch latch;

        public RandomEventRunnable(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                if (random.nextInt(100) < Constants.EVENT_TRIGGER_CHANCE) {
                    EventManager event = EventManager.getRandomEvent();
                    ThreadedText.printTextSync("\n[이벤트] " + event.getScript());
                    event.eventEffect(city);
                }
            } finally {
                latch.countDown();
            }
        }
    }

    public void processEndTurn() {
        CountDownLatch latch = new CountDownLatch(4);

        Thread taxThread = new Thread(new CollectTaxRunnable(latch));
        Thread maintenanceThread = new Thread(new MaintenanceCostRunnable(latch));
        Thread populationThread = new Thread(new PopulationGrowRunnable(latch));
        Thread eventThread = new Thread(new RandomEventRunnable(latch));

        taxThread.start();
        maintenanceThread.start();
        populationThread.start();
        eventThread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Turn processing interrupted", e);
        }
    }
}