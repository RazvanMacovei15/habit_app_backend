package maco.habit_backend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserDTO {
    private String username;
    private String email;
    private String password;
}
