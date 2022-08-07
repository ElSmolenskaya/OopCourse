package ru.academits.smolenskaya.matrix_main;

import ru.academits.smolenskaya.matrix.Matrix;
import ru.academits.smolenskaya.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector[] vectors = new Vector[4];
        vectors[0] = new Vector(new double[]{1, 2, 4, 32, 16});
        vectors[1] = new Vector(new double[]{1, 1, 1, 32, 16});
        vectors[2] = new Vector(new double[]{1, 2, 4, 32, 16});
        vectors[3] = new Vector(new double[]{2, 2, 2, 32, 16});

        Matrix matrix = new Matrix(vectors);

        Matrix matrix1 = new Matrix(matrix);

        Matrix matrix2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});

        Matrix matrix3 = new Matrix(4, 6);

        matrix.transpose();

        matrix.multiply(2);

        for (Vector vector : matrix.vectors) {
            System.out.println(vector);
        }

        Matrix matrix4 = new Matrix(new double[][]{{15,14,12}, {6,7,1}, {54,5,7}});
        System.out.println("determinant = " + matrix4.getDeterminant());
    }
}
