package ru.academits.smolenskaya.hash_table;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private final ArrayList<E>[] lists;
    private static final int DEFAULT_ARRAY_LENGTH = 100;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        lists = (ArrayList<E>[]) new ArrayList[DEFAULT_ARRAY_LENGTH];
    }

    public HashTable(int arrayLength) {
        if (arrayLength <= 0) {
            throw new IllegalArgumentException("Size = " + arrayLength + ": size must be > 0");
        }

        //noinspection unchecked
        lists = (ArrayList<E>[]) new ArrayList[arrayLength];
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentListIndex = -1;
        private int currentElementIndex = -1;
        private int visitedElementsCount = 0;
        private final int startModCount = modCount;

        public boolean hasNext() {
            return visitedElementsCount < size;
        }

        public E next() {
            if (startModCount != modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("The collection is over");
            }

            if ((currentListIndex >= 0) && (currentElementIndex < lists[currentListIndex].size() - 1)) {
                ++currentElementIndex;
                ++visitedElementsCount;

                return lists[currentListIndex].get(currentElementIndex);
            }

            ++currentListIndex;

            while (currentListIndex < lists.length) {
                if (lists[currentListIndex] != null && !lists[currentListIndex].isEmpty()) {
                    break;
                }

                ++currentListIndex;
            }

            currentElementIndex = 0;
            ++visitedElementsCount;

            return lists[currentListIndex].get(currentElementIndex);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(Object o) {
        if (o == null) {
            return 0;
        }

        return Math.abs(o.hashCode() % lists.length);
    }

    @Override
    public boolean contains(Object o) {
        if (size == 0) {
            return false;
        }

        int index = getIndex(o);

        if (lists[index] == null || lists[index].isEmpty()) {
            return false;
        }

        return lists[index].contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] resultArray = new Object[size];

        int i = 0;

        for (E element : this) {
            resultArray[i] = element;

            ++i;
        }

        return resultArray;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size, array.getClass());
        }

        int i = 0;

        for (E element : this) {
            //noinspection unchecked
            array[i] = (T) element;

            ++i;
        }

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e);

        if (lists[index] == null) {
            lists[index] = new ArrayList<>();
        }

        lists[index].add(e);

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        int index = getIndex(o);

        if (lists[index] == null || lists[index].isEmpty()) {
            return false;
        }

        boolean hasRemoved = lists[index].remove(o);

        if (hasRemoved) {
            --size;
            ++modCount;
        }

        return hasRemoved;
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
        int startSize = size;

        for (E element : c) {
            add(element);
        }

        return startSize != size;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int startSize = size;

        for (ArrayList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                int listStartSize = list.size();

                if (list.removeAll(c)) {
                    size -= listStartSize - list.size();

                    ++modCount;
                }
            }
        }

        return startSize != size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int startSize = size;

        for (ArrayList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                int listStartSize = list.size();

                if (list.retainAll(c)) {
                    size -= listStartSize - list.size();

                    ++modCount;
                }
            }
        }

        return startSize != size;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (ArrayList<E> list : lists) {
            if (list != null) {
                list.clear();
            }
        }

        ++modCount;
        size = 0;
    }

    public String toString() {
        if (size == 0) {
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