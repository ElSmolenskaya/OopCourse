package ru.academits.smolenskaya.hash_table_main;

import ru.academits.smolenskaya.hash_table.HashTable;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        int size = 10;

        HashTable<String> hashTable = new HashTable<>(size);

        for (int i = 0; i < 6; i++) {
            hashTable.add(String.format("String%d", i));
        }

        System.out.println(hashTable);

        System.out.println();

        List<String> list1 = List.of("Строка1", "Строка2", "Строка3");
        System.out.println("hashTable.addAll" + list1 + " = " + hashTable.addAll(list1));
        System.out.println(hashTable);

        System.out.println("hashTable.size = " + hashTable.size());

        System.out.println();

        Object[] stringArray = hashTable.toArray(new String[10]);

        System.out.print("hashTable.toArray = ");

        for (Object string : stringArray) {
            System.out.print(string + ", ");
        }

        System.out.println();
        System.out.println();

        System.out.println("hashTable.containsAll" + list1 + " = " + hashTable.containsAll(list1));

        System.out.println();

        List<String> list2 = List.of("Строка1", "Строка2", "Строка4");
        System.out.println("hashTable.containsAll" + list2 + " = " + hashTable.containsAll(list2));

        System.out.println();

        System.out.println("hashTable.removeAll" + list2 + " = " + hashTable.removeAll(list2));
        System.out.println(hashTable);

        System.out.println();

        List<String> list3 = List.of("String1", "String2", "String3", "String4", "String222");
        System.out.println("hashTable.retainAll" + list3 + " = " + hashTable.retainAll(list3));
        System.out.println(hashTable);

        System.out.println();

        System.out.println("hashTable.isEmpty = " + hashTable.isEmpty());

        System.out.println("hashTable.size = " + hashTable.size());

        hashTable.clear();
        System.out.println(hashTable);

        System.out.println("hashTable.size = " + hashTable.size());

        System.out.println();

        HashTable<String> hashTable1 = new HashTable<>();
        hashTable1.add(null);
        System.out.println("hashTable1 = " + hashTable1);
    }
}