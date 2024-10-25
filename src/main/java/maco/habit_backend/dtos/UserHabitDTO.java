package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

import java.time.LocalDateTime;
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserHabitDTO {

    private int id;
    private int userId;
    private int habitId;
    private int currentStreak;
    private int bestStreak;
    private int totalCount;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

}
