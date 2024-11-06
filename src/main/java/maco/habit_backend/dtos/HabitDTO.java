package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class HabitDTO {

    private int id;

    private String habitName;

    private String description;

    private Occurrence occurrence;

    private Type type;

    private int currentStreak;

    private int bestStreak;

    private int totalCount;

    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;

    private UserDTO userDTO;

}
