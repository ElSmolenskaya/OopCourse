package ru.academits.smolenskaya.minesweeper.model;

public class HighScoresTableRow {
    private final String gamerName;
    private final int score;
    String defaultName = "Unknown user";

    public HighScoresTableRow(String name, int score) {
        if (name == null || name.trim().length() == 0) {
            this.gamerName = defaultName;
        } else {
            this.gamerName = name.trim();
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