package ru.academits.smolenskaya.shapesAreaComparator;

import ru.academits.smolenskaya.shape.Shape;

import java.util.Comparator;

public class ShapesAreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getArea() > shape2.getArea())
            return 1;

        return shape1.getArea() < shape2.getArea() ? -1 : 0;
    }
}