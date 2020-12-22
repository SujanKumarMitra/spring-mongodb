package com.github.mitrakumarsujan.springmongodb.configuration;

import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableConfigurationProperties(MongoDbIndexConfigurationProperties.class)
public class MongoDbCollectionIndexAutoConfiguration {

    private MongoDbIndexConfigurer indexConfigurer;
    private MongoDbIndexConfigurationProperties properties;

    @Autowired
    public MongoDbCollectionIndexAutoConfiguration(
            MongoDbIndexConfigurer indexConfigurer,
            MongoDbIndexConfigurationProperties properties) {
        this.indexConfigurer = indexConfigurer;
        this.properties = properties;

    }

    @PostConstruct
    public void createSpecifiedIndexes() {
        createIndexes(properties.getIndexes());
    }

    public void createIndexes(List<MongoDbIndexConfiguration> indexConfigurations) {
        indexConfigurations.forEach(this::createIndex);
    }

    public void createIndex(MongoDbIndexConfiguration indexConfiguration) {
        String collectionName = indexConfiguration.getCollectionName();
        Document keys = indexConfiguration.getKeys();
        IndexOptions indexOptions = indexConfiguration.getIndexOptions();

        indexConfigurer.createIndex(collectionName, keys, indexOptions);
    }

}
