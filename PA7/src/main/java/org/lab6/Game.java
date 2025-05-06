package org.lab6;

import java.util.*;

public class Game {
    public static void main(String[] args) {
        int numPlayers = 3;
        long timeLimit = 30; // 30 secunde de timp pentru joc

        TileBag tileBag = new TileBag();
        Board board = new Board();
        Dictionary dictionary = new Dictionary();
        GameManager manager = new GameManager(numPlayers, timeLimit);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player" + (i + 1), i, tileBag, board, dictionary, manager));
        }

        manager.startGame();

        for (Player player : players) {
            player.start();
        }

        // Cronometrul pentru a opri jocul după un timp limită
        Thread timekeeper = new Thread(() -> {
            try {
                Thread.sleep(timeLimit * 1000); // Așteptăm până trece timpul
                System.out.println("Time limit reached. Stopping game.");
                manager.isGameRunning(); // Opresc jocul
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        timekeeper.setDaemon(true);
        timekeeper.start();

        // Așteptăm ca toate firele de execuție ale jucătorilor să termine
        for (Player player : players) {
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Afișăm scorul final
        Player winner = players.stream().max(Comparator.comparingInt(Player::getScore)).orElse(null);
        if (winner != null) {
            System.out.println("Winner: " + winner.getPlayerName() + " with score: " + winner.getScore());
        } else {
            System.out.println("No winner.");
        }
    }
}
