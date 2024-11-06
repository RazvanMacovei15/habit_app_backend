package maco.habit_backend.strategies.habitlogs;

import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

public interface LogStrategy {
    void createLog(Habit habit, User user);
}
