package ru.academits.smolenskaya.matrix;

import ru.academits.smolenskaya.vector.Vector;

public class Matrix {
    public Vector[] vectors;

    public Matrix(int rowsCount, int columnsCount) {
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
        this.vectors = new Vector[components.length];

        for (int i = 0; i < components.length; i++) {
            this.vectors[i] = new Vector(components[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        this.vectors = new Vector[vectors.length];

        for (int i = 0; i < vectors.length; i++) {
            this.vectors[i] = new Vector(vectors[i]);
        }
    }

    public int getRowsCount() {
        return vectors.length;
    }

    public int getColumnsCount() {
        if (vectors.length <= 0) {
            return 0;
        }

        return vectors[0].getSize();
    }

    public Vector getRowVector(int index) {
        if (index < 0 || index >= vectors.length) {
            throw new IllegalArgumentException("index must be >= 0 and < " + vectors.length);
        }

        return new Vector(vectors[index]);
    }

    public void setRowVector(Vector vector, int index) {
        if (index < 0 || index >= vectors.length) {
            throw new IllegalArgumentException("row index must be >= 0 and < " + vectors.length);
        }

        if (vector.getSize() > getColumnsCount()) {
            throw new IllegalArgumentException("size of vector must be <= columns count of matrix" + vectors.length);
        }

        //vectors[index] = new Vector(getColumnsCount(), vector.getComponents());
    }

    public Vector getColumnVector(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IllegalArgumentException("index must be >= 0 and < " + getColumnsCount());
        }

        Vector result = new Vector(getRowsCount());

        for (int i = 0; i < getRowsCount(); i++) {
            result.setComponent(i, vectors[i].getComponent(index));
        }

        return result;
    }

    public void getTransposition() {
        int rowsCount = getRowsCount();
        int columnsCount = getColumnsCount();

        Matrix resultMatrix = new Matrix(columnsCount, rowsCount);

        for (int i = 0; i < columnsCount; i++) {
            resultMatrix.vectors[i] = getColumnVector(i);
        }

        vectors = resultMatrix.vectors;
    }

    public void getProduct(double n) {
        for (Vector vector : vectors) {
            vector.multiply(n);
        }
    }


}
