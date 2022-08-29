package ru.academits.smolenskaya.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private final ArrayList<E>[] rows;
    private int elementsCount;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        rows = (ArrayList<E>[]) new ArrayList[1];
    }

    public HashTable(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size = " + size + ": size must be > 0");
        }

        //noinspection unchecked
        rows = (ArrayList<E>[]) new ArrayList[size];
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentRowIndex = -1;
        private int currentColumnIndex = -1;
        private final int startModCount = modCount;

        public boolean hasNext() {
            if (currentRowIndex < 0) {
                return elementsCount > 0;
            }

            if ((currentColumnIndex + 1 < rows[currentRowIndex].size())) {
                return true;
            }

            for (int i = currentRowIndex + 1; i < rows.length; i++) {
                if (rows[i] != null && rows[i].size() > 0) {
                    return true;
                }
            }

            return false;
        }

        public E next() {
            if (startModCount != modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("The collection is over");
            }

            if (currentRowIndex < 0) {
                while (true) {
                    ++currentRowIndex;

                    if (rows[currentRowIndex] != null && rows[currentRowIndex].size() > 0) {
                        ++currentColumnIndex;

                        return rows[currentRowIndex].get(currentColumnIndex);
                    }
                }
            }

            if (currentColumnIndex < rows[currentRowIndex].size() - 1) {
                ++currentColumnIndex;

                return rows[currentRowIndex].get(currentColumnIndex);
            }

            ++currentRowIndex;

            while (currentRowIndex < rows.length) {
                if (rows[currentRowIndex] != null && rows[currentRowIndex].size() > 0) {
                    break;
                }

                ++currentRowIndex;
            }

            currentColumnIndex = 0;

            return rows[currentRowIndex].get(currentColumnIndex);
        }
    }

    @Override
    public int size() {
        return elementsCount;
    }

    @Override
    public boolean isEmpty() {
        return elementsCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (elementsCount == 0) {
            return false;
        }

        int index = 0;

        if (o != null) {
            index = Math.abs(o.hashCode() % rows.length);
        }

        if (rows[index] == null) {
            return false;
        }

        return rows[index].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[elementsCount];

        int i = 0;

        for (E element : this) {
            resultArray[i] = element;

            ++i;
        }

        return resultArray;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < elementsCount) {
            //noinspection unchecked
            return (T[]) toArray();
        }

        int i = 0;

        for (E element : this) {
            //noinspection unchecked
            array[i] = (T) element;

            ++i;
        }

        if (array.length > elementsCount) {
            array[elementsCount] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        int index = 0;

        if (e != null) {
            index = Math.abs(e.hashCode() % rows.length);
        }

        if (rows[index] == null) {
            rows[index] = new ArrayList<>();
        }

        rows[index].add(e);

        ++elementsCount;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        int index = 0;

        if (o != null) {
            index = Math.abs(o.hashCode() % rows.length);
        }

        rows[index].remove(o);

        --elementsCount;
        ++modCount;

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int startElementsCount = elementsCount;

        for (E element : c) {
            add(element);
        }

        return startElementsCount != elementsCount;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int startElementsCount = elementsCount;

        for (Object element : c) {
            while (true) {
                if (!remove(element)) {
                    break;
                }
            }
        }

        return startElementsCount != elementsCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int startElementsCount = elementsCount;

        for (ArrayList<E> row : rows) {
            if (row != null) {
                int j = 0;

                while (j < row.size()) {
                    if (!c.contains(row.get(j))) {
                        row.remove(j);

                        --elementsCount;
                        ++modCount;

                        if (j >= row.size()) {
                            break;
                        }
                    } else {
                        j++;
                    }
                }
            }
        }

        return startElementsCount != elementsCount;
    }

    @Override
    public void clear() {
        if (elementsCount == 0) {
            return;
        }

        for (ArrayList<E> row : rows) {
            if (row != null) {
                row.clear();
            }
        }

        ++modCount;
        elementsCount = 0;
    }

    public String toString() {
        if (elementsCount == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (E element : this) {
            stringBuilder.append(element).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}