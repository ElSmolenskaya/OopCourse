package ru.academits.smolenskaya.range_main;

import ru.academits.smolenskaya.range.Range;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Range lineSegment1 = new Range(0, 0);

        System.out.print("Введите начальное число для первого отрезка: ");
        lineSegment1.setFrom(scanner.nextDouble());

        System.out.print("Введите конечное число для первого отрезка: ");
        lineSegment1.setTo(scanner.nextDouble());

        System.out.printf("Задан первый отрезок прямой от %fсм. до %fсм.%n", lineSegment1.getFrom(), lineSegment1.getTo());
        System.out.printf("Длина первого отрезка равна %fсм.%n", lineSegment1.getLength());

        System.out.print("Введите число: ");
        double userNumber = scanner.nextDouble();

        if (lineSegment1.isInside(userNumber)) {
            System.out.println("Число принадлежит заданному отрезку");
        } else {
            System.out.println("Число не принадлежит заданному отрезку");
        }

        System.out.println();

        Range lineSegment2 = new Range(0, 0);

        System.out.print("Введите начальное число для второго отрезка: ");
        lineSegment2.setFrom(scanner.nextDouble());

        System.out.print("Введите конечное число для второго отрезка: ");
        lineSegment2.setTo(scanner.nextDouble());

        System.out.printf("Задан второй отрезок прямой от %fсм. до %fсм.%n", lineSegment2.getFrom(), lineSegment2.getTo());
        System.out.printf("Длина второго отрезка равна %fсм.%n%n", lineSegment2.getLength());

        Range intersectionLineSegment = lineSegment1.intersection(lineSegment2);

        if (intersectionLineSegment == null) {
            System.out.println("Данные отрезки не пересекаются");
        } else {
            System.out.printf("Интервал-пересечение двух отрезков лежит в пределах от %fсм. до %fсм.%n",
                    intersectionLineSegment.getFrom(), intersectionLineSegment.getTo());
            System.out.printf("Длина пересечения двух отрезков равна %fсм.%n%n", intersectionLineSegment.getLength());
        }

        Range[] unionLineSegment = lineSegment1.union(lineSegment2);

        for (Range lineSegment : unionLineSegment) {
            System.out.printf("Интервал-объединение двух отрезков лежит в пределах от %fсм. до %fсм.%n",
                    lineSegment.getFrom(), lineSegment.getTo());
            System.out.printf("Длина объединения двух отрезков равна %fсм.%n", lineSegment.getLength());
        }

        System.out.println();

        Range[] differenceLineSegment = lineSegment1.difference(lineSegment2);

        if (differenceLineSegment == null) {
            System.out.println("Разность первого и второго отрезков (первый - второй) отсутствует");
        } else {

            for (Range lineSegment : differenceLineSegment) {
                System.out.printf("Разность первого и второго отрезков (первый - второй) лежит в пределах от %fсм. до %fсм.%n",
                        lineSegment.getFrom(), lineSegment.getTo());
                System.out.printf("Длина разности первого и второго отрезков равна %fсм.%n", lineSegment.getLength());
            }
        }
    }
}