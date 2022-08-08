package ru.academits.smolenskaya.matrix_main;

import ru.academits.smolenskaya.matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        /*Vector[] vectors = new Vector[4];
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
*/
        /*Matrix matrix4 = new Matrix(new double[][]{{15,14,12}, {6,7,1}, {54,5,7}});
        System.out.println("determinant = " + matrix4.getDeterminant());*/

       // Matrix matrix4 = new Matrix(new double[][]{{0,1,2}, {6,7,1}, {6,5,7}, {1,1,1}});
        //System.out.println("determinant = " + matrix4.getDeterminant());

        /*Matrix matrix4 = new Matrix(new double[][]{{1,1,2,4},{3,4,5,6},{5,4,2,45},{35,44,54,64}});
        System.out.println("determinant = " + matrix4.getDeterminant());*/

        /*Matrix matrix4 = new Matrix(new double[][]{{1}});
        System.out.println("determinant = " + matrix4.getDeterminant());*/

        /*Vector vector1 = new Vector(new double[]{1,2,3});
        System.out.println(matrix4.multiply(vector1));*/


        Matrix matrix1 = new Matrix(new double[][]{{2, 0, -1}, {0, -2, 2}});
        Matrix matrix2 = new Matrix(new double[][]{{4, 1, 0}, {3, 2, 1}, {0, 1, 0}});
        Matrix matrix3 = Matrix.getProduct(matrix1, matrix2);
        System.out.println(matrix3);
    }
}
