package ru.academits.smolenskaya.list;

import java.util.NoSuchElementException;

public class List<T> {
    private ListItem<T> head;
    private int size;

    public int getSize() {
        return size;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty");
        }

        return head.getData();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index = " + index + ": index must be >= 0 and < " + size);
        }
    }

    private ListItem<T> getItem(int index) {
        ListItem<T> item = head;

        for (int i = 1; i <= index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public T get(int index) {
        checkIndex(index);

        return getItem(index).getData();
    }

    public T set(int index, T data) {
        checkIndex(index);

        ListItem<T> item = getItem(index);

        T oldData = item.getData();

        item.setData(data);

        return oldData;
    }

    public T deleteByIndex(int index) {
        checkIndex(index);

        if (index == 0) {
            return deleteFirst();
        }

        ListItem<T> item = getItem(index - 1);

        T deletedData = item.getNext().getData();

        item.setNext(item.getNext().getNext());

        size--;

        return deletedData;
    }

    public void insertFirst(T data) {
        head = new ListItem<>(data, head);

        size++;
    }

    public void insert(int index, T data) {
        if (index == 0) {
            insertFirst(data);

            return;
        }

        if (index == size) {
            ListItem<T> lastItem = getItem(size - 1);
            lastItem.setNext(new ListItem<>(data));

            size++;

            return;
        }

        checkIndex(index);

        ListItem<T> previousItem = getItem(index - 1);

        ListItem<T> listItem = new ListItem<>(data, previousItem.getNext());

        previousItem.setNext(listItem);

        size++;
    }

    public boolean deleteByValue(T data) {
        for (ListItem<T> item = head, previousItem = null; item != null; previousItem = item, item = item.getNext()) {
            if (item.getData().equals(data)) {
                if (previousItem == null) {
                    head = item.getNext();
                } else {
                    previousItem.setNext(item.getNext());
                }

                size--;

                return true;
            }
        }

        return false;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty");
        }

        T deletedData = head.getData();

        head = head.getNext();

        size--;

        return deletedData;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (ListItem<T> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getData()).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    public void reverse() {
        if (size <= 1) {
            return;
        }

        ListItem<T> item = head;
        ListItem<T> previousItem = null;
        ListItem<T> nextItem = head.getNext();

        while (nextItem != null) {
            item.setNext(previousItem);

            previousItem = item;
            item = nextItem;
            nextItem = item.getNext();
        }

        item.setNext(previousItem);
        head = item;
    }

    public List<T> getCopy() {
        List<T> listCopy = new List<>();

        if (head == null) {
            return listCopy;
        }

        ListItem<T> previousItemCopy = new ListItem<>(head.getData());
        listCopy.head = previousItemCopy;

        for (ListItem<T> item = head.getNext(); item != null; item = item.getNext()) {
            ListItem<T> itemCopy = new ListItem<>(item.getData());
            previousItemCopy.setNext(itemCopy);

            previousItemCopy = itemCopy;
        }

        return listCopy;
    }
}