package ru.academits.smolenskaya.hashTable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private final ArrayList<E>[] rows;
    private int elementsCount;
    private int modCount;

    public HashTable(int size) {
        //noinspection unchecked
        rows = (ArrayList<E>[]) new ArrayList[size];
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentRowIndex = -1;
        private int currentColumnIndex = 0;
        private final int modCount;

        public HashTableIterator() {
            modCount = HashTable.this.modCount;
        }

        public boolean hasNext() {
            if (currentRowIndex < 0) {
                return elementsCount > 0;
            }

            if ((currentColumnIndex < rows[currentRowIndex].size() - 1)) {
                return true;
            }

            for (int i = currentRowIndex + 1; i < rows.length; i++) {
                if (rows[i].size() > 0) {
                    return true;
                }
            }

            return false;
        }

        public E next() {
            if (elementsCount == 0) {
                throw new NoSuchElementException("The collection is over");
            }

            if (modCount != HashTable.this.modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            if (currentRowIndex == -1) {
                while (true) {
                    ++currentRowIndex;

                    if (rows[currentRowIndex].size() > 0) {
                        return rows[currentRowIndex].get(currentColumnIndex);
                    }
                }
            }

            if ((currentRowIndex == rows.length - 1) && (currentColumnIndex == rows[currentRowIndex].size() - 1)) {
                throw new NoSuchElementException("The collection is over");
            }

            if (currentColumnIndex < rows[currentRowIndex].size() - 1) {
                ++currentColumnIndex;

                return rows[currentRowIndex].get(currentColumnIndex);
            }

            ++currentRowIndex;
            currentColumnIndex = 0;

            while (currentRowIndex < rows.length) {
                if (rows[currentRowIndex].size() > 0) {
                    return rows[currentRowIndex].get(currentColumnIndex);
                }

                ++currentRowIndex;
            }

            throw new NoSuchElementException("The collection is over");
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
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection must not contain null elements");
        }

        if (elementsCount == 0) {
            return false;
        }

        int index = Math.abs(o.hashCode() % rows.length);

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
            return (T[]) Arrays.copyOf(toArray(), elementsCount);
        }


        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(toArray(), 0, array, 0, elementsCount);

        if (array.length > elementsCount) {
            array[elementsCount] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        int index = Math.abs(e.hashCode() % rows.length);

        if (rows[index] == null) {
            rows[index] = new ArrayList<>();
        }

        if (rows[index].contains(e)) {
            return false;
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

        int index = Math.abs(o.hashCode() % rows.length);

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
            remove(element);
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
        for (ArrayList<E> row : rows) {
            row.clear();
        }

        modCount += elementsCount;
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