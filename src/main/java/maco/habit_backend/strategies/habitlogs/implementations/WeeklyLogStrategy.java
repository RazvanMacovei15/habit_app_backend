package maco.habit_backend.strategies.habitlogs.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.LocalDate;

@AllArgsConstructor
public class WeeklyLogStrategy implements LogStrategy {
    private final WeeklyLogRepo weeklyLogRepository;

    @Override
    public void createLog(Habit habit) {
        WeeklyLog weeklyLog = new WeeklyLog();
        weeklyLog.setHabit(habit);
        weeklyLog.setWeekStartDay(LocalDate.now());
        weeklyLog.setWeekEndDay(LocalDate.now().plusWeeks(1).minusDays(1)); // Ends after 1 week
        weeklyLog.setCompleted(false);

        // Default initial state
        weeklyLogRepository.save(weeklyLog);
    }
}
