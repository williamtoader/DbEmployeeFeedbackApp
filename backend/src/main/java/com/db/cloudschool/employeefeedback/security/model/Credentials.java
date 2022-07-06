package com.db.cloudschool.employeefeedback.security.model;

import com.db.cloudschool.employeefeedback.model.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Credentials {
    public Credentials(String email, String passwordHash, String refreshTokenKey) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.refreshTokenKey = refreshTokenKey;
    }

    public Credentials(String email, String passwordHash, String refreshTokenKey, Identity identity) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.refreshTokenKey = refreshTokenKey;
        this.identity = identity;
    }

    @Id
    @GeneratedValue
    Long id;

    @Column(unique = true)
    String email;

    String passwordHash;

    String refreshTokenKey;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "identity_id", nullable = false, referencedColumnName = "id")
//    @MapsId
    Identity identity;
}
