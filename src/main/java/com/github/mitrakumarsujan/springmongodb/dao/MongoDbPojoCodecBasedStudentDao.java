package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.MongoDbBsonAnnotatedStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.pojo.PojoCodecProvider.builder;

@Repository
public class MongoDbPojoCodecBasedStudentDao implements StudentDao {

    private StudentDao delegatee;
    private MongoCollection<MongoDbBsonAnnotatedStudent> codecBasedStudentsCollection;

    @Autowired
    public MongoDbPojoCodecBasedStudentDao(
            MongoCollectionBasedStudentDao delegatee,
            MongoDatabase database) {
        this.delegatee = delegatee;
        CodecRegistry pojoCodecRegistry = fromProviders(builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(getDefaultCodecRegistry(), pojoCodecRegistry);

        this.codecBasedStudentsCollection = database
                .withCodecRegistry(codecRegistry)
                .getCollection("students", MongoDbBsonAnnotatedStudent.class);
    }

    @Override
    public Boolean createStudent(Student newStudent) {

        MongoDbBsonAnnotatedStudent student = new MongoDbBsonAnnotatedStudent(newStudent);
        try {
            InsertOneResult result = codecBasedStudentsCollection.insertOne(student);
            return result.wasAcknowledged();
        } catch (MongoWriteException e) {
            return false;
        }
    }

    @Override
    public List<Student> getStudents(Integer skip, Integer limit) {
        Spliterator<MongoDbBsonAnnotatedStudent> spliterator = codecBasedStudentsCollection
                .find()
                .skip(skip)
                .limit(limit)
                .spliterator();
        return StreamSupport
                .stream(spliterator, false)
                .collect(toList());
    }

    @Override
    public Student getStudent(Long roll) {
        return codecBasedStudentsCollection
                .find(eq("roll", roll))
                .limit(1)
                .iterator()
                .tryNext();
    }

    @Override
    public Boolean updateStudent(Student updatedStudent) {
        return delegatee.updateStudent(updatedStudent);
    }

    @Override
    public Boolean deleteStudent(Long roll) {
        return delegatee.deleteStudent(roll);
    }
}
