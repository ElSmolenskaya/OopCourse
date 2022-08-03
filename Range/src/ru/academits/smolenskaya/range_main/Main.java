package ru.academits.smolenskaya.range_main;

import ru.academits.smolenskaya.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите начальное число для первого отрезка: ");
        double from = scanner.nextDouble();

        System.out.print("Введите конечное число для первого отрезка: ");
        double to = scanner.nextDouble();

        Range lineSegment1 = new Range(from, to);

        System.out.println("Задан первый отрезок прямой: " + lineSegment1);
        System.out.printf("Длина первого отрезка равна %fсм.%n", lineSegment1.getLength());

        System.out.print("Введите число: ");
        double userNumber = scanner.nextDouble();

        if (lineSegment1.isInside(userNumber)) {
            System.out.println("Число принадлежит заданному отрезку");
        } else {
            System.out.println("Число не принадлежит заданному отрезку");
        }

        System.out.println();

        System.out.print("Введите начальное число для второго отрезка: ");
        from = scanner.nextDouble();

        System.out.print("Введите конечное число для второго отрезка: ");
        to = scanner.nextDouble();

        Range lineSegment2 = new Range(from, to);

        System.out.println("Задан второй отрезок прямой: " + lineSegment2);
        System.out.printf("Длина второго отрезка равна %fсм.%n%n", lineSegment2.getLength());

        Range intersectionLineSegment = lineSegment1.getIntersection(lineSegment2);

        if (intersectionLineSegment == null) {
            System.out.println("Данные отрезки не пересекаются");
        } else {
            System.out.println("Интервал-пересечение двух отрезков: " + intersectionLineSegment);
            System.out.printf("Длина пересечения двух отрезков равна %fсм.%n%n", intersectionLineSegment.getLength());
        }

        Range[] unionLineSegment = lineSegment1.getUnion(lineSegment2);

        for (Range lineSegment : unionLineSegment) {
            System.out.println("Интервал-объединение двух отрезков: " + lineSegment);
            System.out.printf("Длина объединения двух отрезков равна %fсм.%n", lineSegment.getLength());
        }

        System.out.println();

        Range[] differenceLineSegment = lineSegment1.getDifference(lineSegment2);

        if (differenceLineSegment.length == 0) {
            System.out.println("Разность первого и второго отрезков отсутствует");
        } else {
            for (Range lineSegment : differenceLineSegment) {
                System.out.println("Разность первого и второго отрезков: " + lineSegment);
                System.out.printf("Длина разности первого и второго отрезков равна %fсм.%n", lineSegment.getLength());
            }
        }
    }
}