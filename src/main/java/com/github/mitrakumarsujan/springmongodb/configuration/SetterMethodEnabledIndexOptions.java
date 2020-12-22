package com.github.mitrakumarsujan.springmongodb.configuration;

import com.mongodb.client.model.IndexOptions;

/**
 * An extension of {@link IndexOptions} with setter methods for injection.
 *
 * @author skmitra
 */
public class SetterMethodEnabledIndexOptions extends IndexOptions {

    void setUnique(boolean unique) {
        unique(unique);
    }

}
