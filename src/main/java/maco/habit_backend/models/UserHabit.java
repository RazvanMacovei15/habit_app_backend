package maco.habit_backend.models;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class UserHabit {

    private String username;
    private int userId;
    private int habitId;
    private String habitName;
    private boolean isHabitCompleted;
    private int currentStreak;
}
