package maco.habit_backend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.WeeklyLogService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;

@AllArgsConstructor
@Service
public class WeeklyLogServiceI implements WeeklyLogService {
    private final WeeklyLogRepo weeklyLogRepo;
    private final HabitService habitService;

    @Override
    public List<WeeklyLog> findAllByYearWeekAndUser(int yearWeek, User user) {
        return weeklyLogRepo.findAllByYearWeekAndUserOrderByHabit(yearWeek, user);
    }

    @Override
    public WeeklyLog getById(int weeklyLogId) {
        return weeklyLogRepo.findById(weeklyLogId).orElse(null);
    }

    @Override
    public List<WeeklyLog> createWeeklyLogsOnGivenWeek(int yearWeek, User user) {
        List<Habit> habits = habitService.getAllHabitsByOccurrenceAndUser(Occurrence.WEEKLY, user);
        for (Habit habit : habits) {
            boolean isPreviousWeekCompleted;
            WeeklyLog previousWeekLog = weeklyLogRepo.getWeeklyLogByHabitAndYearWeekAndUser(habit, yearWeek - 1, user);
            if(previousWeekLog == null){
                isPreviousWeekCompleted = false;
            } else {
                isPreviousWeekCompleted = previousWeekLog.isCompleted();
            }
            // Convert the integer to a string
            String yearWeekStr = String.valueOf(yearWeek);
            // Extract the year and week parts
            int year = Integer.parseInt(yearWeekStr.substring(0, 4)); // "2024" -> 2024
            int week = Integer.parseInt(yearWeekStr.substring(4, 6)); // "45" -> 45
            // Define WeekFields with Monday as the first day of the week
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
            // Get the first day of the week
            LocalDate firstDayOfWeek = LocalDate.of(year, 1, 1)
                    .with(weekFields.weekOfYear(), week)
                    .with(ChronoField.DAY_OF_WEEK, weekFields.getFirstDayOfWeek().getValue());
            // Get the last day of the week (Sunday for a week starting on Monday)
            LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);
            WeeklyLog weeklyLog = WeeklyLog.builder()
                    .habit(habit)
                    .yearWeek(yearWeek)
                    .user(user)
                    .weekStartDay(firstDayOfWeek)
                    .weekEndDay(lastDayOfWeek)
                    .currentCount(0)
                    .isCompleted(false)
                    .previousCompleted(isPreviousWeekCompleted)
                    .build();
            weeklyLogRepo.save(weeklyLog);
        }
        return weeklyLogRepo.findAllByYearWeekAndUserOrderByHabit(yearWeek, user);
    }

    @Override
    public WeeklyLog getWeeklyLogByHabitAndYearWeekAndUser(Habit habit, int yearWeek, User user) {
        return weeklyLogRepo.getWeeklyLogByHabitAndYearWeekAndUser(habit, yearWeek, user);
    }
}
