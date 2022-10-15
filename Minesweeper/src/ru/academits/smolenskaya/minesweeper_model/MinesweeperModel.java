package ru.academits.smolenskaya.minesweeper_model;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinesweeperModel {
    private final MinesweeperCell[][] minesFieldCells;
    private GameState gameState;
    private final int level;
    private int openedCellsCount;

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

    private final Collection<MinesweeperModelSubscriber> subscribers = new CopyOnWriteArrayList<MinesweeperModelSubscriber>();

    public MinesweeperModel(int level) {
        gameState = GameState.NEW;

        this.level = level;

        if (level < 0 || level > 2) {
            throw new IllegalArgumentException("Level = " + level + ": level must be >= 0 and <= 2");
        }

        minesFieldCells = new MinesweeperCell[sizeConstantsByLevel[level].rowsCount][sizeConstantsByLevel[level].columnsCount];

        for (int i = 0; i < sizeConstantsByLevel[level].rowsCount; i++) {
            for (int j = 0; j < sizeConstantsByLevel[level].columnsCount; j++) {
                minesFieldCells[i][j] = new MinesweeperCell(0);
            }
        }

        int i = 0;

        Random random = new Random();

        while (i < sizeConstantsByLevel[level].minesCount) {
            int rowNumber = random.nextInt(sizeConstantsByLevel[level].rowsCount);
            int columnNumber = random.nextInt(sizeConstantsByLevel[level].columnsCount);

            if (!minesFieldCells[rowNumber][columnNumber].isMined()) {
                minesFieldCells[rowNumber][columnNumber].setMine();

                ++i;
            }
        }

        for (i = 0; i < sizeConstantsByLevel[level].rowsCount; i++) {
            for (int j = 0; j < sizeConstantsByLevel[level].columnsCount; j++) {
                if (!minesFieldCells[i][j].isMined()) {
                    CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(i, j);

                    int cellMinesCount = 0;

                    for (int k = extremeCellsCoordinates[0].rowNumber; k <= extremeCellsCoordinates[1].rowNumber; k++) {
                        for (int l = extremeCellsCoordinates[0].columnNumber; l <= extremeCellsCoordinates[1].columnNumber; l++) {
                            if (!(k == i && l == j) && minesFieldCells[k][l].isMined()) {
                                cellMinesCount++;
                            }
                        }
                    }

                    minesFieldCells[i][j].setNeighboursMinesCount(cellMinesCount);
                }
            }
        }
    }

    public void openCell(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        if (minesFieldCells[rowNumber][columnNumber].getStatus() != MinesweeperCell.Status.CLOSED) {
            return;
        }

        if (gameState == GameState.NEW) {
            gameState = GameState.IN_PROGRESS;
        }

        if (minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount() != 0) {
            minesFieldCells[rowNumber][columnNumber].open();

            if (minesFieldCells[rowNumber][columnNumber].getStatus() == MinesweeperCell.Status.MINE_DETONATED){
                gameState = GameState.IS_FAILED;
            }

            openedCellsCount++;

            notifySubscribers();

            return;
        }

        Deque<CellCoordinates> stack = new LinkedList<>();
        stack.addLast(new CellCoordinates(rowNumber, columnNumber));

        while (!stack.isEmpty()) {
            CellCoordinates cellCoordinates = stack.removeLast();

            minesFieldCells[cellCoordinates.rowNumber][cellCoordinates.columnNumber].open();

            openedCellsCount++;

            /*if (isGameOver()) {
                return;
            }*/

            CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(cellCoordinates.rowNumber, cellCoordinates.columnNumber);

            for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
                for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                    if (!minesFieldCells[i][j].isOpened() && !minesFieldCells[i][j].isMarkedAsMined() && !minesFieldCells[i][j].isMarkedAsInQuestion()) {
                        if (minesFieldCells[i][j].getNeighboursMinesCount() > 0) {
                            minesFieldCells[i][j].open();

                            openedCellsCount++;

                            /*if (isGameOver()) {
                                return;
                            }*/
                        } else if (minesFieldCells[i][j].getNeighboursMinesCount() == 0 &&
                                !minesFieldCells[rowNumber][columnNumber].isMarkedAsMined() &&
                                !minesFieldCells[rowNumber][columnNumber].isMarkedAsInQuestion()) {
                            stack.addLast(new CellCoordinates(i, j));
                        }
                    }
                }
            }
        }

        notifySubscribers();
    }

    public void checkCell(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        CellCoordinates[] extremeCellsCoordinates = getExtremeNeighbourCellsCoordinates(rowNumber, columnNumber);

        int markedAsMinedCellsCount = 0;

        for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
            for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                if (minesFieldCells[i][j].isMarkedAsMined()) {
                    markedAsMinedCellsCount++;

                    if (markedAsMinedCellsCount > minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount()) {
                        return;
                    }
                }
            }
        }

        if (markedAsMinedCellsCount < minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount()) {
            return;
        }

        for (int i = extremeCellsCoordinates[0].rowNumber; i <= extremeCellsCoordinates[1].rowNumber; i++) {
            for (int j = extremeCellsCoordinates[0].columnNumber; j <= extremeCellsCoordinates[1].columnNumber; j++) {
                if (minesFieldCells[i][j].isMined()) {
                    if (!minesFieldCells[i][j].isMarkedAsMined()) {
                        gameState = GameState.IS_FAILED;
                    }

                    minesFieldCells[i][j].open();
                } else {
                    openCell(i, j);
                }
            }
        }

        notifySubscribers();
    }

    public void openAllMinedCells() {
        for (MinesweeperCell[] minesweeperCell : minesFieldCells) {
            for (int i = 0; i < minesFieldCells[0].length; i++) {
                if (minesweeperCell[i].isMined() && !minesweeperCell[i].isOpened()) {
                    minesweeperCell[i].open();
                }
            }
        }

        notifySubscribers();
    }

    public MinesweeperCell.Status getCellStatus(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        return minesFieldCells[rowNumber][columnNumber].getStatus();
    }

    public int getCellNeighboursMinesCount(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        return minesFieldCells[rowNumber][columnNumber].getNeighboursMinesCount();
    }

    public void markCellAsMined(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        minesFieldCells[rowNumber][columnNumber].markAsMined();

        notifySubscribers();
    }

    public void markCellAsInQuestion(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        minesFieldCells[rowNumber][columnNumber].markAsInQuestion();

        notifySubscribers();
    }

    public void dropCellMark(int rowNumber, int columnNumber) {
        checkCellIndexes(rowNumber, columnNumber);

        minesFieldCells[rowNumber][columnNumber].dropMark();

        notifySubscribers();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void finishGame() {

    }

    private boolean isGameOver() {
        /*if (gameState == GameState.IS_FAILED) {
            return true;
        }

        if (openedCellsCount < sizeConstantsByLevel[level].rowsCount * sizeConstantsByLevel[level].columnsCount - sizeConstantsByLevel[level].minesCount) {
            return false;
        }

        gameState = GameState.IS_WON;

        notifySubscribers();

        return true;*/
        return false;
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

    protected void notifySubscribers() {
        for (final MinesweeperModelSubscriber subscriber : subscribers) {
            notifySubscriber(subscriber);
        }
    }

    private void notifySubscriber(MinesweeperModelSubscriber subscriber) {
        assert subscriber != null;

        subscriber.modelChanged();
    }

    public void subscribe(MinesweeperModelSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("The subscriber " + subscriber + " has already been signed");
        }

        subscribers.add(subscriber);
        notifySubscriber(subscriber);
    }

    public void unsubscribe(MinesweeperModelSubscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException("Subscriber is empty");
        }

        if (!subscribers.contains(subscriber)) {
            throw new IllegalArgumentException("Unknown subscriber: " + subscriber);
        }

        subscribers.remove(subscriber);
    }
}