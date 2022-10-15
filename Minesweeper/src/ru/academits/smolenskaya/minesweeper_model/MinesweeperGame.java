package ru.academits.smolenskaya.minesweeper_model;

public interface MinesweeperGame {
    public void openCell(int rowNumber, int columnNumber);

    public boolean isCellOpened(int rowNumber, int columnNumber);

    public void checkCell(int rowNumber, int columnNumber);

    public void openAllMinedCells();

    public void finishGame();

    public int getCellNeighboursMinesCount(int rowNumber, int columnNumber);

    public void markCellAsMined(int rowNumber, int columnNumber);

    public boolean isCellMarkedAsMined(int rowNumber, int columnNumber);

    public void markCellAsInQuestion(int rowNumber, int columnNumber);

    public boolean isCellMarkedAsInQuestion(int rowNumber, int columnNumber);

    public void dropCellMark(int rowNumber, int columnNumber);

    //public boolean isMined(int rowNumber, int columnNumber);
}
