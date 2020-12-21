package com.github.mitrakumarsujan.springmongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("students")
public class SpringDataMongoDbAnnotatedStudent implements Student {

    @Field("roll")
    private Long roll;
    @Field("name")
    private String name;

    public SpringDataMongoDbAnnotatedStudent() {
    }

    public SpringDataMongoDbAnnotatedStudent(Long roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    public SpringDataMongoDbAnnotatedStudent(Student student) {
        this(student.getRoll(), student.getName());
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
    public String toString() {
        return "SpringDataAnnotatedStudent{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }
}
