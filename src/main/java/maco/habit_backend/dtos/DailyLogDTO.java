package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.entities.Habit;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class DailyLogDTO {

    private int id;
    private LocalDate date;
    private HabitDTO habitDTO;
    private UserDTO userDTO;
    private boolean completed;

}
