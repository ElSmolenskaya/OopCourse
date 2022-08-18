package ru.academits.smolenskaya.matrix;

import ru.academits.smolenskaya.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0) {
            throw new IllegalArgumentException("rowsCount = " + rowsCount + ": size must be > 0");
        }

        if (columnsCount <= 0) {
            throw new IllegalArgumentException("columnsCount = " + columnsCount + ": size must be > 0");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.rows.length];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
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

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount, components[i]);
        }
    }

    public Matrix(Vector[] rows) {
        int rowsCount = rows.length;

        if (rowsCount == 0) {
            throw new IllegalArgumentException("rows.length = 0: size must be > 0");
        }

        int columnsCount = 0;

        for (Vector row : rows) {
            columnsCount = Math.max(columnsCount, row.getSize());
        }

        this.rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            double[] tempRow = new double[rows[i].getSize()];

            for (int j = 0; j < rows[i].getSize(); j++) {
                tempRow[j] = rows[i].getComponent(j);
            }

            this.rows[i] = new Vector(columnsCount, tempRow);
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("index = " + index + ": row index must be >= 0 and < " + rows.length);
        }

        return new Vector(rows[index]);
    }

    public void setRow(int index, Vector row) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("index = " + index + ": row index must be >= 0 and < " + rows.length);
        }

        if (row.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("row.getSize() = " + row.getSize() + ": row size must be " + getColumnsCount());
        }

        rows[index] = new Vector(row);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("index = " + index + ": column index must be >= 0 and < " + getColumnsCount());
        }

        Vector resultColumn = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            resultColumn.setComponent(i, rows[i].getComponent(index));
        }

        return resultColumn;
    }

    public void transpose() {
        double[][] tempArray = new double[getColumnsCount()][rows.length];

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                tempArray[j][i] = rows[i].getComponent(j);
            }
        }

        rows = new Vector[tempArray.length];

        for (int i = 0; i < tempArray.length; i++) {
            rows[i] = new Vector(tempArray[i].length, tempArray[i]);
        }
    }

    public void multiply(double number) {
        for (Vector row : rows) {
            row.multiply(number);
        }
    }

    public double getDeterminant() {
        int size = rows.length;

        if (size != getColumnsCount()) {
            throw new UnsupportedOperationException("The matrix " + this + " is not square");
        }

        Matrix matrix = new Matrix(this);

        int determinantCoefficient = 1;

        for (int i = 0; i < size - 1; i++) {
            int maxComponentRowIndex = i;
            double maxComponent = Math.abs(matrix.rows[i].getComponent(i));

            for (int j = i + 1; j < size; j++) {
                if (Math.abs(matrix.rows[j].getComponent(i)) > maxComponent) {
                    maxComponentRowIndex = j;
                    maxComponent = Math.abs(matrix.rows[j].getComponent(i));
                }
            }

            if (maxComponent == 0) {
                return 0;
            }

            if (maxComponentRowIndex != i) {
                Vector temp = matrix.rows[i];
                matrix.rows[i] = matrix.rows[maxComponentRowIndex];
                matrix.rows[maxComponentRowIndex] = temp;

                determinantCoefficient *= -1;
            }

            double multiplier1 = matrix.rows[i].getComponent(i);

            double denominator = 1;

            if (i != 0) {
                denominator = matrix.rows[i - 1].getComponent(i - 1);

                if (denominator == 0) {
                    return 0;
                }
            }

            for (int j = i + 1; j < size; j++) {
                double multiplier2 = matrix.rows[j].getComponent(i);

                for (int k = i; k < size; k++) {
                    matrix.rows[j].setComponent(k, (multiplier1 * matrix.rows[j].getComponent(k) - multiplier2 * matrix.rows[i].getComponent(k)) / denominator);
                }
            }
        }

        return matrix.rows[size - 1].getComponent(size - 1) * determinantCoefficient;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector row : rows) {
            stringBuilder.append(row).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public Vector multiply(Vector row) {
        if (row.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("row.getSize() = " + row.getSize() + ": row size must be = " + getColumnsCount());
        }

        double[] resultArray = new double[rows.length];

        for (int i = 0; i < rows.length; i++) {
            double sum = 0;

            for (int j = 0; j < getColumnsCount(); j++) {
                sum += rows[i].getComponent(j) * row.getComponent(j);
            }

            resultArray[i] = sum;
        }

        return new Vector(resultArray);
    }

    private static void compareSizes(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length) {
            throw new IllegalArgumentException("Matrices " + matrix1 + " and " + matrix2 + " have different number of rows");
        }

        if (matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Matrices " + matrix1 + " and " + matrix2 + " have different number of columns");
        }
    }

    public void add(Matrix matrix) {
        compareSizes(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                rows[i].setComponent(j, rows[i].getComponent(j) + matrix.rows[i].getComponent(j));
            }
        }
    }

    public void subtract(Matrix matrix) {
        compareSizes(this, matrix);

        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < getColumnsCount(); j++) {
                rows[i].setComponent(j, rows[i].getComponent(j) - matrix.rows[i].getComponent(j));
            }
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        compareSizes(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        compareSizes(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Matrices " + matrix1 + " and " + matrix2 + "have incompatible number of rows and columns");
        }

        int rowsCount = matrix1.rows.length;
        int columnsCount = matrix2.getColumnsCount();

        Matrix resultMatrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                int scalarProduct = 0;

                for (int k = 0; k < rowsCount; k++) {
                    scalarProduct += matrix1.rows[i].getComponent(k) * matrix2.rows[k].getComponent(j);
                }

                resultMatrix.rows[i].setComponent(j, scalarProduct);
            }
        }

        return resultMatrix;
    }
}