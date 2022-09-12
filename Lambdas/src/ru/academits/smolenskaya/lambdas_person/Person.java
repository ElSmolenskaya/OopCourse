package ru.academits.smolenskaya.lambdas_person;

@SuppressWarnings("ClassCanBeRecord")
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name is empty: name must not be empty.");
        }

        if (age < 0) {
            throw new IllegalArgumentException("Age = " + age + ": age must be >= 0");
        }

        final int maximumAge = 130;

        if (age > maximumAge) {
            throw new IllegalArgumentException("Age = " + age + ": age must be <= " + maximumAge);
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}