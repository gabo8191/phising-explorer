package presenter;

import java.util.HashMap;

import model.Explorer;
import persistence.Persistence;
import view.View;

public class Presenter {
    private static final String DATA_FILE = "data/phishing.txt";
    private Persistence persistence;
    private View view;
    private Explorer explorer;
    private String data;
    private HashMap<String, Integer> selectedWords;
    private HashMap<String, Integer> foundWords;

    public Presenter() {
        view = new View();
        foundWords = new HashMap<>();
        loadSelectedWords();
        readData();
        explorer = new Explorer();
    }

    public void readData() {
        try {
            persistence = new Persistence(DATA_FILE);
            data = persistence.loadFile().toString().toLowerCase();
        } catch (Exception e) {
            view.showError("Error reading data file");
        }
    }

    public void showResults() {
        foundWords = explorer.validateLines(data, selectedWords);
        if (!foundWords.isEmpty()) {
            view.printData("WORD                    || TIMES FOUND || INDIVIDUAL WEIGHT || TOTAL WEIGHT");
            view.printData("-----------------------------------------||------------------||--------------");
            int totalTimesFound = 0;
            int totalWeight = 0;
            for (String word : foundWords.keySet()) {
                int timesFound = foundWords.get(word);
                int pointsPerWord = selectedWords.get(word);
                int individualWeight = pointsPerWord;
                int totalPoints = timesFound * pointsPerWord;
                view.printData(String.format("%-24s %-14d %-18d %d", word, timesFound, individualWeight, totalPoints));
                totalTimesFound += timesFound;
                totalWeight += totalPoints;
            }
            view.printData("===========================================================");
            view.printData("Total words found:      " + totalTimesFound);
            view.printData("Total weight:           " + totalWeight);

            double percentageThreshold = 0.6;
            double percentage = (double) totalWeight / (double) (totalTimesFound * 30);

            view.printData("===========================================================");
            view.printData("Total weight percentage: " + percentage * 100 + "%");

            if (percentage >= percentageThreshold) {
                view.printData("High risk of phishing detected! Please, be careful");
            } else {
                view.printData("No significant risk of phishing detected");
            }

            view.printData("===========================================================");
        } else {
            view.printData("No phishing words found");
        }
    }

    public void loadSelectedWords() {
        selectedWords = new HashMap<>();
        selectedWords.put("password", 30);
        selectedWords.put("account", 29);
        selectedWords.put("verify", 28);
        selectedWords.put("verify your account", 27);
        selectedWords.put("verify your password", 26);
        selectedWords.put("verify your email", 25);
        selectedWords.put("verify your phone", 24);
        selectedWords.put("credit card", 23);
        selectedWords.put("credit card number", 22);
        selectedWords.put("credit card information", 21);
        selectedWords.put("credit card details", 20);
        selectedWords.put("social security", 19);
        selectedWords.put("social security number", 18);
        selectedWords.put("social security information", 17);
        selectedWords.put("social security details", 16);
        selectedWords.put("bank account", 15);
        selectedWords.put("bank account number", 14);
        selectedWords.put("bank account information", 13);
        selectedWords.put("bank account details", 12);
        selectedWords.put("mother's maiden name", 11);
        selectedWords.put("mother's maiden name information", 10);
        selectedWords.put("mother's maiden name details", 9);
        selectedWords.put("username", 8);
        selectedWords.put("username information", 7);
        selectedWords.put("username details", 6);
        selectedWords.put("user name", 5);
        selectedWords.put("user name information", 4);
        selectedWords.put("user name details", 3);
        selectedWords.put("login", 2);
        selectedWords.put("login information", 1);
        selectedWords.put("login details", 1);
    }

    public static void main(String[] args) {
        Presenter presenter = new Presenter();
        presenter.showResults();
    }
}
