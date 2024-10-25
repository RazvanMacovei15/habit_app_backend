package maco.habit_backend.mapper;

import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class HabitMapper {
    public HabitDTO mapTo(Habit habit) {
        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .occurrence(habit.getOccurrence())
                .type(habit.getType())
                .build();
    }

    public Habit mapFrom(HabitDTO habitDto) {
        return Habit.builder()
                .id(habitDto.getId())
                .name(habitDto.getName())
                .description(habitDto.getDescription())
                .occurrence(habitDto.getOccurrence())
                .type(habitDto.getType())
                .build();
    }
}
