package ru.academits.smolenskaya.minesweeper.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MinesweeperImages {
    private final ImageIcon[] digitsImagesArray;
    private ImageIcon closedCellImage;
    private ImageIcon detonatedMineImage;
    private ImageIcon flagImage;
    private ImageIcon mineImage;
    private ImageIcon mistakenMineImage;
    private ImageIcon questionImage;
    private ImageIcon newGameImage;
    private ImageIcon wonGameImage;
    private ImageIcon failedGameImage;
    private ImageIcon gamerImage;
    private ImageIcon aboutImage;

    public MinesweeperImages(int cellButtonSize, int gameStateButtonSize) {
        digitsImagesArray = new ImageIcon[9];

        try {
            for (int i = 0; i <= 8; i++) {
                digitsImagesArray[i] = getImageFromFile(i + ".png", cellButtonSize);
            }

            closedCellImage = getImageFromFile("closed.png", cellButtonSize);

            detonatedMineImage = getImageFromFile("detonatedMine.png", cellButtonSize);

            flagImage = getImageFromFile("flag.png", cellButtonSize);

            mineImage = getImageFromFile("mine.png", cellButtonSize);

            mistakenMineImage = getImageFromFile("mistakenMine.png", cellButtonSize);

            questionImage = getImageFromFile("question.png", cellButtonSize);

            newGameImage = getImageFromFile("newGame.png", gameStateButtonSize);

            wonGameImage = getImageFromFile("wonGame.png", gameStateButtonSize);

            failedGameImage = getImageFromFile("failedGame.png", gameStateButtonSize);

            gamerImage = getImageFromFile("gamer.png", gameStateButtonSize);

            aboutImage = getImageFromFile("about.png", gameStateButtonSize);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), "An error occurred when reading files with images: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ImageIcon getDigitImage(int digit) {
        if (digit < 0 || digit > 8) {
            throw new IndexOutOfBoundsException("Digit = " + digit + ": digit must be >= 0 and <= 8");
        }

        return digitsImagesArray[digit];
    }

    public ImageIcon getClosedCellImage() {
        return closedCellImage;
    }

    public ImageIcon getDetonatedMineImage() {
        return detonatedMineImage;
    }

    public ImageIcon getFlagImage() {
        return flagImage;
    }

    public ImageIcon getMineImage() {
        return mineImage;
    }

    public ImageIcon getMistakenMineImage() {
        return mistakenMineImage;
    }

    public ImageIcon getQuestionImage() {
        return questionImage;
    }

    public ImageIcon getNewGameImage() {
        return newGameImage;
    }

    public ImageIcon getWonGameImage() {
        return wonGameImage;
    }

    public ImageIcon getFailedGameImage() {
        return failedGameImage;
    }

    public ImageIcon getGamerImage() {
        return gamerImage;
    }

    public ImageIcon getAboutImage() {
        return aboutImage;
    }

    private ImageIcon getImageFromFile(String imageFileName, int size) throws IOException {
        String path = "Minesweeper/src/ru/academits/smolenskaya/minesweeper/resources/images/";

        BufferedImage imageIcon = ImageIO.read(new File(path + imageFileName));

        return new ImageIcon(imageIcon.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }
}