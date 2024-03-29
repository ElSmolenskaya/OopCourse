package ru.academits.smolenskaya.minesweeper.model;

class MinesweeperCell {
    private boolean isMined;
    private int neighboursMinesCount;
    private Status status;

    public enum Status {
        CLOSED, MARKED_AS_MINED, MARKED_AS_IN_QUESTION, OPENED, MISTAKEN_MINE, MINE_DETONATED
    }

    public MinesweeperCell(int neighboursMinesCount) {
        this.neighboursMinesCount = neighboursMinesCount;

        status = Status.CLOSED;
    }

    public int getNeighboursMinesCount() {
        return neighboursMinesCount;
    }

    public void setNeighboursMinesCount(int minesCount) {
        neighboursMinesCount = minesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMine() {
        isMined = true;
    }

    public boolean isMined() {
        return isMined;
    }
}