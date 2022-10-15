package ru.academits.smolenskaya.minesweeper_controller;

import ru.academits.smolenskaya.minesweeper_model.MinesweeperModel;

public class MinesweeperController {
    public void openCell(MinesweeperModel minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.openCell(rowNumber, columnNumber);
    }

    public void checkCell(MinesweeperModel minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.checkCell(rowNumber, columnNumber);
    }

    public void finishGame() {

    }

    public void markCellAsMined(MinesweeperModel minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.markCellAsMined(rowNumber, columnNumber);
    }

    public void markCellAsInQuestion(MinesweeperModel minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.markCellAsInQuestion(rowNumber, columnNumber);
    }

    public void dropCellMark(MinesweeperModel minesweeperModel, int rowNumber, int columnNumber) {
        minesweeperModel.dropCellMark(rowNumber, columnNumber);
    }
}
