package ru.academits.smolenskaya.arrayListHome_main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> convertFileToList(String fileName) throws IOException {
        ArrayList<String> resultList = new ArrayList<>();

        try (Scanner scanner = new Scanner(Files.newInputStream(Path.of(fileName)))) {
            while (scanner.hasNextLine()) {
                resultList.add(scanner.nextLine().toLowerCase());
            }
        }

        return resultList;
    }

    public static void removeEvenNumbers(ArrayList<Integer> integerList) {
        for (int i = 0; i < integerList.size(); i++) {
            if (integerList.get(i) % 2 == 0) {
                integerList.remove(i);

                i--;
            }
        }
    }

    public static ArrayList<Integer> getUniqueNumbersList(ArrayList<Integer> integerList) {
        ArrayList<Integer> resultIntegerList = new ArrayList<>();

        for (Integer number : integerList) {
            if (!resultIntegerList.contains(number)) {
                resultIntegerList.add(number);
            }
        }

        return resultIntegerList;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> listFromFile = convertFileToList("ArrayListHome/src/ru/academits/smolenskaya/files/input.txt");

        for (String string : listFromFile) {
            System.out.println(string);
        }

        System.out.println();

        ArrayList<Integer> integerList1 = new ArrayList<>(Arrays.asList(2, 2, 5, 4, 2, 7, 8, 88, 1, 3, 23, 54, 7, 35, 89, 90, 6));
        System.out.println("integerList1 = " + integerList1);

        removeEvenNumbers(integerList1);
        System.out.println("integerList1 = " + integerList1);

        ArrayList<Integer> integerList2 = new ArrayList<>(Arrays.asList(2, 2, 5, 4, 2, 7, 8, 8, 4, 1, 3, 7, 23, 54, 2, 6, 7));
        System.out.println("integerList2 = " + integerList2);

        ArrayList<Integer> integerList3 = getUniqueNumbersList(integerList2);
        System.out.println("integerList3 = " + integerList3);
    }
}