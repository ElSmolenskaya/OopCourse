package ru.academits.smolenskaya.hashTable_main;

import ru.academits.smolenskaya.hashTable.HashTable;

public class Main {
    public static void main(String[] args) {
        int size = 10;

        HashTable<String> hashTable = new HashTable<>(size);

        for (int i = 0; i < 20; i++) {
            hashTable.add(String.format("String%d", i));
        }

        for (String text : hashTable) {
            System.out.println(text);
        }

        //Object[] stringArray = hashTable.toArray();

        /*System.out.println("hashTable = " + stringArray);*/
    }
}
