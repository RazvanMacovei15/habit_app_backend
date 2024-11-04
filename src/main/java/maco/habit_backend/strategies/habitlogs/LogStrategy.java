package maco.habit_backend.strategies.habitlogs;

import maco.habit_backend.entities.Habit;

public interface LogStrategy {
    void createLog(Habit habit);
}
