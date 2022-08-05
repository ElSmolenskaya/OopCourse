package ru.academits.smolenskaya.shapes_comparators;

import ru.academits.smolenskaya.shapes.Shape;

import java.util.Comparator;

public class ShapesAreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getArea() > shape2.getArea())
            return 1;

        return shape1.getArea() < shape2.getArea() ? -1 : 0;
    }
}