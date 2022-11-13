package ru.academits.smolenskaya.minesweeper.model;

public class HighScoresTableRow {
    private final String gamerName;
    private final int score;

    public HighScoresTableRow(String name, int score) {
        String defaultName = "Unknown user";

        if (name == null || name.trim().length() == 0) {
            gamerName = defaultName;
        } else {
            gamerName = name.trim();
        }

        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getGamerName() {
        return gamerName;
    }
}