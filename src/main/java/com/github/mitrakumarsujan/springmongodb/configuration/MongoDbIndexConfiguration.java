package com.github.mitrakumarsujan.springmongodb.configuration;

import org.bson.Document;

public class MongoDbIndexConfiguration {
    private String collectionName;
    private Document keys;
    private SetterMethodEnabledIndexOptions indexOptions;

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Document getKeys() {
        return keys;
    }

    public void setKeys(Document keys) {
        this.keys = keys;
    }

    public SetterMethodEnabledIndexOptions getIndexOptions() {
        return indexOptions;
    }

    public void setIndexOptions(SetterMethodEnabledIndexOptions indexOptions) {
        this.indexOptions = indexOptions;
    }

    @Override
    public String toString() {
        return "MongoDbIndexConfiguration{" +
                "collectionName='" + collectionName + '\'' +
                ", keys=" + keys +
                ", indexOptions=" + indexOptions +
                '}';
    }
}
