package com.github.mitrakumarsujan.springmongodb.model;

/**
 * Interface representing Student entity
 */
public interface Student {

    Long getRoll();

    String getName();

    void setRoll(Long roll);

    void setName(String name);
}
