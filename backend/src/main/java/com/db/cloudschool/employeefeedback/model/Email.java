package com.db.cloudschool.employeefeedback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String address;

    private Boolean confirmed;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "identity_id", nullable = false, referencedColumnName = "id")
//    @MapsId
    private Identity identity;
}
