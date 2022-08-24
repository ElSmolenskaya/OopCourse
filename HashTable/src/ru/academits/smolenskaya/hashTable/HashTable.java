package ru.academits.smolenskaya.hashTable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private ArrayList<E>[] hashTableRows;
    private int modCount;
    private int elementsCount;

    public HashTable() {
    }

    public HashTable(int size) {
        //noinspection unchecked
        hashTableRows = (ArrayList<E>[]) new ArrayList[size];
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentRowIndex = 0;
        private int currentColumnIndex = 0;
        private final int modCount;

        public HashTableIterator() {
            modCount = HashTable.this.modCount;
        }

        public boolean hasNext() {
            if (currentColumnIndex < hashTableRows[currentRowIndex].size() - 1) {
                return true;
            }

            for (int i = currentRowIndex; i < hashTableRows.length; i++) {
                if (hashTableRows[i] != null) {
                    return true;
                }
            }

            return false;
        }

        public E next() {
            if (modCount != HashTable.this.modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            if ((currentRowIndex >= hashTableRows.length) ||
                    ((currentRowIndex == hashTableRows.length - 1) & (currentColumnIndex >= hashTableRows[currentRowIndex].size()))) {
                throw new NoSuchElementException("The collection is over");
            }

            if (currentColumnIndex < hashTableRows[currentRowIndex].size() - 1) {
                ++currentColumnIndex;

                return hashTableRows[currentRowIndex].get(currentColumnIndex);
            }

            ++currentRowIndex;
            currentColumnIndex = 0;

            while (currentRowIndex < hashTableRows.length) {
                if (hashTableRows[currentRowIndex] != null) {
                    break;
                }

                ++currentRowIndex;
            }

            return hashTableRows[currentRowIndex].get(currentColumnIndex);
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

        int index = Math.abs(o.hashCode() % hashTableRows.length);

        return hashTableRows[index].contains(o);
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

    private static <E> void copyElementHelper(E element1, E element2) {
        element1 = element2;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < elementsCount) {
            //noinspection unchecked
            T[] copyArray = (T[]) new Object[elementsCount];

            int i = 0;

            for (E element : this) {
                copyElementHelper(copyArray[i], element);

                ++i;
            }

            return copyArray;
        }

        int i = 0;

        for (E element : this) {
            copyElementHelper(array[i], element);

            ++i;
        }

        if (array.length > elementsCount) {
            array[i] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        //if (!contains(e)) {
        int index = Math.abs(e.hashCode() % hashTableRows.length);

        if (hashTableRows[index] == null) {
            hashTableRows[index] = new ArrayList<>();
        }

        hashTableRows[index].add(e);

        ++elementsCount;
        ++modCount;

        return true;
       /* }

        return false;*/
    }

    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            int index = Math.abs(o.hashCode() % hashTableRows.length);
            hashTableRows[index].remove(o);

            --elementsCount;
            ++modCount;

            return true;
        }

        return false;
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

        for (ArrayList<E> row : hashTableRows) {
            for (E element : row) {
                if (element != null & !c.contains(element)) {
                    remove(element);
                }
            }
        }

        return startElementsCount != elementsCount;
    }

    @Override
    public void clear() {
        for (ArrayList<E> row : hashTableRows) {
            row.clear();
        }

        modCount += elementsCount;
        elementsCount = 0;
    }
}
