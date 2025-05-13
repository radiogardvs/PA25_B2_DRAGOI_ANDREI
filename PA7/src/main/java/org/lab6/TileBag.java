package org.lab6;

import java.util.*;

public class TileBag {
    private final Map<Character, Integer> tiles;
    private final Random random = new Random();

    public TileBag() {
        tiles = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            tiles.put(c, 10);  // Fiecare literă are 10 piese
        }
    }

    public synchronized List<Character> extractTiles(int count) {
        List<Character> extractedTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (tiles.size() == 0) break;

            List<Character> availableLetters = new ArrayList<>(tiles.keySet());
            char tile = availableLetters.get(random.nextInt(availableLetters.size()));

            extractedTiles.add(tile);
            tiles.put(tile, tiles.get(tile) - 1);
            if (tiles.get(tile) == 0) {
                tiles.remove(tile);
            }
        }
        return extractedTiles;
    }

    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }

    public synchronized int getPoints(char tile) {
        return random.nextInt(10) + 1;  // Punctaj aleator pentru fiecare literă
    }
}
