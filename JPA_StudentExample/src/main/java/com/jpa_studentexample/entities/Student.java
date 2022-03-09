package com.jpa_studentexample.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Student {


    @Id
    private int id;

    private String name;
    private int age;
    private LocalDate birthday;

    public Student(){}


    public Student(int id, String name, int age, LocalDate birthday){
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
