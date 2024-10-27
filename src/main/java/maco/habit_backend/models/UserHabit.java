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
    private String habitName;
    private int currentStreak;

}
