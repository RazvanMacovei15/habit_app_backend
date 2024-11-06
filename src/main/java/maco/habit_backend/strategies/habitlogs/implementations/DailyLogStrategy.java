package maco.habit_backend.strategies.habitlogs.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.LocalDate;
@AllArgsConstructor

public class DailyLogStrategy implements LogStrategy {
    private final DailyLogRepo dailyLogRepository;

    @Override
    public void createLog(Habit habit, User user) {
        DailyLog dailyLog = new DailyLog();
        dailyLog.setHabit(habit);
        dailyLog.setUser(user);
        dailyLog.setDate(LocalDate.now());
        dailyLog.setCompleted(false); // Default initial state
        dailyLogRepository.save(dailyLog);
    }
}
