package ru.academits.smolenskaya.array_list_main;

import ru.academits.smolenskaya.array_list.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int capacity = 10;

        ArrayList<Integer> arrayList = new ArrayList<>(capacity);

        arrayList.ensureCapacity(20);

        int size = 8;

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }

        System.out.println("arrayList = " + arrayList);

        int index = 5;
        int newValue = 23;

        arrayList.add(index, newValue);
        System.out.println("arrayList = " + arrayList);

        arrayList.addAll(List.of(12, 13, 14));
        System.out.println("arrayList = " + arrayList);

        index = arrayList.size();

        arrayList.addAll(index, List.of(15, 16, 17));
        System.out.println("arrayList = " + arrayList);

        index = 5;
        newValue = 33;

        arrayList.set(index, newValue);
        System.out.println("arrayList = " + arrayList);

        System.out.printf("arrayList[%d] = %s%n", index, arrayList.get(index));

        arrayList.remove(index);
        System.out.println("arrayList = " + arrayList);

        Object deleteValue = 14;

        arrayList.remove(deleteValue);
        System.out.println("arrayList = " + arrayList);

        arrayList.removeAll(List.of(134, 15, 0, 13, 56, 45));
        System.out.println("arrayList = " + arrayList);

        arrayList.retainAll(List.of(16, 1, 2, 3, 12, 23));
        System.out.println("retained arrayList = " + arrayList);

        Object checkValue = 33;

        System.out.printf("arrayList.contains(%d) = %s%n", checkValue, arrayList.contains(checkValue));

        List<Integer> checkValues = List.of(23, 1, 16);
        System.out.printf("arrayList.contains(%s) = %s%n", checkValues, new HashSet<>(arrayList).containsAll(checkValues));

        checkValue = 3;

        System.out.printf("arrayList.indexOf(%d) = %s%n", checkValue, arrayList.indexOf(checkValue));

        newValue = 3;

        arrayList.add(newValue);
        System.out.println("arrayList = " + arrayList);
        System.out.printf("arrayList.indexOf(%d) = %s%n", checkValue, arrayList.lastIndexOf(checkValue));

        System.out.printf("arrayList.size = %d%n", arrayList.size());

        arrayList.trimToSize();
        System.out.println("arrayList = " + arrayList);

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }

        System.out.println("arrayList = " + arrayList);

        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 12, 12, 13, 14, 14};
        arrayList.toArray(array);

        System.out.printf("arrayList.toArray = " + Arrays.toString(array));

        System.out.println();

        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.addAll(List.of(1, 2, 3));
        System.out.println("arrayList1 = " + arrayList1);
    }
}