package maco.habit_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class HabitDTO {

    private int id;

    private String name;

    private String description;

    private Occurrence occurrence;

    private Type type;
}
