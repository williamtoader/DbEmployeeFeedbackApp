package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    @NotNull
    Long userId;

    String firstName;
    String lastName;

    @JsonProperty
    String middleName;

    String email;
}
