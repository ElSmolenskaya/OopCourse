package ru.academits.smolenskaya.minesweeper.model;

public interface MinesweeperSubscriber {
    void modelChanged();

    void timerChanged();

    void enterGamerName();
}
