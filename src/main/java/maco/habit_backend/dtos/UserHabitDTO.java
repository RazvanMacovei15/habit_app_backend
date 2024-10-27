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
    private int habitId;
    private String habitName;
    private boolean isHabitCompleted;
    private int currentStreak;
}
