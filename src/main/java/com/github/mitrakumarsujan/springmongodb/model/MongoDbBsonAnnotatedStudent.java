package com.github.mitrakumarsujan.springmongodb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Objects;

public class MongoDbBsonAnnotatedStudent implements Student {

    @BsonId
    @JsonIgnore
    private ObjectId id;
    @BsonProperty("roll")
    private Long roll;
    @BsonProperty("name")
    private String name;

    public MongoDbBsonAnnotatedStudent() {
    }

    public MongoDbBsonAnnotatedStudent(Student student) {
        this(student.getRoll(), student.getName());
    }

    public MongoDbBsonAnnotatedStudent(Long roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    @Override
    public Long getRoll() {
        return roll;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
        MongoDbBsonAnnotatedStudent student = (MongoDbBsonAnnotatedStudent) o;
        return Objects.equals(id, student.id)
                && Objects.equals(roll, student.roll)
                && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roll, name);
    }

    @Override
    public String toString() {
        return "SimpleStudent{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }
}
