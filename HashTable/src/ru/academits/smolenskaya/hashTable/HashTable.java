package ru.academits.smolenskaya.hashTable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private ArrayList<E>[] table;
    private int modCount;

    public HashTable(int size) {
        //noinspection unchecked
        table = (ArrayList<E>[]) new ArrayList[size];
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int modCount;

        public HashTableIterator() {
            modCount = HashTableIterator.this.modCount;
        }

        public boolean hasNext() {
            return currentIndex + 1 < length;
        }

        public E next() {
            if (modCount != HashTable.this.modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            ++currentIndex;

            if (currentIndex >= length) {
                throw new NoSuchElementException("The collection is over");
            }

            return items[currentIndex];
        }
    }

    @Override
    public int size() {
        return table.length;
    }

    @Override
    public boolean isEmpty() {
        return table.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = Math.abs(o.hashCode() % table.length);

        return table[index].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (!contains(e)) {
            add(e);

            ++modCount;

            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (contains(o)) {
            int index = Math.abs(o.hashCode() % table.length);
            table[index].remove(o);

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
        for (E element : c) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int startModCount = modCount;

        for (Object element : c) {
            remove(element);
        }

        return startModCount == modCount;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
