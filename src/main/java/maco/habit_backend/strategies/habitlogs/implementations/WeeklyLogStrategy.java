package maco.habit_backend.strategies.habitlogs.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@AllArgsConstructor
public class WeeklyLogStrategy implements LogStrategy {
    private final WeeklyLogRepo weeklyLogRepository;

    @Override
    public void createLog(Habit habit, User user) {
        WeeklyLog weeklyLog = new WeeklyLog();

        // Get current date to calculate the week
        LocalDate today = LocalDate.now();

        // Calculate the week start and end dates
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate weekStartDay = today.with(weekFields.dayOfWeek(), 1); // Start of the week (Monday)
        LocalDate weekEndDay = today.with(weekFields.dayOfWeek(), 7);   // End of the week (Sunday)
        int yearWeek = weekStartDay.get(weekFields.weekOfWeekBasedYear());
        System.out.println("yearweek: " + yearWeek);

        // Set the weekly log properties
        weeklyLog.setYearWeek(yearWeek);
        weeklyLog.setHabit(habit);
        weeklyLog.setUser(user);
        weeklyLog.setCurrentCount(0);
        weeklyLog.setWeekStartDay(weekStartDay);
        weeklyLog.setWeekEndDay(weekEndDay);
        weeklyLog.setCompleted(false);

        // Default initial state
        weeklyLogRepository.save(weeklyLog);
    }
}
