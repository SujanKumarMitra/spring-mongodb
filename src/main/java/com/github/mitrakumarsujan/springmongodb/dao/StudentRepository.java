package com.github.mitrakumarsujan.springmongodb.dao;

import com.github.mitrakumarsujan.springmongodb.model.SpringDataMongoDbAnnotatedStudent;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<SpringDataMongoDbAnnotatedStudent, Long> {

    @Aggregation({"{'$skip' : ?0}", "{'$limit' : ?1}"})
    List<SpringDataMongoDbAnnotatedStudent> findAllBySkipAndLimit(Integer skip, Integer limit);

    Optional<SpringDataMongoDbAnnotatedStudent> findFirstByRoll(Long roll);

    Optional<SpringDataMongoDbAnnotatedStudent> deleteSingleByRoll(Long roll);

}
