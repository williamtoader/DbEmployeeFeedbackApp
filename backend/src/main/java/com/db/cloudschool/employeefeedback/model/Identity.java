package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Identity implements Serializable {
    @Id
    @GeneratedValue
    private Long apiUserId;

    @OneToOne
    private Profile profile;

    @OneToOne
    private Email email;
}
