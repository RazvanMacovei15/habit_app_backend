package maco.habit_backend.models;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserHabit {
    private String username;
    private String email;
    private int habitId;
    private String habitName;
    private boolean isHabitCompleted;
    private int currentStreak;
}
