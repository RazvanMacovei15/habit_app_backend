package maco.habit_backend.services;

import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;

import java.util.List;
import java.util.Optional;

public interface HabitService {
    Habit save(Habit habit, User user);
    List<Habit> getAll();
    Optional<Habit> getById(int id);
    void deleteById(int id);
    Habit updateHabitStatus(int habitId);
    void deleteAllForUser(User user);
    List<Habit> getAllHabitsByUserId(int userId);
    Habit updateHabitDetails(int habitId, HabitDTO habitDTO);
    List<Habit> getAllHabitsByOccurrenceAndUser(Occurrence occurrence, User user);
    Habit updateHabitFromTrueToFalse(int habitId, boolean shouldDecrementTotalCount);
    Habit updateHabitFromFalseToTrue(int habitId, boolean isPreviousHabitLogCompleted, int previousStreak);
    void deleteAllHabitsForUser(User user);
}
