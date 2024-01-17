package model;

import java.util.HashMap;

public class Explorer {

    public Explorer() {
    }

    public HashMap<String, Integer> validateLines(String text, HashMap<String, Integer> selectedWords) {
        HashMap<String, Integer> foundWords = new HashMap<>();

        for (String word : selectedWords.keySet()) {
            int timesFound = countOccurrences(text, word);
            if (timesFound > 0) {
                foundWords.put(word, timesFound);
            }
        }

        return foundWords;
    }

    private int countOccurrences(String text, String word) {
        int timesFound = 0;
        int index = 0;

        while (index != -1) {
            index = text.indexOf(word, index);

            if (index != -1) {
                timesFound++;
                index += word.length();
            }
        }

        return timesFound;
    }

}
