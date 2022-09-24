package ru.academits.smolenskaya.lambdas_main;

import ru.academits.smolenskaya.lambdas_person.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Иван", 45),
                new Person("Петр", 64),
                new Person("Анастасия", 8),
                new Person("Эрика", 9),
                new Person("Варвара", 17),
                new Person("Петр", 5),
                new Person("Емельян", 25),
                new Person("Ярослав", 15),
                new Person("Таисия", 31),
                new Person("Иван", 12)
        );

        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println(uniqueNames.stream().collect(Collectors.joining(", ", "Имена: ", ".")));

        System.out.println();

        List<Person> personsUnder18 = persons.stream()
                .filter(person -> person.getAge() < 18)
                .toList();

        double personsUnder18AverageAge = personsUnder18.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);

        System.out.printf("Средний возраст людей младше 18 лет равен %5.2f%n", personsUnder18AverageAge);

        System.out.println();

        Map<String, Double> averageAgesByPersonName = persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println("Средний возраст людей в разрезе имен:");
        averageAgesByPersonName.forEach((name, age) -> System.out.printf("%15s - %-5.2f%n", name, age));

        System.out.println();

        System.out.println(persons.stream()
                .filter(person -> person.getAge() >= 20 && person.getAge() <= 45)
                .sorted((person1, person2) -> person2.getAge() - person1.getAge())
                .map(Person::getName)
                .collect(Collectors.joining(", ", "Имена людей от 20 до 45 в порядке убывания возраста: ", ".")));
    }
}