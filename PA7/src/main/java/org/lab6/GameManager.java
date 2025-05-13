package org.lab6;

public class GameManager {
    private final int numPlayers;
    private int currentTurn;
    private boolean gameRunning;
    private final long timeLimit;
    private long startTime;

    public GameManager(int numPlayers, long timeLimitInSeconds) {
        this.numPlayers = numPlayers;
        this.timeLimit = timeLimitInSeconds * 10000;
        this.gameRunning = true;
        this.currentTurn = 0;
    }

    public synchronized void waitForTurn(int playerIndex) {
        while (playerIndex != currentTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void endTurn() {
        currentTurn = (currentTurn + 1) % numPlayers;
        notifyAll();
    }

    public synchronized boolean isGameRunning() {
        if (!gameRunning) return false;

        long elapsedTime = System.currentTimeMillis() - startTime;
        if (elapsedTime >= timeLimit) {
            gameRunning = false;
            System.out.println("Time limit reached. Game over.");
        }
        return gameRunning;
    }

    public synchronized void startGame() {
        startTime = System.currentTimeMillis();
        System.out.println("Game started.");
    }

    public synchronized void stopGame() {
        startTime = System.currentTimeMillis();
        System.out.println("Game stopped.");
        gameRunning=false;
    }


    public int getNumPlayers() {
        return numPlayers;
    }
}
