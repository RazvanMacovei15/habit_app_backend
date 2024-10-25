package maco.habit_backend.services;

import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.UserHabit;

import java.util.List;

public interface UserHabitService {
    UserHabit save(UserHabit userHabit);
    List<UserHabit> getAll();
}
