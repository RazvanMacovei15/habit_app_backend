package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.entities.Habit;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class WeeklyLogDTO {
    private int weeklyLogDTOId;
    private HabitDTO habitDTO;
    private int yearWeek;
    private LocalDate weekStartDay;
    private LocalDate weekEndDay;
    private int currentCount;
    private int targetCount;
    private boolean isCompleted;
}
