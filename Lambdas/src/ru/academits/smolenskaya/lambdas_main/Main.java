package ru.academits.smolenskaya.lambdas_main;

import ru.academits.smolenskaya.lambdas_person.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> persons = new LinkedList<>();

        persons.add(new Person("Иван", 45));
        persons.add(new Person("Петр", 64));
        persons.add(new Person("Анастасия", 8));
        persons.add(new Person("Эрика", 9));
        persons.add(new Person("Варвара", 17));
        persons.add(new Person("Петр", 5));
        persons.add(new Person("Емельян", 25));
        persons.add(new Person("Ярослав", 15));
        persons.add(new Person("Таисия", 31));
        persons.add(new Person("Иван", 12));

        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println(uniqueNames.stream().collect(Collectors.joining(", ", "Имена: ", ".")));

        System.out.println();

        List<Person> personsUnder18 = persons.stream()
                .filter(x -> x.getAge() < 18)
                .toList();

        double personsUnder18AverageAge = personsUnder18.stream().
                mapToInt(Person::getAge).
                average().
                orElse(0);

        System.out.printf("Средний возраст людей младше 18 лет равен %f%n", personsUnder18AverageAge);

        System.out.println();

        Map<String, Double> averageAgeByPersons = persons.stream()
                .collect(Collectors
                        .groupingBy(Person::getName, Collectors
                                .averagingDouble(Person::getAge)));

        System.out.println("Средний возраст людей в разрезе имен:");
        averageAgeByPersons.forEach((name, age) -> System.out.printf("%s - %5.2f%n", name, age));

        System.out.println();

        List<Person> personsFrom18To45 = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 45)
                .toList();

        System.out.println(personsFrom18To45.stream()
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .collect(Collectors
                        .joining(", ", "Имена людей от 18 до 45 в порядке убывания возраста: ", ".")));
    }
}