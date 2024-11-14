package maco.habit_backend.dtos;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private int id;
    private String username;
    private String email;
}
