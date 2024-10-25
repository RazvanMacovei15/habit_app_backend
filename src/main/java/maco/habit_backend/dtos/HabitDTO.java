package maco.habit_backend.dtos;

import lombok.*;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class HabitDTO {

    private int id;

    private String name;

    private String description;

    private Occurrence occurrence;

    private Type type;
}
