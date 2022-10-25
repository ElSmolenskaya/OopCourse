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

        BufferedImage imageIcon;

        try {
            String imagesPath = "Minesweeper/src/ru/academits/smolenskaya/minesweeper/resources/images/";
            for (int i = 0; i <= 8; i++) {
                imageIcon = ImageIO.read(new File(imagesPath + i + ".png"));
                digitsImagesArray[i] = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));
            }

            imageIcon = ImageIO.read(new File(imagesPath + "closed.png"));
            closedCellImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "detonatedMine.png"));
            detonatedMineImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "flag.png"));
            flagImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "mine.png"));
            mineImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "mistakenMine.png"));
            mistakenMineImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "question.png"));
            questionImage = new ImageIcon(imageIcon.getScaledInstance(cellButtonSize, cellButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "newGame.png"));
            newGameImage = new ImageIcon(imageIcon.getScaledInstance(gameStateButtonSize, gameStateButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "wonGame.png"));
            wonGameImage = new ImageIcon(imageIcon.getScaledInstance(gameStateButtonSize, gameStateButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "failedGame.png"));
            failedGameImage = new ImageIcon(imageIcon.getScaledInstance(gameStateButtonSize, gameStateButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "gamer.png"));
            gamerImage = new ImageIcon(imageIcon.getScaledInstance(gameStateButtonSize, gameStateButtonSize, Image.SCALE_SMOOTH));

            imageIcon = ImageIO.read(new File(imagesPath + "about.png"));
            aboutImage = new ImageIcon(imageIcon.getScaledInstance(gameStateButtonSize, gameStateButtonSize, Image.SCALE_SMOOTH));
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
}