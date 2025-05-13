package org.lab6;

public class Board {
    public synchronized void submitWord(String word, int score) {
        System.out.println("Submitted word: " + word + " with score: " + score);
    }
}
