package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Identity implements Serializable {
    @Id
    @GeneratedValue
    private Long apiUserId;

    @OneToOne
    private Profile profile;

    @OneToOne
    private Email email;
}
