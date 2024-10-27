package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.entities.Habit;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class DailyCheckDTO {

    private int id;
    private String date;
    private Habit habit;
    private boolean completed;

}
