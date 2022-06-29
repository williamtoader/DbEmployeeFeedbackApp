package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    @Column(unique = true)
    private String token;

    @Id
    private String address;

    private Boolean confirmed;

    @OneToOne
    private Identity identity;
}
