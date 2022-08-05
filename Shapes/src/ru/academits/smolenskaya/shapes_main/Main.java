package ru.academits.smolenskaya.shapes_main;

import ru.academits.smolenskaya.shapes.Circle;
import ru.academits.smolenskaya.shapes.Rectangle;
import ru.academits.smolenskaya.shapes.Shape;
import ru.academits.smolenskaya.shapes_comparators.ShapesAreaComparator;
import ru.academits.smolenskaya.shapes_comparators.ShapesPerimeterComparator;
import ru.academits.smolenskaya.shapes.Square;
import ru.academits.smolenskaya.shapes.Triangle;

import java.util.Arrays;

public class Main {
    public static Shape getMaxAreaShape(Shape[] shapes, int positionNumber) {
        if (shapes.length < positionNumber || positionNumber < 1) {
            return null;
        }

        ShapesAreaComparator shapesAreaComparator = new ShapesAreaComparator();
        Arrays.sort(shapes, shapesAreaComparator);

        return shapes[shapes.length - positionNumber];
    }

    public static Shape getMaxPerimeterShape(Shape[] shapes, int positionNumber) {
        if (shapes.length < positionNumber || positionNumber < 1) {
            return null;
        }

        ShapesPerimeterComparator shapesPerimeterComparator = new ShapesPerimeterComparator();
        Arrays.sort(shapes, shapesPerimeterComparator);

        return shapes[shapes.length - positionNumber];
    }

    public static void main(String[] args) {
        Shape[] shapes = new Shape[]{new Square(5.2),
                new Square(9.7),
                new Rectangle(3, 4.8),
                new Rectangle(5, 6),
                new Circle(7.3),
                new Circle(4.5),
                new Triangle(2, 2, 5, 5, 2, 8)};

        System.out.println("Задан массив фигур:");

        for (Shape shape : shapes) {
            System.out.println(shape);
        }

        System.out.println();

        Shape maxAreaShape = getMaxAreaShape(shapes, 1);

        if (maxAreaShape != null) {
            System.out.println("Фигура с максимальной площадью - " + maxAreaShape);
        }

        Shape maxPerimeterShape = getMaxPerimeterShape(shapes, 2);

        if (maxPerimeterShape != null) {
            System.out.println("Фигура со вторым по величине периметром - " + maxPerimeterShape);
        }
    }
}
