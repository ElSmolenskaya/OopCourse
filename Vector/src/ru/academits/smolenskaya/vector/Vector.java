package ru.academits.smolenskaya.vector;

public class Vector {
    private double[] components;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        components = new double[n];

        for (int i = 0; i < n; i++) {
            components[i] = 0;
        }
    }

    public Vector(Vector vector) {
        components = new double[vector.components.length];

        System.arraycopy(vector.components, 0, components, 0, components.length);
    }

    public Vector(double[] components) {
        this.components = new double[components.length];

        System.arraycopy(components, 0, this.components, 0, components.length);
    }

    public Vector(int n, double[] components) {
        this(n);

        System.arraycopy(components, 0, this.components, 0, Math.min(n, components.length));
    }

    public double[] getComponents() {
        return components;
    }

    public void setComponents(double[] components) {
        this.components = components;
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{");

        if (components.length > 0) {
            for (double component : components) {
                result.append(component).append(", ");
            }

            result.delete(result.length() - 2, result.length());
        }

        result.append("}");

        return result.toString();
    }

    public void sum(Vector vector) {
        if (components.length < vector.getSize()) {
            Vector resultVector = new Vector(vector.getSize(), components);

            for (int i = 0; i < resultVector.getSize(); i++) {
                resultVector.setComponent(i, resultVector.getComponent(i) + vector.getComponent(i));
            }

            setComponents(resultVector.getComponents());
        } else {
            for (int i = 0; i < vector.getSize(); i++) {
                components[i] += vector.getComponent(i);
            }
        }
    }

    public void subtract(Vector vector) {
        if (components.length < vector.getSize()) {
            Vector resultVector = new Vector(vector.getSize(), components);

            for (int i = 0; i < resultVector.getSize(); i++) {
                resultVector.getComponents()[i] -= vector.getComponents()[i];
            }

            setComponents(resultVector.getComponents());
        } else {
            for (int i = 0; i < vector.getSize(); i++) {
                components[i] -= vector.getComponents()[i];
            }
        }
    }

    public void multiply(double n) {
        for (int i = 0; i < components.length; i++) {
            components[i] *= n;
        }
    }

    public void reverse() {
        multiply(-1);
    }

    public double getLength() {
        double result = 0;

        for (double component : components) {
            result += component * component;
        }

        return Math.sqrt(result);
    }

    public double getComponent(int index) {
        if (index < 0 || index >= components.length) {
            throw new IllegalArgumentException("index must be >= 0 and index must be <= " + components.length);
        }

        return components[index];
    }

    public void setComponent(int index, double component) {
        if (index < 0 || index >= components.length) {
            throw new IllegalArgumentException("index must be >= 0 and index must be <= " + components.length);
        }

        components[index] = component;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        for (double component : components) {
            hash = prime * hash + Double.hashCode(component);
        }

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o == null || o.getClass() != getClass() || o.hashCode() != hashCode()) {
            return false;
        }

        Vector vector = (Vector) o;

        if (vector.getSize() != components.length) {
            return false;
        }

        for (int i = 0; i < components.length; i++) {
            if (components[i] != vector.getComponent(i)) {
                return false;
            }
        }

        return true;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        int size = Math.max(vector1.getSize(), vector2.getSize());

        if (size == 0) {
            return new Vector(new double[]{});
        }

        Vector resultVector = new Vector(size, vector1.getComponents());
        resultVector.sum(vector2);

        return resultVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        int size = Math.max(vector1.getSize(), vector2.getSize());

        if (size == 0) {
            return new Vector(new double[]{});
        }

        Vector resultVector = new Vector(size, vector1.getComponents());
        resultVector.subtract(vector2);

        return resultVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double result = 0;

        for (int i = 0; i < Math.min(vector1.getSize(), vector2.getSize()); i++) {
            result += vector1.getComponent(i) * vector2.getComponent(i);
        }

        return result;
    }
}