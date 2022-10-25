package ru.academits.smolenskaya.minesweeper.model;

class MinesweeperCell {
    private int neighboursMinesCount;
    private Status status;

    public enum Status {
        CLOSED, MARKED_AS_MINED, MARKED_AS_IN_QUESTION, OPENED, MISTAKEN_MINE, MINE_DETONATED
    }

    public MinesweeperCell(int minesCount) {
        this.neighboursMinesCount = minesCount;

        status = Status.CLOSED;
    }

    public int getNeighboursMinesCount() {
        return neighboursMinesCount;
    }

    public void setNeighboursMinesCount(int neighboursMinesCount) {
        this.neighboursMinesCount = neighboursMinesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMine() {
        neighboursMinesCount = -1;
    }

    public boolean isMined() {
        return neighboursMinesCount == -1;
    }
}