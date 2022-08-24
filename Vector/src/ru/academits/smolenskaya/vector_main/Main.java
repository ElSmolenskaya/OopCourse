package ru.academits.smolenskaya.vector_main;

import ru.academits.smolenskaya.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(new double[]{1, 2, 4, 32, 16});
        System.out.println("vector1 = " + vector1);

        Vector vector2 = new Vector(new double[]{5, 67, 45, 22, 34, 4, 5, 6});
        System.out.println("vector2 = " + vector2);

        Vector vector3 = Vector.getDifference(vector1, vector2);
        System.out.println("vector3 = vector1 - vector2 = " + vector3);

        vector3 = Vector.getSum(vector1, vector2);
        System.out.println("vector3 = vector1 + vector2 = " + vector3);

        vector1.subtract(vector2);
        System.out.println("vector1 = vector1 - vector2 = " + vector1);

        vector1.add(vector2);
        System.out.println("vector1 = vector1 + vector2 = " + vector1);

        double n = 2.3;
        vector1.multiply(n);
        System.out.printf("vector1 = vector1 * %f = %s%n", n, vector1);

        vector1.reverse();
        System.out.println("vector1 = -1 * vector1 = " + vector1);

        System.out.println("vector1.size = " + vector1.getSize());

        System.out.println("vector1.length = " + vector1.getLength());

        System.out.println("vector1.equals(vector2) = " + vector1.equals(vector2));

        Vector vector4 = new Vector(vector1);
        System.out.println("vector4 = " + vector4);

        System.out.println("vector1.equals(vector4) = " + vector1.equals(vector4));

        Vector vector5 = new Vector(new double[]{1, 3, 4});
        System.out.println("vector5 = " + vector5);

        System.out.println("vector1 * vector5 = " + Vector.getScalarProduct(vector1, vector5));
    }
}