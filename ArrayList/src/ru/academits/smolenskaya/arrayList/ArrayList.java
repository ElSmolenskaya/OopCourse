package ru.academits.smolenskaya.arrayList;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int length;
    private int capacity;
    private int modCount;

    public ArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity = " + capacity + " : capacity must be > 0");
        }

        this.capacity = capacity;

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int modCount;

        public ArrayListIterator() {
            modCount = ArrayList.this.modCount;
        }

        public boolean hasNext() {
            return currentIndex + 1 < length;
        }

        public E next() {
            if (modCount != ArrayList.this.modCount) {
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
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    public void ensureCapacity(int capacity) {
        if (this.capacity < capacity) {
            items = Arrays.copyOf(items, capacity);

            this.capacity = capacity;
        }
    }

    public void trimToSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("size = " + size + " : size must be > 0");
        }

        if (size < length) {
            for (int i = size; i < length; i++) {
                items[i] = null;
            }

            modCount += length - size;
            length = size;
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = 0; i < length; i++) {
            if (items[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, length);
    }

    private static <E> void toArrayHelper(E[] array1, E[] array2, int length) {
        System.arraycopy(array1, 0, array2, 0, length);
    }

    @Override
    public <E1> E1[] toArray(E1[] array) {
        if (array.length < length) {
            //noinspection unchecked
            E1[] copyArray = (E1[]) new Object[length];

            toArrayHelper(items, copyArray, length);

            return copyArray;
        }

        toArrayHelper(items, array, length);

        if (length < array.length) {
            array[length] = null;
        }

        return array;
    }

    private void increaseCapacity() {
        capacity *= 2;

        items = Arrays.copyOf(items, capacity);
    }

    @Override
    public boolean add(E element) {
        if (length >= capacity) {
            increaseCapacity();
        }

        items[length] = element;

        ++length;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = 0; i < length; i++) {
            if (items[i].equals(o)) {
                System.arraycopy(items, i + 1, items, i, length - i - 1);

                --length;
                --modCount;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("The collection sent is empty");
        }

        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("The collection sent is empty");
        }

        for (E element : c) {
            add(element);
        }

        return true;
    }

    private static <E> void addAllHelper(E[] array1, int startIndex, E[] array2) {
        System.arraycopy(array2, 0, array1, startIndex, array2.length);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("The collection sent is empty");
        }

        int newSize = length + c.size();

        while (newSize > capacity) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + c.size(), length - index);

        addAllHelper(items, index, c.toArray());

        modCount += c.size();
        length += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("The collection sent is empty");
        }

        boolean result = false;

        for (Object element : c) {
            if (remove(element)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int i = 0;
        boolean isChanged = false;

        while (i < length) {
            if (!c.contains(items[i])) {
                remove(i);

                isChanged = true;
            } else {
                ++i;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        for (int i = 0; i < length; i++) {
            items[i] = null;
        }

        modCount = length;
        length = 0;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + length);
        }

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + length);
        }

        if (element == null) {
            throw new IllegalArgumentException("element = null: collection must not contain null elements");
        }

        E oldElement = items[index];
        items[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + length);
        }

        if (element == null) {
            throw new IllegalArgumentException("element = null: collection must not contain null elements");
        }

        if (length >= capacity) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, length - index);

        items[index] = element;

        ++length;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + length);
        }

        E oldElement = items[index];

        System.arraycopy(items, index + 1, items, index, length - index - 1);

        --length;
        --modCount;

        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = 0; i < length; i++) {
            if (items[i] == o) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = length - 1; i >= 0; i--) {
            if (items[i] == o) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < length; i++) {
            result.append(items[i]).append(", ");
        }

        result.delete(result.length() - 2, result.length());

        result.append("]");

        return result.toString();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        //noinspection ConstantConditions
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        //noinspection ConstantConditions
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //noinspection ConstantConditions
        return null;
    }
}