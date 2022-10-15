package ru.academits.smolenskaya.minesweeper_model;

class MinesweeperCell {
    private int neighboursMinesCount;
    private Status status;

    public enum Status {
        OPENED, CLOSED, MARKED_AS_MINED, MARKED_AS_IN_QUESTION, MINE_SWEPT, MINE_DETONATED
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

    public boolean isMined() {
        return neighboursMinesCount == -1;
    }

    public void setMine() {
        neighboursMinesCount = -1;
    }

    public boolean isOpened() {
        return status == Status.OPENED || status == Status.MINE_SWEPT || status == Status.MINE_DETONATED;
    }

    public void open() {
        if (neighboursMinesCount >= 0) {
            status = Status.OPENED;
        } else {
            if (status == Status.MARKED_AS_MINED) {
                status = Status.MINE_SWEPT;
            } else {
                status = Status.MINE_DETONATED;
            }
        }
    }

    public void markAsMined() {
        setStatus(Status.MARKED_AS_MINED);
    }

    public boolean isMarkedAsMined() {
        return status == Status.MARKED_AS_MINED;
    }

    public void markAsInQuestion() {
        setStatus(Status.MARKED_AS_IN_QUESTION);
    }

    public boolean isMarkedAsInQuestion() {
        return status == Status.MARKED_AS_IN_QUESTION;
    }

    public void dropMark() {
        setStatus(Status.CLOSED);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (!isOpened()) {
            this.status = status;
        }
    }
}