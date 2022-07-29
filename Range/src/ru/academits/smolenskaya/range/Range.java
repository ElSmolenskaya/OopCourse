package ru.academits.smolenskaya.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        double epsilon = 1.0e-10;

        return number - from >= -epsilon && to - number >= -epsilon;
    }

    public Range intersection(Range range) {
        if (range.getTo() <= from || range.getFrom() >= to) {
            return null;
        }

        return new Range(Math.max(from, range.getFrom()), Math.min(to, range.getTo()));
    }

    public Range[] union(Range range) {
        if (range.getTo() < from || range.getFrom() > to) {
            return new Range[]{new Range(from, to), new Range(range.getFrom(), range.getTo())};
        }

        return new Range[]{new Range(Math.min(from, range.getFrom()), Math.max(to, range.getTo()))};
    }

    public Range[] difference(Range range) {
        if (to <= range.getFrom() || from >= range.getTo()) {
            return new Range[]{new Range(from, to)};
        }

        if (from < range.getFrom()) {
            if (to <= range.getTo()) {
                return new Range[]{new Range(from, range.getFrom())};
            }

            return new Range[]{new Range(from, range.getFrom()), new Range(range.getTo(), to)};
        }

        if (to > range.getTo()) {
            return new Range[]{new Range(range.getTo(), to)};
        }

        return null;
    }
}