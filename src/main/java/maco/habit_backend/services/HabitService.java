package maco.habit_backend.services;

import maco.habit_backend.entities.Habit;

import java.util.List;
import java.util.Optional;

public interface HabitService {
    Habit save(Habit habit);
    List<Habit> getAll();
    Optional<Habit> getById(int id);
    void deleteById(int id);
    Habit updateHabitStatus(int habitId);
    void deleteAll();
    List<Habit> getAllHabitsByUserId(int userId);

}
