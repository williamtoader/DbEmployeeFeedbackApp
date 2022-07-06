package com.db.cloudschool.employeefeedback.model;

import com.db.cloudschool.employeefeedback.security.model.Credentials;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Identity implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long apiUserId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "identity")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name= "profile_id", referencedColumnName = "id")
//    @MapsId
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "identity")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name= "email_id", referencedColumnName = "id")
//    @MapsId
    private Email email;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "identity")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name= "credentials_id", referencedColumnName = "id")
//    @MapsId
    private Credentials credentials;

    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roles;
}
