package ru.academits.smolenskaya.minesweeper.controller;

import ru.academits.smolenskaya.minesweeper.model.MinesweeperModel;
import ru.academits.smolenskaya.minesweeper.model.Minesweeper;

public class MinesweeperController {
    public static void openCell(Minesweeper minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.openCell(rowNumber, columnNumber);
    }

    public static void checkCell(Minesweeper minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.checkCell(rowNumber, columnNumber);
    }

    public static Minesweeper startNewGame(int level) {
        return new MinesweeperModel(level);
    }

    public static void markCellAsMined(Minesweeper minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.markCellAsMined(rowNumber, columnNumber);
    }

    public static void markCellAsInQuestion(Minesweeper minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.markCellAsInQuestion(rowNumber, columnNumber);
    }

    public static void dropCellMark(Minesweeper minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.dropCellMark(rowNumber, columnNumber);
    }

    public static void addScoreToHighScoresTable(Minesweeper minesweeperModel, String gamerName) {
        minesweeperModel.addScoreToHighScoresTable(gamerName);
    }
}
