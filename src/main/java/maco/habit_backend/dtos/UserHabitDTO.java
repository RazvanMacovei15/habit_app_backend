package maco.habit_backend.dtos;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserHabitDTO {
    private String username;
    private String email;
    private String habitName;
    private int currentStreak;
}
