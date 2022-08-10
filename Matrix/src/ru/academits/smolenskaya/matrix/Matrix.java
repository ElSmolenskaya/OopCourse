package ru.academits.smolenskaya.matrix;

import ru.academits.smolenskaya.vector.Vector;

public class Matrix {
    private Vector[] vectors;

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

    public void setRowVector(int index, Vector vector) {
        if (index < 0 || index >= vectors.length) {
            throw new IndexOutOfBoundsException("index = " + index + ": row index must be >= 0 and < " + vectors.length);
        }

        if (vector.getSize() != vectors[0].getSize()) {
            throw new IllegalArgumentException("vector.getSize() = " + vector.getSize() + ": size of vector must be " + vectors[0].getSize());
        }

        vectors[index] = new Vector(vector);
    }

    public Vector getColumnVector(int index) {
        if (index < 0 || index >= vectors[0].getSize()) {
            throw new IndexOutOfBoundsException("index must be >= 0 and < " + vectors[0].getSize());
        }

        Vector resultVector = new Vector(vectors.length);

        for (int i = 0; i < vectors.length; i++) {
            resultVector.setComponent(i, vectors[i].getComponent(index));
        }

        return resultVector;
    }

    public void transpose() {
        Matrix resultMatrix = new Matrix(vectors[0].getSize(), vectors.length);

        for (int i = 0; i < vectors[0].getSize(); i++) {
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
        int size = vectors.length;

        if (size != vectors[0].getSize()) {
            throw new ArithmeticException("The matrix " + this + " is not square");
        }

        Matrix matrix = new Matrix(this);

        for (int i = 0; i < size - 1; i++) {
            int maxComponentRowIndex = i;
            double maxComponent = Math.abs(matrix.vectors[i].getComponent(i));

            for (int k = i + 1; k < size; k++) {
                if (Math.abs(matrix.vectors[k].getComponent(i)) > maxComponent) {
                    maxComponentRowIndex = k;
                    maxComponent = Math.abs(matrix.vectors[k].getComponent(i));
                }
            }

            if (maxComponent == 0) {
                throw new ArithmeticException("Determinant for matrix " + this + " is undefined");
            }

            if (maxComponentRowIndex != i) {
                Vector temp = matrix.vectors[i];
                matrix.vectors[i] = matrix.vectors[maxComponentRowIndex];
                matrix.vectors[maxComponentRowIndex] = temp;
            }

            double multiplier1 = matrix.vectors[i].getComponent(i);

            double denominator = 1;

            if (i != 0) {
                denominator = matrix.vectors[i - 1].getComponent(i - 1);

                if (denominator == 0) {
                    throw new ArithmeticException("Determinant for matrix " + this + " is undefined");
                }
            }

            for (int j = i + 1; j < size; j++) {
                double multiplier2 = matrix.vectors[j].getComponent(i);

                for (int k = i; k < size; k++) {
                    matrix.vectors[j].setComponent(k, (multiplier1 * matrix.vectors[j].getComponent(k) - multiplier2 * matrix.vectors[i].getComponent(k)) / denominator);
                }
            }
        }

        return matrix.vectors[size - 1].getComponent(size - 1);
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

    public void multiply(Vector vector) {
        if (vector.getSize() != vectors[0].getSize()) {
            throw new ArithmeticException("vector.getSize() = " + vector.getSize() + ": size of vector != columns count of matrix " + vectors[0].getSize());
        }

        Matrix resultMatrix = new Matrix(1, vectors.length);

        for (int i = 0; i < vectors.length; i++) {
            double sum = 0;

            for (int j = 0; j < vectors[0].getSize(); j++) {
                sum += vectors[i].getComponent(j) * vector.getComponent(j);
            }

            resultMatrix.vectors[0].setComponent(i, sum);
        }

        vectors = resultMatrix.vectors;
    }

    public void sum(Matrix matrix) {
        if (matrix.vectors.length != vectors.length) {
            throw new ArithmeticException("Matrices " + this + " and " + matrix + " have different number of rows");
        }

        if (matrix.vectors[0].getSize() != vectors[0].getSize()) {
            throw new ArithmeticException("Matrices " + this + " and " + matrix + " have different number of columns");
        }

        for (int i = 0; i < vectors.length; i++) {
            vectors[i].sum(matrix.getRowVector(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (matrix.vectors.length != vectors.length) {
            throw new ArithmeticException("Matrices " + this + " and " + matrix + " have different number of rows");
        }

        if (matrix.vectors[0].getSize() != vectors[0].getSize()) {
            throw new ArithmeticException("Matrices " + this + " and " + matrix + " have different number of columns");
        }

        for (int i = 0; i < vectors.length; i++) {
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
        if (matrix1.vectors[0].getSize() != matrix2.vectors.length) {
            throw new ArithmeticException("Matrices " + matrix1 + " and " + matrix2 + "have incompatible number of rows and columns");
        }

        int rowsCount = matrix1.vectors.length;
        int columnsCount = matrix2.vectors[0].getSize();

        Matrix resultMatrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                resultMatrix.vectors[i].setComponent(j, Vector.getScalarProduct(matrix1.getRowVector(i), matrix2.getColumnVector(j)));
            }
        }

        return resultMatrix;
    }
}