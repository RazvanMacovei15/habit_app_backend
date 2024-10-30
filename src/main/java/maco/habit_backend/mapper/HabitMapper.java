package maco.habit_backend.mapper;


import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HabitMapper {


    public HabitDTO mapTo(Habit habit) {
        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .occurrence(habit.getOccurrence())
                .type(habit.getType())
                .isCompleted(habit.isCompleted())
                .currentStreak(habit.getCurrentStreak())
                .bestStreak(habit.getBestStreak())
                .totalCount(habit.getTotalCount())
                .dateCreated(habit.getCreatedAt())
                .lastUpdated(habit.getUpdatedAt())
                .build();
    }

    public Habit mapFrom(HabitDTO habitDto) {
        return Habit.builder()
                .id(habitDto.getId())
                .name(habitDto.getName())
                .description(habitDto.getDescription())
                .occurrence(habitDto.getOccurrence())
                .type(habitDto.getType())
                .isCompleted(habitDto.isCompleted())
                .currentStreak(habitDto.getCurrentStreak())
                .bestStreak(habitDto.getBestStreak())
                .totalCount(habitDto.getTotalCount())
                .createdAt(habitDto.getDateCreated())
                .updatedAt(habitDto.getLastUpdated())
                .build();
    }

    public Habit createNewHabit(HabitDTO habitDTO, User user) {
        boolean isCompleted = false;
        int currentStreak = 0;
        int bestStreak = 0;
        int totalCount = 0;
        LocalDateTime dateCreated = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();
        return Habit.builder()
                .name(habitDTO.getName())
                .description(habitDTO.getDescription())
                .occurrence(habitDTO.getOccurrence())
                .type(habitDTO.getType())
                .isCompleted(isCompleted)
                .currentStreak(currentStreak)
                .bestStreak(bestStreak)
                .totalCount(totalCount)
                .createdAt(dateCreated)
                .updatedAt(lastUpdated)
                .user(user).build();
    }

    public HabitDTO mapToNewHabitDTO(Habit habit) {
        return HabitDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .occurrence(habit.getOccurrence())
                .type(habit.getType())
                .isCompleted(habit.isCompleted())
                .currentStreak(habit.getCurrentStreak())
                .bestStreak(habit.getBestStreak())
                .totalCount(habit.getTotalCount())
                .dateCreated(habit.getCreatedAt())
                .lastUpdated(habit.getUpdatedAt())
                .build();
    }
}
