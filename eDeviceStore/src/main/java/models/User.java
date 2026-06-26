package models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = "passwordHash")
//@Entity
//@Table(name = "users")

public class User {
    private String id;
    private String name;
    private String surname;
    private String login;
    private String passwordHash;
    private String email;
    private Role role;

    public User copy(){
        return User.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .login(login)
                .passwordHash(passwordHash)
                .email(email)
                .role(role)
                .build();
    }

}
