package com.db.cloudschool.employeefeedback.model;

import com.db.cloudschool.employeefeedback.security.model.Credentials;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @OneToOne
    private Credentials credentials;

    @ElementCollection
    List<String> roles;
}
