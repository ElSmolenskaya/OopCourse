package ru.academits.smolenskaya.lambdas_main;

import ru.academits.smolenskaya.lambdas_person.Person;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Person> persons = new LinkedList<>();

        persons.add(new Person("Иван", 45));
        persons.add(new Person("Петр", 64));
        persons.add(new Person("Анастасия", 8));
        persons.add(new Person("Эрика", 9));
        persons.add(new Person("Варвара", 17));
        persons.add(new Person("Аркадий", 5));
        persons.add(new Person("Емельян", 25));
        persons.add(new Person("Ярослав", 15));
        persons.add(new Person("Давид", 31));
        persons.add(new Person("Таисия", 12));

        //LinkedList<String> uniqueNames = persons.stream().distinct(x -> x.getName()).collect(Collectors.toList());
        //System.out.println(uniqueNames);
    }
}
