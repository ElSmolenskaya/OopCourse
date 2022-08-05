package ru.academits.smolenskaya.matrix;

import ru.academits.smolenskaya.vector.Vector;

public class Matrix {
    public Vector[] vectors;

    public Matrix(int m, int n) {
        vectors = new Vector[m];

        for (int i = 0; i < m; i++) {
            vectors[i] = new Vector(n);
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

    public int[] getSize() {
        return new int[]{vectors.length, vectors[0].getSize()};
    }

    public Vector getVector(int index){
        if (index < 0 || index >= vectors.length) {
            throw new IllegalArgumentException("index must be >= 0 and index must be < " + vectors.length);
        }

        return vectors[index];
    }

}
