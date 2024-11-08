package maco.habit_backend.strategies.habitlogs;

import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.services.HabitService;

public interface LogStrategy {
    void createLog(Habit habit, User user);

}
