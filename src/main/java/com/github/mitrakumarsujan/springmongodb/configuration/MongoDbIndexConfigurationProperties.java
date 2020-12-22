package com.github.mitrakumarsujan.springmongodb.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.mongodb")
public class MongoDbIndexConfigurationProperties {

    private List<MongoDbIndexConfiguration> indexes;

    public List<MongoDbIndexConfiguration> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<MongoDbIndexConfiguration> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "MongoDbIndexConfigurationProperties{" +
                "indexes=" + indexes +
                '}';
    }
}
