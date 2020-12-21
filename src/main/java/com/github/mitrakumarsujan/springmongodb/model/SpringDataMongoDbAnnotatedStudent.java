package com.github.mitrakumarsujan.springmongodb.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("students")
public class SpringDataMongoDbAnnotatedStudent implements Student {

    @MongoId
    private ObjectId objectId;

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

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
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
                "objectId=" + objectId +
                ", roll=" + roll +
                ", name='" + name + '\'' +
                '}';
    }

}
