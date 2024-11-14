package maco.habit_backend.mapper;


import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class HabitMapper {


    public HabitDTO mapTo(Habit habit) {
        return HabitDTO.builder()
                .id(habit.getId())
                .habitName(habit.getName())
                .description(habit.getDescription())
                .occurrence(habit.getOccurrence())
                .type(habit.getType())
                .currentStreak(habit.getCurrentStreak())
                .bestStreak(habit.getBestStreak())
                .dayOfBestStreak(habit.getDayOfBestStreak())
                .totalCount(habit.getTotalCount())
                .targetCount(habit.getTargetCount())
                .dateCreated(habit.getCreatedAt())
                .lastUpdated(habit.getUpdatedAt())
                .build();
    }

    public Habit mapFrom(HabitDTO habitDto) {
        return Habit.builder()
                .id(habitDto.getId())
                .name(habitDto.getHabitName())
                .description(habitDto.getDescription())
                .occurrence(habitDto.getOccurrence())
                .type(habitDto.getType())
                .currentStreak(habitDto.getCurrentStreak())
                .bestStreak(habitDto.getBestStreak())
                .dayOfBestStreak(habitDto.getDayOfBestStreak())
                .totalCount(habitDto.getTotalCount())
                .targetCount(habitDto.getTargetCount())
                .createdAt(habitDto.getDateCreated())
                .updatedAt(habitDto.getLastUpdated())
                .build();
    }

    public Habit createNewHabit(HabitDTO habitDTO, User user) {
        int currentStreak = 0;
        int bestStreak = 0;
        int totalCount = 0;
        LocalDate dayOfBestStreak = LocalDate.now();
        LocalDateTime dateCreated = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();
        return Habit.builder()
                .name(habitDTO.getHabitName())
                .description(habitDTO.getDescription())
                .occurrence(habitDTO.getOccurrence())
                .type(habitDTO.getType())
                .targetCount(habitDTO.getTargetCount())
                .currentStreak(currentStreak)
                .bestStreak(bestStreak)
                .dayOfBestStreak(dayOfBestStreak)
                .totalCount(totalCount)
                .createdAt(dateCreated)
                .updatedAt(lastUpdated)
                .user(user).build();
    }

    public HabitDTO mapToNewHabitDTO(Habit habit) {
        return HabitDTO.builder()
                .id(habit.getId())
                .habitName(habit.getName())
                .description(habit.getDescription())
                .occurrence(habit.getOccurrence())
                .type(habit.getType())
                .currentStreak(habit.getCurrentStreak())
                .bestStreak(habit.getBestStreak())
                .dayOfBestStreak(habit.getDayOfBestStreak())
                .totalCount(habit.getTotalCount())
                .targetCount(habit.getTargetCount())
                .dateCreated(habit.getCreatedAt())
                .lastUpdated(habit.getUpdatedAt())
                .build();
    }
}
