package com.github.mitrakumarsujan.springmongodb.configuration;

import com.mongodb.client.model.IndexOptions;
import org.bson.conversions.Bson;

public interface MongoDbIndexConfigurer {

    String createIndex(String collectionName, Bson keys, IndexOptions indexOptions);

    void dropIndex(String collectionName, Bson keys);

}
