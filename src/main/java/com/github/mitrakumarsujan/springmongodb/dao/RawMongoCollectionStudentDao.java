package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.SimpleStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Repository
public class RawMongoCollectionStudentDao implements StudentDao {

    private MongoCollection<Document> studentsCollection;

    @Autowired
    public RawMongoCollectionStudentDao(MongoDatabase database) {
        this.studentsCollection = database.getCollection("students");
    }

    @Override
    public Boolean createStudent(Student newStudent) {
        Document studentDocument = new Document()
                .append("roll", newStudent.getRoll())
                .append("name", newStudent.getName());
        try {
            InsertOneResult result = studentsCollection.insertOne(studentDocument);
            return result.wasAcknowledged();
        } catch (MongoWriteException e) {
            return Boolean.valueOf(false);
        }

    }

    @Override
    public Student getStudent(Long roll) {
        Document document = studentsCollection
                .find(eq("roll", roll))
                .iterator()
                .tryNext();
        if (document == null)
            return null;

        Long studentRoll = document.getLong("roll");
        String studentName = document.getString("name");

        return new SimpleStudent(studentRoll, studentName);
    }

    @Override
    public Boolean updateStudent(Student updatedStudent) {
        Long roll = updatedStudent.getRoll();
        String name = updatedStudent.getName();

        UpdateResult result = studentsCollection.updateOne(
                eq("roll", roll),
                set("name", name)
        );

        return result.wasAcknowledged() && result.getMatchedCount() == 1L;
    }

    @Override
    public Boolean deleteStudent(Long roll) {
        DeleteResult deleteResult = studentsCollection.deleteOne(
                eq("roll", roll)
        );

        return deleteResult.wasAcknowledged() && deleteResult.getDeletedCount() == 1L;
    }
}
