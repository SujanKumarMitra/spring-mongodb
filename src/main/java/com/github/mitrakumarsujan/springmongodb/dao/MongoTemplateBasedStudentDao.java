package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.SpringDataMongoDbAnnotatedStudent;
import com.github.mitrakumarsujan.springmongodb.model.Student;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoTemplateBasedStudentDao implements StudentDao {

    private MongoTemplate mongoTemplate;

    @Autowired
    public MongoTemplateBasedStudentDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Boolean createStudent(Student newStudent) {
        SpringDataMongoDbAnnotatedStudent student = new SpringDataMongoDbAnnotatedStudent(newStudent);
        try {
            mongoTemplate.save(student);
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    @Override
    public List<Student> getStudents(Integer skip, Integer limit) {
        Query pageQuery = new Query()
                .skip(skip)
                .limit(limit);
        return Collections.unmodifiableList(mongoTemplate.find(pageQuery, SpringDataMongoDbAnnotatedStudent.class));
    }

    @Override
    public Student getStudent(Long roll) {

        Query query = new Query().addCriteria(where("roll").is(roll));
        return mongoTemplate.findOne(query, SpringDataMongoDbAnnotatedStudent.class);
    }

    @Override
    public Boolean updateStudent(Student updatedStudent) {
        Query query = new Query().addCriteria(where("roll").is(updatedStudent.getRoll()));
        Update update = new Update()
                .set("name", updatedStudent.getName());
        UpdateResult result = mongoTemplate.updateFirst(
                query,
                update,
                SpringDataMongoDbAnnotatedStudent.class);

        return result.wasAcknowledged() && result.getMatchedCount() == 1L;
    }

    @Override
    public Boolean deleteStudent(Long roll) {
        Query query = new Query().addCriteria(where("roll").is(roll));
        DeleteResult result = mongoTemplate.remove(query, SpringDataMongoDbAnnotatedStudent.class);
        return result.wasAcknowledged() && result.getDeletedCount() == 1L;
    }
}
