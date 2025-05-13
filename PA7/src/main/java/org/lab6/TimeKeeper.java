package org.lab6;

public class TimeKeeper extends Thread {
    private final int timeLimit;
    private final GameManager manager;

    public TimeKeeper(int timeLimit, GameManager manager) {
        this.timeLimit = timeLimit;
        this.manager = manager;
        setDaemon(true);
    }

    @Override
    public void run() {
        for (int i = 1; i <= timeLimit; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Time elapsed: " + i + "s");
            } catch (InterruptedException e) {
                return;
            }
        }

        System.out.println("Time limit reached. Stopping game...");
        manager.stopGame();
    }
}
