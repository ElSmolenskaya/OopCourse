package ru.academits.smolenskaya.range_main;

import ru.academits.smolenskaya.range.Range;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Range lineSegment = new Range(0, 0);

        System.out.print("Введите начальное число: ");
        lineSegment.setFrom(scanner.nextDouble());

        System.out.print("Введите конечное число: ");
        lineSegment.setTo(scanner.nextDouble());

        System.out.printf("Задан отрезок прямой от %fсм. до %fсм.%n", lineSegment.getFrom(), lineSegment.getTo());
        System.out.printf("Длина отрезка равна %fсм.%n", lineSegment.getLength());

        System.out.print("Введите число: ");
        double userNumber = scanner.nextDouble();

        if (lineSegment.isInside(userNumber)) {
            System.out.print("Число принадлежит заданному отрезку");
        } else {
            System.out.print("Число не принадлежит заданному отрезку");
        }
    }
}