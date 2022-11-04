package ru.academits.smolenskaya.minesweeper.gui;

import ru.academits.smolenskaya.minesweeper.controller.MinesweeperController;
import ru.academits.smolenskaya.minesweeper.model.Minesweeper;
import ru.academits.smolenskaya.minesweeper.model.MinesweeperSubscriber;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MinesweeperFrame implements MinesweeperSubscriber {
    private Minesweeper minesweeperModel;
    private int levelIndex;
    private final int cellButtonSize = 25;
    private final int gameStateButtonSize = 35;
    private int rowsCount;
    private int columnsCount;
    private final MinesweeperImages images;
    private JButton gameStateButton;
    private JButton[][] buttonsArray;
    private JTextField needToMarkCellsCountTextField;
    private JTextField timerTextField;
    private JFrame minesweeperFrame;

    public MinesweeperFrame(Minesweeper minesweeperModel) {
        this.minesweeperModel = minesweeperModel;
        subscribe();

        levelIndex = minesweeperModel.getCurrentLevelIndex();

        images = new MinesweeperImages(cellButtonSize, gameStateButtonSize);

        rowsCount = minesweeperModel.getRowsCount();
        columnsCount = minesweeperModel.getColumnsCount();

        initMinesweeperFrameComponents();
    }

    private void initMinesweeperFrameComponents() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            minesweeperFrame = new JFrame("Minesweeper");
            minesweeperFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            minesweeperFrame.setLayout(new GridBagLayout());

            JMenuBar minesweeperMenuBar = getMainMenu();
            minesweeperFrame.setJMenuBar(minesweeperMenuBar);

            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            JPanel gameStatePanel = getGameStatePanel();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            minesweeperFrame.add(gameStatePanel, gridBagConstraints);

            JPanel gameFieldPanel = getGameFieldPanel();
            gridBagConstraints.gridy = 1;
            minesweeperFrame.add(gameFieldPanel, gridBagConstraints);

            minesweeperFrame.pack();
            minesweeperFrame.setMinimumSize(new Dimension(minesweeperFrame.getPreferredSize().width, minesweeperFrame.getPreferredSize().height));
            minesweeperFrame.setLocationRelativeTo(null);
            minesweeperFrame.setVisible(true);
        });
    }

    private JPanel getGameFieldPanel() {
        GridLayout gridLayout = new GridLayout(rowsCount, columnsCount);
        JPanel gameFieldPanel = new JPanel(gridLayout);

        buttonsArray = new JButton[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                buttonsArray[i][j] = new JButton();
                buttonsArray[i][j].setPreferredSize(new Dimension(cellButtonSize, cellButtonSize));
                buttonsArray[i][j].setIcon(images.getClosedCellImage());

                int fixedI = i;
                int fixedJ = j;

                buttonsArray[i][j].addMouseListener(new MouseAdapter() {
                    private boolean isLeftButtonPressed = false;
                    private boolean isRightButtonPressed = false;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            isLeftButtonPressed = true;
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            isRightButtonPressed = true;
                        } else if (e.getButton() == MouseEvent.BUTTON2) {
                            MinesweeperController.checkCell(minesweeperModel, fixedI, fixedJ);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (isLeftButtonPressed && isRightButtonPressed) {
                            MinesweeperController.checkCell(minesweeperModel, fixedI, fixedJ);

                            isLeftButtonPressed = false;
                            isRightButtonPressed = false;
                        } else if (isLeftButtonPressed) {
                            MinesweeperController.openCell(minesweeperModel, fixedI, fixedJ);

                            isLeftButtonPressed = false;
                        } else if (isRightButtonPressed) {
                            setNextCellStatus(fixedI, fixedJ);

                            isRightButtonPressed = false;
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
            case CLOSED -> MinesweeperController.markCellAsMined(minesweeperModel, rowNumber, columnNumber);
            case MARKED_AS_MINED ->
                    MinesweeperController.markCellAsInQuestion(minesweeperModel, rowNumber, columnNumber);
            case MARKED_AS_IN_QUESTION -> MinesweeperController.dropCellMark(minesweeperModel, rowNumber, columnNumber);
        }
    }

    private JMenuBar getMainMenu() {
        JMenu mainMenu = new JMenu("Menu");

        JMenu levelMenu = new JMenu("Level");
        mainMenu.add(levelMenu);

        JMenuItem[] levelMenuItems = new JMenuItem[minesweeperModel.getLevelsCount()];

        for (int i = 0; i < minesweeperModel.getLevelsCount(); i++) {
            levelMenuItems[i] = new JMenuItem("Level " + (i + 1));
            levelMenu.add(levelMenuItems[i]);

            int fixedI = i;

            levelMenuItems[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (minesweeperModel.getCurrentLevelIndex() != fixedI) {
                        setModel(MinesweeperController.startNewGame(fixedI));
                    }
                }
            });
        }

        levelMenuItems[minesweeperModel.getCurrentLevelIndex()].setBackground(Color.LIGHT_GRAY);

        JMenuItem newGameMenuItem = new JMenuItem("New game");

        newGameMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setModel(MinesweeperController.startNewGame(minesweeperModel.getCurrentLevelIndex()));
                timerTextField.setText(String.valueOf(minesweeperModel.getTimerSecondsCount()));
            }
        });

        mainMenu.add(newGameMenuItem);

        JMenuItem highScoresMenuItem = new JMenuItem("High scores");

        highScoresMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Object[][] highScoresList = minesweeperModel.getHighScoresTable();

                if (highScoresList == null) {
                    showEmptyHighScoresTableDialog();
                } else {
                    showHighScoresTableDialog(highScoresList);
                }
            }
        });

        mainMenu.add(highScoresMenuItem);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        mainMenu.add(aboutMenuItem);

        aboutMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showAboutDialog();
            }
        });

        mainMenu.addSeparator();

        JMenuItem exitMenuItem = new JMenuItem("Exit");

        exitMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showExitDialog();
            }
        });

        mainMenu.add(exitMenuItem);

        JMenuBar minesweeperMenuBar = new JMenuBar();
        minesweeperMenuBar.add(mainMenu);
        minesweeperMenuBar.setPreferredSize(new Dimension(columnsCount * cellButtonSize, 20));
        minesweeperMenuBar.setVisible(true);

        return minesweeperMenuBar;
    }

    private JPanel getGameStatePanel() {
        JPanel gameStatePanel = new JPanel();
        gameStatePanel.setPreferredSize(new Dimension(rowsCount * cellButtonSize, 50));
        gameStatePanel.setVisible(true);
        gameStatePanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 0, 10);
        gridBagConstraints.gridy = 0;

        needToMarkCellsCountTextField = new JTextField();
        needToMarkCellsCountTextField.setText(String.valueOf(minesweeperModel.getNeedToMarkAsMinedCellsCount()));
        needToMarkCellsCountTextField.setPreferredSize(new Dimension(40, gameStateButtonSize));
        needToMarkCellsCountTextField.setFont(new Font("Serif", Font.BOLD, 20));
        needToMarkCellsCountTextField.setForeground(Color.RED);
        needToMarkCellsCountTextField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        needToMarkCellsCountTextField.setHorizontalAlignment(SwingConstants.CENTER);
        needToMarkCellsCountTextField.setVisible(true);
        needToMarkCellsCountTextField.setEditable(false);
        gridBagConstraints.gridx = 0;
        gameStatePanel.add(needToMarkCellsCountTextField, gridBagConstraints);

        gameStateButton = new JButton();
        gameStateButton.setPreferredSize(new Dimension(gameStateButtonSize, gameStateButtonSize));
        gameStateButton.setIcon(images.getNewGameImage());
        gameStateButton.setVisible(true);
        gridBagConstraints.gridx = 1;
        gameStatePanel.add(gameStateButton, gridBagConstraints);

        timerTextField = new JTextField();
        timerTextField.setText(String.valueOf(minesweeperModel.getTimerSecondsCount()));
        timerTextField.setPreferredSize(new Dimension(40, gameStateButtonSize));
        timerTextField.setFont(new Font("Serif", Font.BOLD, 20));
        timerTextField.setForeground(Color.RED);
        timerTextField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
        timerTextField.setHorizontalAlignment(SwingConstants.CENTER);
        timerTextField.setVisible(true);
        timerTextField.setEditable(false);
        gridBagConstraints.gridx = 2;
        gameStatePanel.add(timerTextField, gridBagConstraints);

        gameStateButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                setModel(MinesweeperController.startNewGame(minesweeperModel.getCurrentLevelIndex()));
                timerTextField.setText(String.valueOf(minesweeperModel.getTimerSecondsCount()));
            }
        });

        return gameStatePanel;
    }

    @SuppressWarnings("unused")
    protected Minesweeper getMinesweeperModel() {
        return minesweeperModel;
    }

    public void setModel(Minesweeper model) {
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

    @SuppressWarnings("unused")
    public void dispose() {
        unsubscribe();
    }

    @Override
    public void modelChanged() {
        if (minesweeperModel.getCurrentLevelIndex() != levelIndex) {
            minesweeperFrame.dispose();

            initMinesweeperFrameComponents();

            levelIndex = minesweeperModel.getCurrentLevelIndex();

            rowsCount = minesweeperModel.getRowsCount();
            columnsCount = minesweeperModel.getColumnsCount();

            return;
        }

        if (buttonsArray == null) {
            return;
        }

        needToMarkCellsCountTextField.setText(String.valueOf(minesweeperModel.getNeedToMarkAsMinedCellsCount()));

        switch (minesweeperModel.getGameState()) {
            case IS_WON -> gameStateButton.setIcon(images.getWonGameImage());
            case IS_FAILED -> gameStateButton.setIcon(images.getFailedGameImage());
            default -> gameStateButton.setIcon(images.getNewGameImage());
        }

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                switch (minesweeperModel.getCellStatus(i, j)) {
                    case OPENED -> {
                        int minesCount = minesweeperModel.getCellNeighboursMinesCount(i, j);

                        if (minesCount < 0) {
                            buttonsArray[i][j].setIcon(images.getMineImage());
                        } else {
                            buttonsArray[i][j].setIcon(images.getDigitImage(minesCount));
                        }
                    }
                    case CLOSED -> buttonsArray[i][j].setIcon(images.getClosedCellImage());
                    case MARKED_AS_MINED -> buttonsArray[i][j].setIcon(images.getFlagImage());
                    case MARKED_AS_IN_QUESTION -> buttonsArray[i][j].setIcon(images.getQuestionImage());
                    case MISTAKEN_MINE -> buttonsArray[i][j].setIcon(images.getMistakenMineImage());
                    case MINE_DETONATED -> buttonsArray[i][j].setIcon(images.getDetonatedMineImage());
                }
            }
        }
    }

    @Override
    public void timerChanged() {
        timerTextField.setText(String.valueOf(minesweeperModel.getTimerSecondsCount()));
    }

    @Override
    public void enterGamerName() {
        String gamerName = getGamerNameFromDialog();

        if (gamerName != null && !gamerName.isEmpty()) {
            MinesweeperController.addScoreToHighScoresTable(minesweeperModel, gamerName);
        }
    }

    private String getGamerNameFromDialog() {
        String gamerName;

        final int maximumNameLength = 30;

        while (true) {
            gamerName = Objects.requireNonNullElse(JOptionPane.showInputDialog(new JPanel(new FlowLayout()),
                    "You have made a new record! Your score is " + minesweeperModel.getTimerSecondsCount() + " sec." + System.lineSeparator()
                            + "Please, enter your name to complete high scores table: ", "Gamer information",
                    JOptionPane.WARNING_MESSAGE,
                    images.getGamerImage(), null, ""), "").toString();

            if (gamerName == null || gamerName.trim().length() <= maximumNameLength) {
                break;
            }

            JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), "Gamer name length mustn't be longer than 20 symbols!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return gamerName;
    }

    private void showExitDialog() {
        int needExit = JOptionPane.showConfirmDialog(new JPanel(new FlowLayout()), "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

        if (needExit == 0) {
            minesweeperFrame.dispose();

            System.exit(0);
        }
    }

    private void showEmptyHighScoresTableDialog() {
        JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), "There is no any score record. Be the first!",
                "High scores", JOptionPane.INFORMATION_MESSAGE, images.getGamerImage());
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(new JPanel(new FlowLayout()), minesweeperModel.getAboutProgramInformation(),
                "About", JOptionPane.INFORMATION_MESSAGE, images.getAboutImage());
    }

    private void showHighScoresTableDialog(Object[][] data) {
        String[] columnsHeaders = new String[]{"Name", "Score"};

        JTable table = new JTable(data, columnsHeaders) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setVisible(true);

        TableColumn gamerNameColumn = table.getColumnModel().getColumn(0);
        gamerNameColumn.setMinWidth(200);
        gamerNameColumn.setMaxWidth(200);
        gamerNameColumn.setPreferredWidth(200);

        TableColumn scoreColumn = table.getColumnModel().getColumn(1);
        scoreColumn.setMinWidth(50);
        scoreColumn.setMaxWidth(50);
        scoreColumn.setPreferredWidth(50);

        JPanel tablePanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 0, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;

        tablePanel.add(table.getTableHeader(), constraints);

        constraints.insets = new Insets(2, 10, 0, 10);
        constraints.gridy = 1;
        tablePanel.add(table, constraints);

        JDialog highScoresTableDialog = new JDialog(minesweeperFrame, "High scores");
        highScoresTableDialog.setLayout(new BorderLayout());

        highScoresTableDialog.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                highScoresTableDialog.setVisible(false);
                highScoresTableDialog.removeAll();
                highScoresTableDialog.dispose();
            }
        });

        buttonsPanel.add(okButton);

        highScoresTableDialog.getRootPane().setDefaultButton(okButton);
        highScoresTableDialog.add(buttonsPanel, BorderLayout.SOUTH);

        highScoresTableDialog.pack();
        highScoresTableDialog.setResizable(false);
        highScoresTableDialog.setLocationRelativeTo(null);
        highScoresTableDialog.setVisible(true);
    }
}