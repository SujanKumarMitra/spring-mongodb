package com.github.mitrakumarsujan.springmongodb.model;

import java.util.Objects;

public class SimpleStudent implements Student {

    private Long roll;
    private String name;

    public SimpleStudent() {
    }

    public SimpleStudent(Long roll, String name) {
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
        SimpleStudent that = (SimpleStudent) o;
        return Objects.equals(roll, that.roll) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roll, name);
    }

    @Override
    public String toString() {
        return "SimpleStudent{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }
}
