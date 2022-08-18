package ru.academits.smolenskaya.list;

public class List<T> {
    private ListItem<T> head;
    private int count;

    public int getSize() {
        return count;
    }

    public T getFirstItemData() {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        return head.getData();
    }

    public T getData(int index) {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index = " + index + ": index must be > 0 and < " + count);
        }

        ListItem<T> listItem = head;

        for (int i = 1; i <= index; i++) {
            listItem = listItem.getNext();
        }

        return listItem.getData();
    }

    public T setData(int index, T data) {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index = " + index + ": index must be > 0 and < " + count);
        }

        ListItem<T> listItem = head;

        for (int i = 1; i <= index; i++) {
            listItem = listItem.getNext();
        }

        T oldData = listItem.getData();

        listItem.setData(data);

        return oldData;
    }

    public T deleteItem(int index) {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index = " + index + ": index must be > 0 and < " + count);
        }

        ListItem<T> listItem = head;

        if (index == 0) {
            head = head.getNext();
            count--;

            return listItem.getData();
        }

        for (int i = 1; i < index; i++) {
            listItem = listItem.getNext();
        }

        T oldData = listItem.getNext().getData();

        listItem.setNext(listItem.getNext().getNext());

        count--;

        return oldData;
    }

    public void insertHeadItem(T data) {
        ListItem<T> listItem = new ListItem<>(data);

        listItem.setNext(head);

        head = listItem;
        count++;
    }

    public void insertItem(int index, T data) {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index = " + index + ": index must be > 0 and < " + count);
        }

        if (index == 0) {
            insertHeadItem(data);

            return;
        }

        ListItem<T> prevListItem = head;

        for (int i = 1; i < index; i++) {
            prevListItem = prevListItem.getNext();
        }

        ListItem<T> listItem = new ListItem<>(data);

        listItem.setNext(prevListItem.getNext());
        prevListItem.setNext(listItem);

        count++;
    }

    public boolean deleteItem(T data) {
        for (ListItem<T> listItem = head, prevListItem = null; listItem != null; prevListItem = listItem, listItem = listItem.getNext()) {
            if (listItem.getData() == data) {
                if (prevListItem == null) {
                    head = listItem.getNext();
                } else {
                    prevListItem.setNext(listItem.getNext());
                }

                count--;

                return true;
            }
        }

        return false;
    }

    public T deleteHeadItem() {
        if (head == null) {
            throw new NullPointerException("head is null");
        }

        T headData = head.getData();

        head = head.getNext();

        return headData;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        if (head != null) {
            for (ListItem<T> listItem = head; listItem != null; listItem = listItem.getNext()) {
                result.append(listItem.getData()).append(", ");
            }

            result.delete(result.length() - 2, result.length());
        }

        result.append("]");

        return result.toString();
    }

    public void reverse() {
        if (count > 1) {
            ListItem<T> listItem = head;
            ListItem<T> prevListItem = null;
            ListItem<T> nextListItem = head.getNext();

            while (nextListItem != null) {
                listItem.setNext(prevListItem);

                prevListItem = listItem;
                listItem = nextListItem;
                nextListItem = listItem.getNext();
            }

            listItem.setNext(prevListItem);
            head = listItem;
        }
    }

    public List<T> getCopy() {
        List<T> listCopy = new List<>();

        for (ListItem<T> listItem = head; listItem != null; listItem = listItem.getNext()) {
            listCopy.insertHeadItem(listItem.getData());
        }

        listCopy.reverse();

        return listCopy;
    }
}