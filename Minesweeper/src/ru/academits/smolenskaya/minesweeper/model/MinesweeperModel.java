package ru.academits.smolenskaya.minesweeper.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinesweeperModel implements Minesweeper, TimerSubscriber {
    private final MinesweeperCell[][] minesFieldCells;
    private GameState gameState;
    private final int levelIndex;
    private int needToOpenCellsCount;
    private int markedAsMinedCellsCount;
    private final int maximumSecondsCount = 999;
    private final Timer timer;
    private final HighScoresTable highScoresTable;
    private final Collection<MinesweeperSubscriber> subscribers = new CopyOnWriteArrayList<>();

    public enum GameState {
        NEW, IN_PROGRESS, IS_WON, IS_FAILED
    }

    public record CellCoordinates(int rowNumber, int columnNumber) {
    }

    public record SizeConstants(int rowsCount, int columnsCount, int minesCount) {
    }

    public final SizeConstants[] sizeConstantsByLevel = {
            new SizeConstants(9, 9, 10),
            new SizeConstants(16, 16, 40),
            new SizeConstants(16, 30, 100)
    };

    public MinesweeperModel(int levelIndex) {
        if (levelIndex < 0 || levelIndex >= sizeConstantsByLevel.length) {
            throw new IllegalArgumentException("Level = " + levelIndex + ": level must be >= 0 and <= " + (sizeConstantsByLevel.length - 1));
        }

        this.levelIndex = levelIndex;

        gameState = GameState.NEW;

        timer = new Timer(maximumSecondsCount);
        timer.subscribe(this);

        highScoresTable = new HighScoresTable(levelIndex);

        int rowsCount = sizeConstantsByLevel[levelIndex].rowsCount;
        int columnsCount = sizeConstantsByLevel[levelIndex].columnsCount;
        int minesCount = sizeConstantsByLevel[levelIndex].minesCount;

        needToOpenCellsCount = rowsCount * columnsCount - minesCount;

        minesFieldCells = new MinesweeperCell[rowsCount][columnsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                minesFieldCells[i][j] = new MinesweeperCell(0);
            }
        }
    }

    @Override
    public void openCell(int rowNumber, int columnNumber) {
        if (gameState == GameState.IS_WON || gameState == GameState.IS_FAILED) {
            return;
        }

        checkCellIndexes(rowNumber, columnNumber);

        if (minesFieldCells[rowNumber][columnNumber].getStatus() != MinesweeperCell.Status.CLOSED) {
            return;
        }

        if (gameState == GameState.NEW) {
            setMines(rowNumber, columnNumber);

            gameState = GameState.IN_PROGRESS;

            timer.start();
        }

        if (minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount() > 0) {
            minesFieldCells[rowNumber][columnNumber].setStatus(MinesweeperCell.Status.OPENED);

            needToOpenCellsCount--;

            checkGameState();

            return;
        }

        if (minesFieldCells[rowNumber][columnNumber].isMined()) {
            minesFieldCells[rowNumber][columnNumber].setStatus(MinesweeperCell.Status.MINE_DETONATED);

            gameState = GameState.IS_FAILED;

            timer.stop();

            checkGameState();

            return;
        }

        Deque<CellCoordinates> stack = new LinkedList<>();
        stack.addLast(new CellCoordinates(rowNumber, columnNumber));

        while (!stack.isEmpty()) {
            CellCoordinates cellCoordinates = stack.removeLast();

            CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(cellCoordinates.rowNumber, cellCoordinates.columnNumber);

            for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
                for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                    if (minesFieldCells[i][j].getStatus() == MinesweeperCell.Status.CLOSED) {
                        minesFieldCells[i][j].setStatus(MinesweeperCell.Status.OPENED);

                        needToOpenCellsCount--;

                        if (!minesFieldCells[i][j].isMined() && minesFieldCells[i][j].getNeighboursMinesCount() == 0) {
                            stack.addLast(new CellCoordinates(i, j));
                        }
                    }
                }
            }
        }

        checkGameState();
    }

    @Override
    public void checkCell(int rowNumber, int columnNumber) {
        if (gameState != GameState.IN_PROGRESS) {
            return;
        }

        checkCellIndexes(rowNumber, columnNumber);

        if (minesFieldCells[rowNumber][columnNumber].getStatus() != MinesweeperCell.Status.OPENED ||
                minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount() == 0) {
            return;
        }

        CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(rowNumber, columnNumber);

        int markedAsMinedCellsCount = 0;

        for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
            for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                if (minesFieldCells[i][j].getStatus() == MinesweeperCell.Status.MARKED_AS_MINED) {
                    markedAsMinedCellsCount++;

                    if (markedAsMinedCellsCount > minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount()) {
                        return;
                    }
                }
            }
        }

        if (markedAsMinedCellsCount != minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount()) {
            return;
        }

        boolean isGameFailed = false;

        for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
            for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                if (minesFieldCells[i][j].isMined()) {
                    if (minesFieldCells[i][j].getStatus() != MinesweeperCell.Status.MARKED_AS_MINED) {
                        minesFieldCells[i][j].setStatus(MinesweeperCell.Status.MINE_DETONATED);

                        isGameFailed = true;
                    }
                } else if (minesFieldCells[i][j].getStatus() == MinesweeperCell.Status.MARKED_AS_MINED) {
                    minesFieldCells[i][j].setStatus(MinesweeperCell.Status.MISTAKEN_MINE);
                } else if (minesFieldCells[i][j].getStatus() == MinesweeperCell.Status.CLOSED) {
                    openCell(i, j);
                }
            }
        }

        if (isGameFailed) {
            gameState = GameState.IS_FAILED;

            timer.stop();

            checkGameState();
        }
    }

    @Override
    public MinesweeperCell.Status getCellStatus(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        return minesFieldCells[rowNumber][columnNumber].getStatus();
    }

    @Override
    public int getCellNeighboursMinesCount(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        return minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount();
    }

    @Override
    public boolean isCellMined(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        return minesFieldCells[rowNumber][columnNumber].isMined();
    }

    @Override
    public void markCellAsMined(int rowNumber, int columnNumber) {
        if (gameState == GameState.IS_WON || gameState == GameState.IS_FAILED) {
            return;
        }

        checkCellIndexes(rowNumber, columnNumber);

        minesFieldCells[rowNumber][columnNumber].setStatus(MinesweeperCell.Status.MARKED_AS_MINED);

        markedAsMinedCellsCount++;

        notifySubscribersModelChanged();
    }

    @Override
    public int getNeedToMarkAsMinedCellsCount() {
        return sizeConstantsByLevel[levelIndex].minesCount - markedAsMinedCellsCount;
    }

    @Override
    public int getTimerSecondsCount() {
        return timer.getSecondsCount();
    }

    @Override
    public void markCellAsInQuestion(int rowNumber, int columnNumber) {
        if (gameState == GameState.IS_WON || gameState == GameState.IS_FAILED) {
            return;
        }

        checkCellIndexes(rowNumber, columnNumber);

        if (minesFieldCells[rowNumber][columnNumber].getStatus() == MinesweeperCell.Status.MARKED_AS_MINED) {
            --markedAsMinedCellsCount;
        }

        minesFieldCells[rowNumber][columnNumber].setStatus(MinesweeperCell.Status.MARKED_AS_IN_QUESTION);

        notifySubscribersModelChanged();
    }

    @Override
    public void dropCellMark(int rowNumber, int columnNumber) {
        if (gameState == GameState.IS_WON || gameState == GameState.IS_FAILED) {
            return;
        }

        checkCellIndexes(rowNumber, columnNumber);

        if (minesFieldCells[rowNumber][columnNumber].getStatus() == MinesweeperCell.Status.MARKED_AS_MINED) {
            --markedAsMinedCellsCount;
        }

        minesFieldCells[rowNumber][columnNumber].setStatus(MinesweeperCell.Status.CLOSED);

        notifySubscribersModelChanged();
    }

    @Override
    public int getRowsCount() {
        return sizeConstantsByLevel[levelIndex].rowsCount;
    }

    @Override
    public int getColumnsCount() {
        return sizeConstantsByLevel[levelIndex].columnsCount;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getLevelsCount() {
        return sizeConstantsByLevel.length;
    }

    @Override
    public int getCurrentLevelIndex() {
        return levelIndex;
    }

    @Override
    public LinkedList<HighScoresTableRow> getHighScoresTable() {
        return highScoresTable.getRows();
    }

    @Override
    public void addScoreToHighScoresTable(String gamerName) {
        highScoresTable.addScore(gamerName, timer.getSecondsCount());
    }

    @Override
    public void timerChanged() {
        for (MinesweeperSubscriber subscriber : subscribers) {
            assert subscriber != null;

            subscriber.timerChanged();
        }
    }

    @Override
    public void subscribe(MinesweeperSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("The subscriber " + subscriber + " has already been signed");
        }

        subscribers.add(subscriber);
        notifySubscriberModelChanged(subscriber);
    }

    @Override
    public void unsubscribe(MinesweeperSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (!subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("Unknown subscriber: " + subscriber);
        }

        subscribers.remove(subscriber);
    }

    @Override
    public String getAboutProgramInformation() {
        StringBuilder stringBuilder = new StringBuilder();

        String path = "Minesweeper/src/ru/academits/smolenskaya/minesweeper/resources/files/about.txt";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String inputRow;

            while ((inputRow = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputRow).append(System.lineSeparator());
            }
        } catch (IOException ignored) {
        }

        return stringBuilder.toString();
    }

    private void setMines(int startCellRowNumber, int startCellColumnNumber) {
        int rowsCount = sizeConstantsByLevel[levelIndex].rowsCount;
        int columnsCount = sizeConstantsByLevel[levelIndex].columnsCount;
        int minesCount = sizeConstantsByLevel[levelIndex].minesCount;

        int i = 0;

        Random random = new Random();

        CellCoordinates[] startCellExtremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(startCellRowNumber, startCellColumnNumber);

        while (i < minesCount) {
            int rowNumber = random.nextInt(rowsCount);
            int columnNumber = random.nextInt(columnsCount);

            if (!minesFieldCells[rowNumber][columnNumber].isMined() &&
                    (rowNumber < startCellExtremeCellsCoordinates[0].rowNumber ||
                            rowNumber > startCellExtremeCellsCoordinates[1].rowNumber ||
                            columnNumber < startCellExtremeCellsCoordinates[0].columnNumber ||
                            columnNumber > startCellExtremeCellsCoordinates[1].columnNumber)) {
                minesFieldCells[rowNumber][columnNumber].setMine();

                ++i;
            }
        }

        for (i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (!minesFieldCells[i][j].isMined()) {
                    CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(i, j);

                    int cellMinesCount = 0;

                    for (int k = extremeCellsCoordinates[0].rowNumber; k <= extremeCellsCoordinates[1].rowNumber; k++) {
                        for (int l = extremeCellsCoordinates[0].columnNumber; l <= extremeCellsCoordinates[1].columnNumber; l++) {
                            if ((k != i || l != j) && minesFieldCells[k][l].isMined()) {
                                cellMinesCount++;
                            }
                        }
                    }

                    minesFieldCells[i][j].setNeighboursMinesCount(cellMinesCount);
                }
            }
        }
    }

    private void checkGameState() {
        if (gameState == GameState.IN_PROGRESS) {
            if (needToOpenCellsCount > 0) {
                notifySubscribersModelChanged();

                return;
            }

            gameState = GameState.IS_WON;

            markedAsMinedCellsCount = sizeConstantsByLevel[levelIndex].minesCount;

            timer.stop();
        }

        for (MinesweeperCell[] minesweeperCell : minesFieldCells) {
            for (int i = 0; i < minesFieldCells[0].length; i++) {
                if (gameState == GameState.IS_WON) {
                    if (minesweeperCell[i].isMined()) {
                        minesweeperCell[i].setStatus(MinesweeperCell.Status.MARKED_AS_MINED);
                    }
                } else {
                    if (minesweeperCell[i].isMined()) {
                        if (minesweeperCell[i].getStatus() == MinesweeperCell.Status.CLOSED) {
                            minesweeperCell[i].setStatus(MinesweeperCell.Status.OPENED);
                        }
                    } else if (minesweeperCell[i].getStatus() == MinesweeperCell.Status.MARKED_AS_MINED) {
                        minesweeperCell[i].setStatus(MinesweeperCell.Status.MISTAKEN_MINE);
                    }
                }
            }
        }

        notifySubscribersModelChanged();

        if (gameState == GameState.IS_WON && timer.getSecondsCount() < maximumSecondsCount
                && highScoresTable.isScoreNeedToAdd(timer.getSecondsCount())) {
            for (MinesweeperSubscriber subscriber : subscribers) {
                assert subscriber != null;

                subscriber.enterGamerName();
            }
        }
    }

    private CellCoordinates[] getExtremeNeighbourCellsCoordinates(int rowNumber, int columnNumber) {
        CellCoordinates[] resultCellsCoordinates = new CellCoordinates[2];

        resultCellsCoordinates[0] = new CellCoordinates(Math.max(rowNumber - 1, 0), Math.max(columnNumber - 1, 0));
        resultCellsCoordinates[1] = new CellCoordinates(Math.min(rowNumber + 1, minesFieldCells.length - 1), Math.min(columnNumber + 1, minesFieldCells[0].length - 1));

        return resultCellsCoordinates;
    }

    private void checkCellIndexes(int rowNumber, int columnNumber) {
        if (rowNumber < 0 || rowNumber >= minesFieldCells.length) {
            throw new IndexOutOfBoundsException("RowNumber = " + rowNumber + ": rowNumber must be >= 0 and <= " + (minesFieldCells.length - 1));
        }

        if (columnNumber < 0 || columnNumber >= minesFieldCells[0].length) {
            throw new IndexOutOfBoundsException("ColumnNumber = " + columnNumber + ": columnNumber must be >= 0 and <= " + (minesFieldCells[0].length - 1));
        }
    }

    protected void notifySubscribersModelChanged() {
        for (MinesweeperSubscriber subscriber : subscribers) {
            notifySubscriberModelChanged(subscriber);
        }
    }

    private void notifySubscriberModelChanged(MinesweeperSubscriber subscriber) {
        assert subscriber != null;

        subscriber.modelChanged();
    }
}