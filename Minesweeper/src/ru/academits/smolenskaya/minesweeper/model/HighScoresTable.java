package ru.academits.smolenskaya.minesweeper.model;

import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class HighScoresTable {
    private final int maximalRowsCount = 10;
    private final char rowComponentsSeparator = ';';
    private final String path;

    public HighScoresTable(int level) {
        path = "Minesweeper/src/ru/academits/smolenskaya/minesweeper/resources/files/level" + (level + 1) + "Scores.txt";
    }

    private LinkedList<HighScoresTableRow> getRowsFromFile() {
        LinkedList<HighScoresTableRow> resultRows = new LinkedList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String inputRow;

            while ((inputRow = bufferedReader.readLine()) != null) {
                int divisorPosition = inputRow.indexOf(rowComponentsSeparator);

                if (divisorPosition >= 0 && divisorPosition + 1 < inputRow.length()) {
                    String gamerName = inputRow.substring(0, divisorPosition);

                    int score = Integer.parseInt(inputRow.substring(divisorPosition + 1));

                    resultRows.addLast(new HighScoresTableRow(gamerName, score));
                }
            }
        } catch (IOException ignored) {
        }

        return resultRows;
    }

    public boolean isScoreNeedToAdd(int score) {
        List<HighScoresTableRow> rows = getRowsFromFile();

        if (rows.isEmpty() || rows.size() < maximalRowsCount) {
            return true;
        }

        return score < rows.get(rows.size() - 1).getScore();
    }

    public void addScore(String gamerName, int score) {
        List<HighScoresTableRow> rows = getRowsFromFile();

        rows.add(new HighScoresTableRow(gamerName, score));

        rows = rows.stream().sorted(Comparator.comparingInt(HighScoresTableRow::getScore)).toList();

        int tableSize = Math.min(rows.size(), maximalRowsCount);

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(path))) {
            for (int i = 0; i < tableSize; i++) {
                printWriter.println(rows.get(i).getGamerName() + rowComponentsSeparator + rows.get(i).getScore());
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while trying to add a new record to high scores table: " + e.getMessage());
        }
    }

    public Object[][] getRows() {
        LinkedList<HighScoresTableRow> rows = getRowsFromFile();

        if (rows.size() == 0) {
            return null;
        }

        Object[][] objectsArray = new Object[rows.size()][2];

        for (int i = 0; i < rows.size(); i++) {
            objectsArray[i][0] = rows.get(i).getGamerName();
            objectsArray[i][1] = rows.get(i).getScore();
        }

        return objectsArray;
    }
}