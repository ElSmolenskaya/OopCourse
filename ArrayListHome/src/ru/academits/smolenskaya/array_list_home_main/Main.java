package ru.academits.smolenskaya.array_list_home_main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<String> getStringsListFromFile(String fileName) {
        ArrayList<String> strings = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String string;

            while ((string = bufferedReader.readLine()) != null) {
                strings.add(string);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Файл %s не найден%n", fileName);
        } catch (IOException e) {
            System.out.printf("При попытке работы с файлом %s возникла ошибка%n", fileName);
        }

        return strings;
    }

    public static void removeEvenNumbers(ArrayList<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) % 2 == 0) {
                numbers.remove(i);

                i--;
            }
        }
    }

    public static ArrayList<Integer> getUniqueNumbersList(ArrayList<Integer> numbers) {
        ArrayList<Integer> uniqueNumbers = new ArrayList<>(numbers.size());

        for (Integer number : numbers) {
            if (!uniqueNumbers.contains(number)) {
                uniqueNumbers.add(number);
            }
        }

        return uniqueNumbers;
    }

    public static void main(String[] args) {
        String fileName = "ArrayListHome/src/ru/academits/smolenskaya/files/input.txt";

        ArrayList<String> stringsFromFile = getStringsListFromFile(fileName);

        if (stringsFromFile.isEmpty()) {
            System.out.printf("Strings from file %s:%n", fileName);

            for (String string : stringsFromFile) {
                System.out.println(string);
            }
        }

        System.out.println();

        ArrayList<Integer> numbers1 = new ArrayList<>(Arrays.asList(2, 2, 5, 4, 2, 7, 8, 88, 1, 3, 23, 54, 7, 35, 89, 90, 6));
        System.out.println("Numbers1: " + numbers1);

        removeEvenNumbers(numbers1);
        System.out.println("Numbers1 after removing even numbers: " + numbers1);

        ArrayList<Integer> numbers2 = new ArrayList<>(Arrays.asList(2, 2, 5, 4, 2, 7, 8, 8, 4, 1, 3, 7, 23, 54, 2, 6, 7));
        System.out.println("Numbers2: " + numbers2);

        ArrayList<Integer> uniqueNumbers = getUniqueNumbersList(numbers2);
        System.out.println("Unique numbers from numbers2: " + uniqueNumbers);
    }
}