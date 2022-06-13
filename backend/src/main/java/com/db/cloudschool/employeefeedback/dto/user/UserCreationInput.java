package com.db.cloudschool.employeefeedback.dto.user;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Data
public class UserCreationInput {
    String firstName;
    String lastName;
    String username;
    String email;
    String password;
}
