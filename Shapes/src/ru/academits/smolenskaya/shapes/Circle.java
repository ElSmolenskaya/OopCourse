package ru.academits.smolenskaya.shapes;

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public String toString() {
        return String.format("Окружность радиуса %f: площадь %f, периметр %f", radius, getArea(), getPerimeter());
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        return prime * hash + Double.hashCode(radius);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o == null || o.getClass() != getClass() || o.hashCode() != this.hashCode()) return false;

        Circle circle = (Circle) o;

        return radius == circle.radius;
    }
}