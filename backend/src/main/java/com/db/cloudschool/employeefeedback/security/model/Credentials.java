package com.db.cloudschool.employeefeedback.security.model;

import com.db.cloudschool.employeefeedback.model.Identity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


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

    @Id
    String email;

    String passwordHash;

    String refreshTokenKey;

    @OneToOne
    Identity identity;
}
