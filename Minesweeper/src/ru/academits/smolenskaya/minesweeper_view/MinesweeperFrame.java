package ru.academits.smolenskaya.minesweeper_view;

import ru.academits.smolenskaya.minesweeper_controller.MinesweeperController;
import ru.academits.smolenskaya.minesweeper_model.MinesweeperModel;
import ru.academits.smolenskaya.minesweeper_model.MinesweeperModelSubscriber;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesweeperFrame implements MinesweeperModelSubscriber {
    private MinesweeperModel minesweeperModel;
    private MinesweeperController minesweeperController;
    private final int cellSize = 45;
    private final int rowsCount = 9;
    private final int columnsCount = 9;
    private JButton[][] buttonsArray;

    public MinesweeperFrame() {
        minesweeperModel = new MinesweeperModel(0);
        subscribe();

        minesweeperController = new MinesweeperController();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame minesweeperFrame = new JFrame("Minesweeper");
            minesweeperFrame.setLocationRelativeTo(null);
            minesweeperFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            minesweeperFrame.setVisible(true);

            minesweeperFrame.setLayout(new GridBagLayout());

            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            JMenuBar minesweeperMenuBar = getMainMenu();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            minesweeperFrame.add(minesweeperMenuBar, gridBagConstraints);

            JPanel gameStatePanel = getGameStatePanel();
            gridBagConstraints.gridy = 1;
            minesweeperFrame.add(gameStatePanel, gridBagConstraints);

            JPanel gameFieldPanel = getGameFieldPanel();
            gridBagConstraints.gridy = 2;
            minesweeperFrame.add(gameFieldPanel, gridBagConstraints);

            minesweeperFrame.pack();
        });
    }

    private JPanel getGameFieldPanel() {
        GridLayout gridLayout = new GridLayout(rowsCount, columnsCount);
        JPanel gameFieldPanel = new JPanel(gridLayout);

        buttonsArray = new JButton[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                buttonsArray[i][j] = new JButton("");
                buttonsArray[i][j].setPreferredSize(new Dimension(cellSize, cellSize));

                int finalJ = j;
                int finalI = i;

                buttonsArray[i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        //JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), e.getButton());

                        if (e.getButton() == MouseEvent.BUTTON3) {
                            setNextCellStatus(finalI, finalJ);
                        } else if (e.getButton() == MouseEvent.BUTTON1) {
                            minesweeperController.openCell(minesweeperModel, finalI, finalJ);
                        } else if (e.getButton() == MouseEvent.BUTTON2) {
                            minesweeperController.checkCell(minesweeperModel, finalI, finalJ);
                        }
                    }
                });


                gameFieldPanel.add(buttonsArray[i][j]);
            }
        }

        return gameFieldPanel;
    }

    private void setNextCellStatus(int rowNumber, int columnNumber) {
        switch (minesweeperModel.getCellStatus(rowNumber, columnNumber)) {
            case CLOSED -> minesweeperController.markCellAsMined(minesweeperModel, rowNumber, columnNumber);
            case MARKED_AS_MINED ->
                    minesweeperController.markCellAsInQuestion(minesweeperModel, rowNumber, columnNumber);
            case MARKED_AS_IN_QUESTION -> minesweeperController.dropCellMark(minesweeperModel, rowNumber, columnNumber);
        }
    }

    private JMenuBar getMainMenu() {
        JMenu mainMenu = new JMenu("Menu");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        mainMenu.add(exitMenuItem);
        JMenuItem aboutMenuItem = new JMenuItem("About");
        mainMenu.add(aboutMenuItem);
        JMenuItem newGameMenuItem = new JMenuItem("New game");
        mainMenu.add(newGameMenuItem);
        JMenuItem highScoresMenuItem = new JMenuItem("High scores");
        mainMenu.add(highScoresMenuItem);

        JMenuBar minesweeperMenuBar = new JMenuBar();
        minesweeperMenuBar.add(mainMenu);
        minesweeperMenuBar.setPreferredSize(new Dimension(rowsCount * cellSize, 20));
        minesweeperMenuBar.setVisible(true);

        return minesweeperMenuBar;
    }

    private JPanel getGameStatePanel() {
        JPanel gameStatePanel = new JPanel();
        gameStatePanel.setPreferredSize(new Dimension(rowsCount * cellSize, 50));
        gameStatePanel.setVisible(true);
        gameStatePanel.setBorder(BasicBorders.getSplitPaneBorder());

        return gameStatePanel;
    }

    protected MinesweeperModel getMinesweeperModel() {
        return minesweeperModel;
    }

    public void setModel(MinesweeperModel model) {
        unsubscribe();

        minesweeperModel = model;

        subscribe();
    }

    private void subscribe() {
        if (minesweeperModel != null)
            minesweeperModel.subscribe(this);
    }

    private void unsubscribe() {
        if (minesweeperModel != null)
            minesweeperModel.unsubscribe(this);
    }

    public void dispose() {
        unsubscribe();
    }

    @Override
    public void modelChanged() {
        if (buttonsArray == null) {
            return;
        }

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                switch (minesweeperModel.getCellStatus(i, j)) {
                    case OPENED -> {
                        buttonsArray[i][j].setEnabled(false);
                        int minesCount = minesweeperModel.getCellNeighboursMinesCount(i, j);
                        if (minesCount > 0) {
                            buttonsArray[i][j].setText(String.valueOf(minesCount));
                        }
                    }
                    case CLOSED -> buttonsArray[i][j].setText("");
                    case MARKED_AS_MINED -> buttonsArray[i][j].setText("P");
                    case MARKED_AS_IN_QUESTION -> buttonsArray[i][j].setText("?");
                    case MINE_SWEPT -> {
                        buttonsArray[i][j].setEnabled(false);
                        buttonsArray[i][j].setText("X");
                    }
                    case MINE_DETONATED -> {
                        buttonsArray[i][j].setEnabled(false);
                        buttonsArray[i][j].setText("!");
                    }
                }
            }
        }
    }
}
