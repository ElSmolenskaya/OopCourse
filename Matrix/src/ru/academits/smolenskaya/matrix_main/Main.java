package ru.academits.smolenskaya.matrix_main;

import ru.academits.smolenskaya.vector.Vector;
import ru.academits.smolenskaya.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Vector[] vectors = new Vector[4];
        vectors[0] = new Vector(new double[]{1, 2, 4, 32, 16});
        vectors[1] = new Vector(new double[]{1, 1, 1, 32, 16});
        vectors[2] = new Vector(new double[]{1, 2, 4, 32, 16});
        vectors[3] = new Vector(new double[]{2, 2, 2, 32, 16});

        Matrix matrix1 = new Matrix(vectors);
        System.out.println("matrix1 = " + matrix1);

        System.out.printf("matrix1: rows count = %d, columns count = %d%n", matrix1.getRowsCount(), matrix1.getColumnsCount());

        int rowNumber = 2;
        System.out.printf("matrix1, row %d = %s%n", rowNumber, matrix1.getRowVector(rowNumber));

        Vector vector1 = new Vector(new double[]{5, 5, 5, 5, 5});
        matrix1.setRowVector(rowNumber, vector1);
        System.out.printf("matrix1: (changed row %d = %s) = %s%n", rowNumber, vector1, matrix1);

        int columnNumber = 1;
        System.out.printf("matrix1, column %d = %s%n", columnNumber, matrix1.getColumnVector(columnNumber));

        matrix1.transpose();
        System.out.println("Transposed matrix1 = " + matrix1);

        double number = 2.2;
        matrix1.multiply(number);
        System.out.printf("matrix1 * %f = %s%n", number, matrix1);

        System.out.println();

        Matrix matrix2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        System.out.println("matrix2 = " + matrix2);

        Vector vector2 = new Vector(new double[]{2, 1, 2});
        System.out.println("vector2 = " + vector2);

        matrix2.multiply(vector2);
        System.out.println("Matrix2 * vector2 = " + matrix2);

        System.out.println();

        Matrix matrix3 = new Matrix(new double[][]{{0, 14, 12}, {6, 0, 1}, {54, 5, 7}});
        System.out.println("matrix3 = " + matrix3);
        System.out.println("matrix3 determinant = " + matrix3.getDeterminant());

        Matrix matrix4 = new Matrix(new double[][]{{0, 1, 2}, {6, 7, 1}, {6, 5, 7}});
        System.out.println("matrix4 = " + matrix4);
        System.out.println("matrix4 determinant = " + matrix4.getDeterminant());

        System.out.println();

        Matrix matrix5 = Matrix.getSum(matrix3, matrix4);
        System.out.println("matrix5 = matrix3 + matrix4 = " + matrix5);

        matrix5 = Matrix.getDifference(matrix3, matrix4);
        System.out.println("matrix5 = matrix3 - matrix4 = " + matrix5);

        System.out.println();

        matrix3.sum(matrix4);
        System.out.println("matrix3 = matrix3 + matrix4 = " + matrix3);

        matrix3.subtract(matrix4);
        System.out.println("matrix3 = matrix3 - matrix4 = " + matrix3);

        System.out.println();

        Matrix matrix6 = new Matrix(new double[][]{{1, 2}, {2, 1}, {1, 1}});
        System.out.println("matrix6 = " + matrix6);

        matrix5 = Matrix.getProduct(matrix4, matrix6);
        System.out.println("matrix5 = matrix4 * matrix6 = " + matrix5);
    }
}
