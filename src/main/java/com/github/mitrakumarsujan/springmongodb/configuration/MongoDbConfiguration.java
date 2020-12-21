package com.github.mitrakumarsujan.springmongodb.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties(MongoDbConfigurationProperties.class)
public class MongoDbConfiguration {

    private MongoDbConfigurationProperties properties;

    @Autowired
    public MongoDbConfiguration(MongoDbConfigurationProperties properties) {
        Objects.requireNonNull(properties);
        this.properties = properties;
    }

    @Bean
    public MongoDatabase getMongoDatabase(MongoClient client) {
        return client.getDatabase(properties.getDatabase());
    }


}
