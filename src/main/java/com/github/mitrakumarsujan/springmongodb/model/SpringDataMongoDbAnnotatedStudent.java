package com.github.mitrakumarsujan.springmongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("students")
public class SpringDataMongoDbAnnotatedStudent implements Student {

    @MongoId
    @JsonIgnore
    private ObjectId id;

    @Field("roll")
    @Indexed
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

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
        return "SpringDataMongoDbAnnotatedStudent{" +
                "objectId=" + id +
                ", roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }

}
