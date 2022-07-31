package ru.academits.smolenskaya.shapesPerimeterComparator;

import ru.academits.smolenskaya.shape.Shape;

import java.util.Comparator;

public class ShapesPerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        if (shape1.getPerimeter() > shape2.getPerimeter())
            return 1;

        return shape1.getPerimeter() < shape2.getPerimeter() ? -1 : 0;
    }
}