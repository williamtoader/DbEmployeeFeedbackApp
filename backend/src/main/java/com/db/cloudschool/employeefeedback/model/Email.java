package com.db.cloudschool.employeefeedback.model;

import lombok.*;

import javax.persistence.*;

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
