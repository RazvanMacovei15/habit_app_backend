package maco.habit_backend.strategies.habitlogs.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.DayOfWeek;
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
        int year = today.getYear();

        // Define WeekFields with Monday as the first day of the week
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        // Calculate the week start and end dates
        LocalDate weekStartDay = today.with(weekFields.dayOfWeek(), 1); // Start of the week (Monday)
        LocalDate weekEndDay = today.with(weekFields.dayOfWeek(), 7);   // End of the week (Sunday)
        int yearWeek = year * 100 + (weekStartDay.get(weekFields.weekOfWeekBasedYear()));

        // Set the weekly log properties
        weeklyLog.setYearWeek(yearWeek);
        weeklyLog.setHabit(habit);
        weeklyLog.setUser(user);
        weeklyLog.setCurrentCount(0);
        weeklyLog.setCompleted(false);
        weeklyLog.setPreviousWeekCompleted(false);
        weeklyLog.setWeekStartDay(weekStartDay);
        weeklyLog.setWeekEndDay(weekEndDay);

        weeklyLogRepository.save(weeklyLog);
    }
}
