package ru.academits.smolenskaya.minesweeper.main;

import ru.academits.smolenskaya.minesweeper.model.MinesweeperModel;
import ru.academits.smolenskaya.minesweeper.gui.MinesweeperFrame;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        MinesweeperFrame minesweeperFrame = new MinesweeperFrame(new MinesweeperModel(0));
    }
}