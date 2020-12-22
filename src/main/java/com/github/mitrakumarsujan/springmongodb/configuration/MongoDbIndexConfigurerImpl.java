package com.github.mitrakumarsujan.springmongodb.configuration;

import com.mongodb.client.model.IndexOptions;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;

@Configuration
public class MongoDbIndexConfigurerImpl implements MongoDbIndexConfigurer {

    private MongoTemplate mongoTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbIndexConfigurer.class);

    @Autowired
    public MongoDbIndexConfigurerImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String createIndex(String collectionName, Bson keys, IndexOptions indexOptions) {

        Objects.requireNonNull(collectionName, () -> "collectionName cannot be null");
        Objects.requireNonNull(keys, () -> "keys cannot be null");
        Objects.requireNonNull(indexOptions, () -> "indexOptions cannot be null");

        String index = mongoTemplate
                .getCollection(collectionName)
                .createIndex(keys, indexOptions);
        LOGGER.info("Index created for collection [{}] with keys [{}] and indexOptions [{}]", collectionName, keys, indexOptions);

        return index;
    }

    @Override
    public void dropIndex(String collectionName, Bson keys) {

        Objects.requireNonNull(collectionName, () -> "collectionName cannot be null");
        Objects.requireNonNull(keys, () -> "keys cannot be null");

        mongoTemplate
                .getCollection(collectionName)
                .dropIndex(keys);
        LOGGER.info("Index dropped for collection [{}] with keys [{}]", collectionName, keys);
    }

}
