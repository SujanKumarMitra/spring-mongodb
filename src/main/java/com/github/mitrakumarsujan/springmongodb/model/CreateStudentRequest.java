package com.github.mitrakumarsujan.springmongodb.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateStudentRequest implements Student {

    @NotNull(message = "roll should not be null")
    @Min(value = 1, message = "roll should be greater than zero")
    private Long roll;
    @NotNull(message = "name should not be null")
    @NotBlank(message = "name should not be blank")
    private String name;

    @Override
    public Long getRoll() {
        return roll;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRoll(Long roll) {
        this.roll = roll;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
