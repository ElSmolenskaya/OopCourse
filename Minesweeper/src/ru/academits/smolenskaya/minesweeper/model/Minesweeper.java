package ru.academits.smolenskaya.minesweeper.model;

public interface Minesweeper {
    int getRowsCount();

    int getColumnsCount();

    MinesweeperModel.GameState getGameState();

    int getCellNeighboursMinesCount(int rowNumber, int columnNumber);

    void openCell(int rowNumber, int columnNumber);

    void checkCell(int rowNumber, int columnNumber);

    void markCellAsMined(int rowNumber, int columnNumber);

    void markCellAsInQuestion(int rowNumber, int columnNumber);

    void dropCellMark(int rowNumber, int columnNumber);

    MinesweeperCell.Status getCellStatus(int rowNumber, int columnNumber);

    void unsubscribe(MinesweeperSubscriber subscriber);

    void subscribe(MinesweeperSubscriber subscriber);

    int getLevelsCount();

    int getCurrentLevelIndex();

    int getNeedToMarkAsMinedCellsCount();

    int getTimerSecondsCount();

    Object[][] getHighScoresTable();

    void addScoreToHighScoresTable(String gamerName);

    String getAboutProgramInformation();
}