package ru.academits.smolenskaya.list_main;

import ru.academits.smolenskaya.list.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> integerList = new List<>();

        int size = 20;

        for (int i = 1; i <= size; i++) {
            integerList.insertFirst(i);
        }

        System.out.println(integerList);

        int index = 4;
        System.out.printf("getData(%d) = %s%n", index, integerList.get(index));

        System.out.println("size = " + integerList.getSize());

        System.out.println("firstItemData = " + integerList.getFirst());

        System.out.println();

        int oldData = integerList.set(0, 30);
        System.out.println(integerList);

        System.out.println("oldData = " + oldData);

        System.out.println();

        int deletedData = integerList.deleteByIndex(integerList.getSize() - 1);
        System.out.println(integerList);
        System.out.println("deletedData = " + deletedData);

        System.out.println();

        integerList.insert(1, 50);
        System.out.println(integerList);

        System.out.println();

        Integer data = 2;

        System.out.println("isDeleted = " + integerList.deleteByValue(data));
        System.out.println(integerList);

        System.out.println();

        deletedData = integerList.deleteFirst();

        System.out.println(integerList);
        System.out.println("deletedData = " + deletedData);

        System.out.println();

        integerList.reverse();
        System.out.println("reversed array = " + integerList);

        System.out.println();

        List<Integer> integerListCopy = integerList.getCopy();
        System.out.println("copy = " + integerListCopy);
    }
}