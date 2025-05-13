package org.lab6;

import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private final Set<String> validWords;

    public Dictionary() {
        validWords = new HashSet<>();
        // Aici poți adăuga cuvinte sau să încarci dintr-un fișier
        validWords.add("CAT");
        validWords.add("DOG");
        validWords.add("TREE");
        validWords.add("APPLE");
        validWords.add("FISH");
        validWords.add("BIRD");
        // Poți adăuga mai multe cuvinte
    }

    public boolean isValidWord(String word) {
        return validWords.contains(word);
    }
}
