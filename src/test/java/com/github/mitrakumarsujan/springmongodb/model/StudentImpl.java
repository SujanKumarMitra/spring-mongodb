package com.github.mitrakumarsujan.springmongodb.model;

import java.util.Objects;

public class StudentImpl implements Student {

    private Long roll;
    private String name;

    public StudentImpl() {
    }

    public StudentImpl(Long roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    @Override
    public Long getRoll() {
        return roll;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRoll(Long roll) {
        this.roll = roll;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentImpl student = (StudentImpl) o;
        return Objects.equals(roll, student.roll) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roll, name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }
}
