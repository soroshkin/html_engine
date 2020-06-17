package com.icl.html_engine.service;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAgeX2() {
        return age * 2;
    }
}
