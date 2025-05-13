package org.lab6;

import java.util.*;

public class Player extends Thread {
    private final String name;
    private final int index;
    private final TileBag tileBag;
    private final Board board;
    private final Dictionary dictionary;
    private final GameManager manager;
    private final List<Character> tiles = new ArrayList<>();
    private int score = 0;

    public Player(String name, int index, TileBag tileBag, Board board, Dictionary dictionary, GameManager manager) {
        this.name = name;
        this.index = index;
        this.tileBag = tileBag;
        this.board = board;
        this.dictionary = dictionary;
        this.manager = manager;
    }

    @Override
    public void run() {
        System.out.println(name + " thread started.");
        tiles.addAll(tileBag.extractTiles(7));

        while (manager.isGameRunning() && !tileBag.isEmpty()) {
            manager.waitForTurn(index);

            if (!manager.isGameRunning()) break;

            System.out.println(name + "'s turn with tiles: " + tiles);
            String word = findWord();

            if (word != null) {
                int wordPoints = word.chars().map(c -> tileBag.getPoints((char) c)).sum();
                board.submitWord(word, wordPoints);
                score += wordPoints;

                for (char c : word.toCharArray()) {
                    tiles.remove((Character) c);
                }

                tiles.addAll(tileBag.extractTiles(word.length()));
                System.out.println(name + " formed word: " + word + " (+ " + wordPoints + " pts)");
            } else {
                System.out.println(name + " could not form a word, discarding all tiles.");
                tiles.clear();
                tiles.addAll(tileBag.extractTiles(7));
            }

            manager.endTurn();
        }

        System.out.println(name + " thread finished.");
    }

    private String findWord() {
        List<Character> copy = new ArrayList<>(tiles);
        for (int i = 2; i <= copy.size(); i++) {
            List<List<Character>> permutations = generatePermutations(copy, i);
            for (List<Character> perm : permutations) {
                String word = buildWord(perm);
                if (dictionary.isValidWord(word)) return word;
            }
        }
        return null;
    }

    private List<List<Character>> generatePermutations(List<Character> letters, int length) {
        List<List<Character>> results = new ArrayList<>();
        permute(letters, 0, length, results);
        return results;
    }

    private void permute(List<Character> arr, int depth, int length, List<List<Character>> results) {
        if (depth == length) {
            results.add(new ArrayList<>(arr.subList(0, length)));
            return;
        }
        for (int i = depth; i < arr.size(); i++) {
            Collections.swap(arr, i, depth);
            permute(arr, depth + 1, length, results);
            Collections.swap(arr, i, depth);
        }
    }

    private String buildWord(List<Character> letters) {
        StringBuilder sb = new StringBuilder();
        for (char c : letters) sb.append(c);
        return sb.toString();
    }

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return name;
    }
}
