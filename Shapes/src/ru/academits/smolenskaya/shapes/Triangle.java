package ru.academits.smolenskaya.shapes;

public class Triangle implements Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    private void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    private void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    private void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    private void setY2(double y2) {
        this.y2 = y2;
    }

    public double getX3() {
        return x3;
    }

    private void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    private void setY3(double y3) {
        this.y3 = y3;
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    @Override
    public double getArea() {
        return Math.abs(((x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3))) / 2;
    }

    @Override
    public double getPerimeter() {
        return Triangle.getSideLength(x1, y1, x2, y2) + Triangle.getSideLength(x2, y2, x3, y3) + Triangle.getSideLength(x1, y1, x3, y3);
    }

    @Override
    public String toString() {
        return String.format("Треугольник с вершинами (%f, %f), (%f, %f), (%f, %f): площадь %f, периметр %f",
                x1, y1, x2, y2, x3, y3, getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o == null || o.getClass() != getClass()) return false;

        Triangle triangle = (Triangle) o;

        return x1 == triangle.x1 && y1 == triangle.y1 && x2 == triangle.x2 && y2 == triangle.y2 && x3 == triangle.x3 && y3 == triangle.y3;
    }

    public static double getSideLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}