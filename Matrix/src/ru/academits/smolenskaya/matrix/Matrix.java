package ru.academits.smolenskaya.matrix;

import ru.academits.smolenskaya.vector.Vector;

public class Matrix {
    public Vector[] vectors;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("rowsCount = " + rowsCount + ": size must be > 0");
        }

        if (columnsCount <= 0) {
            throw new IllegalArgumentException("columnsCount = " + columnsCount + ": size must be > 0");
        }

        vectors = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            vectors[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        this.vectors = new Vector[matrix.vectors.length];

        for (int i = 0; i < matrix.vectors.length; i++) {
            this.vectors[i] = new Vector(matrix.vectors[i]);
        }
    }

    public Matrix(double[][] components) {
        int rowsCount = components.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("components.length = 0: size must be > 0");
        }

        int columnsCount = components[0].length;

        for (int i = 1; i < rowsCount; i++) {
            columnsCount = Math.max(columnsCount, components[i].length);
        }

        this.vectors = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            this.vectors[i] = new Vector(columnsCount, components[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("vectors.length = 0: size must be > 0");
        }

        this.vectors = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            this.vectors[i] = new Vector(vectors[i]);
        }
    }

    public int getRowsCount() {
        return vectors.length;
    }

    public int getColumnsCount() {
        return vectors[0].getSize();
    }

    public Vector getRowVector(int index) {
        if (index < 0 || index >= vectors.length) {
            throw new IndexOutOfBoundsException("index = " + index + ":row index must be >= 0 and < " + vectors.length);
        }

        return new Vector(vectors[index]);
    }

    public void setRowVector(Vector vector, int index) {
        if (index < 0 || index >= vectors.length) {
            throw new IndexOutOfBoundsException("index = " + index + ": row index must be >= 0 and < " + vectors.length);
        }

        int columnsCount = getColumnsCount();

        if (vector.getSize() != columnsCount) {
            throw new IllegalArgumentException("vector.getSize() = " + vector.getSize() + ": size of vector must be " + columnsCount);
        }

        vectors[index] = new Vector(vector);
    }

    public Vector getColumnVector(int index) {
        int columnsCount = getColumnsCount();

        if (index < 0 || index >= columnsCount) {
            throw new IndexOutOfBoundsException("index must be >= 0 and < " + columnsCount);
        }

        int rowsCount = getRowsCount();
        Vector result = new Vector(getRowsCount());

        for (int i = 0; i < rowsCount; i++) {
            result.setComponent(i, vectors[i].getComponent(index));
        }

        return result;
    }

    public void transpose() {
        int columnsCount = getColumnsCount();
        int rowsCount = getRowsCount();

        Matrix resultMatrix = new Matrix(columnsCount, rowsCount);

        for (int i = 0; i < columnsCount; i++) {
            resultMatrix.vectors[i] = getColumnVector(i);
        }

        vectors = resultMatrix.vectors;
    }

    public void multiply(double number) {
        for (Vector vector : vectors) {
            vector.multiply(number);
        }
    }

    public double getComponent(int rowNumber, int columnNumber) {
        int rowsCount = getRowsCount();

        if (rowNumber < 0 || rowNumber >= getRowsCount()) {
            throw new IndexOutOfBoundsException("rowNumber = " + rowNumber + ": row index must be >= 0 and < " + rowsCount);
        }

        int columnsCount = getColumnsCount();

        if (columnNumber < 0 || columnNumber >= columnsCount) {
            throw new IndexOutOfBoundsException("columnNumber = " + columnNumber + ": column index must be >= 0 and < " + columnsCount);
        }

        return vectors[rowNumber].getComponent(columnNumber);
    }

    public void setComponent(int rowNumber, int columnNumber, double number) {
        int rowsCount = getRowsCount();

        if (rowNumber < 0 || rowNumber >= getRowsCount()) {
            throw new IndexOutOfBoundsException("rowNumber = " + rowNumber + ": row index must be >= 0 and < " + rowsCount);
        }

        int columnsCount = getColumnsCount();

        if (columnNumber < 0 || columnNumber >= columnsCount) {
            throw new IndexOutOfBoundsException("columnNumber = " + columnNumber + ": column index must be >= 0 and < " + columnsCount);
        }

        vectors[rowNumber].setComponent(columnNumber, number);
    }

    public double getDeterminant() {
        int size = getRowsCount();

        if (size != getColumnsCount()) {
            throw new ArithmeticException("The matrix is not square");
        }

        Matrix matrix = new Matrix(this);
        double determinant = 1;

        for (int i = 0; i < size; i++) {
            int j = i;

            while (true) {
                if (j < size) {
                    if (matrix.getComponent(i, j) != 0) {
                        break;
                    }
                } else {
                    throw new ArithmeticException("Determinant for the matrix is undefined");
                }

                j++;
            }

            if (j != i) {
                boolean isChanged = false;

                for (int k = j; k < size; k++) {
                    if (matrix.getComponent(k, i) != 0) {
                        Vector temp = matrix.vectors[i];
                        matrix.vectors[i] = matrix.vectors[k];
                        matrix.vectors[k] = temp;

                        determinant *= -1;

                        isChanged = true;

                        break;
                    }
                }

                if (!isChanged) {
                    throw new ArithmeticException("Determinant for the matrix is undefined");
                }
            }

            for (int k = i; k < size; k++) {
                if (matrix.getComponent(k, i) != 0) {
                    determinant *= matrix.getComponent(k, i);

                    for (int l = i + 1; l < size; l++) {
                        matrix.setComponent(k, l, matrix.getComponent(k, l) / matrix.getComponent(k, i));
                    }
                }
            }

            for (int k = i + 1; k < size; k++) {
                if (matrix.getComponent(k, i) != 0) {
                    matrix.vectors[k].subtract(matrix.vectors[i]);
                }
            }
        }

        return determinant;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");

        for (Vector vector : vectors) {
            result.append(vector).append(", ");
        }

        result.delete(result.length() - 2, result.length());

        result.append("}");

        return result.toString();
    }

    public Vector multiply(Vector vector) {
        int columnsCount = getColumnsCount();

        if (vector.getSize() != columnsCount) {
            throw new ArithmeticException("vector.getSize() = " + vector.getSize() + ": size of vector != columns count of matrix " + columnsCount);
        }

        int rowsCount = getRowsCount();
        Vector resultVector = new Vector(rowsCount);

        for (int i = 0; i < rowsCount; i++) {
            double sum = 0;

            for (int j = 0; j < columnsCount; j++) {
                sum += getComponent(i, j) * vector.getComponent(j);
            }

            resultVector.setComponent(i, sum);
        }

        return resultVector;
    }

    public void sum(Matrix matrix) {
        int rowsCount = getRowsCount();

        if (matrix.getRowsCount() != rowsCount) {
            throw new ArithmeticException("Matrices have different number of rows");
        }

        if (matrix.getColumnsCount() != getColumnsCount()) {
            throw new ArithmeticException("Matrices have different number of columns");
        }

        for (int i = 0; i < rowsCount; i++) {
            vectors[i].sum(matrix.getRowVector(i));
        }
    }

    public void subtract(Matrix matrix) {
        int rowsCount = getRowsCount();

        if (matrix.getRowsCount() != rowsCount) {
            throw new ArithmeticException("Matrices have different number of rows");
        }

        if (matrix.getColumnsCount() != getColumnsCount()) {
            throw new ArithmeticException("Matrices have different number of columns");
        }

        for (int i = 0; i < rowsCount; i++) {
            vectors[i].subtract(matrix.getRowVector(i));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.sum(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()){
            throw new ArithmeticException("Matrices have incompatible number of rows and columns");
        }

        int rowsCount = matrix1.getRowsCount();
        int columnsCount = matrix2.getColumnsCount();

        Matrix resultMatrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                resultMatrix.setComponent(i, j, Vector.getScalarProduct(matrix1.getRowVector(i), matrix2.getColumnVector(j)));
            }
        }

        return resultMatrix;
    }
}