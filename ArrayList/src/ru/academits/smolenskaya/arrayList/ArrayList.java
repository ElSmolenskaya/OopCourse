package ru.academits.smolenskaya.arrayList;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size;
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
            return currentIndex + 1 < size;
        }

        public E next() {
            if (modCount != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("The collection size was changed. New elements were added/removed while traversing the collection");
            }

            ++currentIndex;

            if (currentIndex >= size) {
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
        if (size < 0) {
            throw new IllegalArgumentException("size = " + size + " : size must be >= 0");
        }

        if (size < this.size) {
            for (int i = size; i < this.size; i++) {
                items[i] = null;
            }

            modCount += this.size - size;
            this.size = size;
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

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = 0; i < size; i++) {
            if (items[i].equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    private static <E> void toArrayHelper(E[] array1, E[] array2, int size) {
        System.arraycopy(array1, 0, array2, 0, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size);
        }

        toArrayHelper(items, array, size);

        if (size < array.length) {
            array[size] = null;
        }

        return array;
    }

    private void increaseCapacity() {
        capacity *= 2;

        items = Arrays.copyOf(items, capacity);
    }

    @Override
    public boolean add(E element) {
        if (size >= capacity) {
            increaseCapacity();
        }

        items[size] = element;

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("o = null: collection does not contain null elements");
        }

        for (int i = 0; i < size; i++) {
            if (items[i].equals(o)) {
                System.arraycopy(items, i + 1, items, i, size - i - 1);

                --size;
                ++modCount;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("Collection c is empty");
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
            throw new IllegalArgumentException("Collection c is empty");
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and <= " + size);
        }

        if (c.size() == 0) {
            throw new IllegalArgumentException("Collection c is empty");
        }

        int newSize = size + c.size();

        while (newSize > capacity) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + c.size(), size - index);

        addAllHelper(items, index, c.toArray());

        modCount += c.size();
        size += c.size();

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.size() == 0) {
            throw new IllegalArgumentException("Collection c is empty");
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

        while (i < size) {
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
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        modCount += size;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + size);
        }
    }

    private void checkElement(Object element) {
        if (element == null) {
            throw new IllegalArgumentException("element = null: collection must not contain null elements");
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        checkElement(element);

        E oldElement = items[index];
        items[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index == size) {
            add(element);

            return;
        }

        checkIndex(index);

        checkElement(element);

        if (size >= capacity) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);

        items[index] = element;

        ++size;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E oldElement = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        --size;
        ++modCount;

        return oldElement;
    }

    @Override
    public int indexOf(Object o) {
        checkElement(o);

        for (int i = 0; i < size; i++) {
            if (items[i] == o) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        checkElement(o);

        for (int i = size - 1; i >= 0; i--) {
            if (items[i] == o) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append("]");

        return stringBuilder.toString();
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