package ru.academits.smolenskaya.minesweeper.main;

import ru.academits.smolenskaya.minesweeper.gui.MinesweeperFrame;
import ru.academits.smolenskaya.minesweeper.model.MinesweeperModel;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        MinesweeperFrame minesweeperFrame = new MinesweeperFrame(new MinesweeperModel(0));
    }
}