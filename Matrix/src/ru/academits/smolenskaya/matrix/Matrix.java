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
            throw new ArrayIndexOutOfBoundsException("index = " + index + ":row index must be >= 0 and < " + vectors.length);
        }

        return new Vector(vectors[index]);
    }

    public void setRowVector(Vector vector, int index) {
        if (index < 0 || index >= vectors.length) {
            throw new ArrayIndexOutOfBoundsException("index = " + index + ": row index must be >= 0 and < " + vectors.length);
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
            throw new ArrayIndexOutOfBoundsException("index must be >= 0 and < " + columnsCount);
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

    public double getDeterminant() {
        int size = getRowsCount();

        if (size != getColumnsCount()) {
            throw new NegativeArraySizeException("The matrix is not square");
        }

        Matrix matrix = new Matrix(this);
        double determinant = 1;

        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (matrix.vectors[j].getComponent(i) != 0) {

                    determinant *= matrix.vectors[j].getComponent(i);

                    //matrix.vectors[j].multiply(1 / matrix.vectors[j].getComponent(i));
                    for (int k = 0; k < size; k++) {
                        double temp = matrix.vectors[j].getComponent(k) / matrix.vectors[j].getComponent(i);
                        matrix.vectors[j].setComponent(k, temp);
                        System.out.println(temp);
                    }
                }
            }

            for (int k = i + 1; k < size; k++) {
                matrix.vectors[k].subtract(matrix.vectors[i]);
            }
        }

        return determinant;
    }


}
