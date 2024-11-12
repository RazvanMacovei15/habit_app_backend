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
    public List<WeeklyLog> findAll(User user) {
        return weeklyLogRepo.findAllByUser(user);
    }

    @Override
    public List<WeeklyLog> findAllByYearWeekAndUser(int yearWeek, User user) {
        return weeklyLogRepo.findAllByYearWeekAndUser(yearWeek, user);
    }

    @Override
    public WeeklyLog getById(int weeklyLogId) {
        return weeklyLogRepo.findById(weeklyLogId).orElse(null);
    }

    @Transactional
    @Override
    public WeeklyLog addUpdateStatus(WeeklyLog weeklyLogToUpdate) {

        Habit habit = weeklyLogToUpdate.getHabit();

        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean isPreviousWeekCompleted = weeklyLogToUpdate.isPreviousCompleted();

        currentCount++;
        if (currentCount == targetCount) {
            weeklyLogToUpdate.setCompleted(true);
            habitService.updateHabitFromFalseToTrue(habit.getHabitId(), isPreviousWeekCompleted, habit.getCurrentStreak());
        }
        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
    }

    @Override
    public WeeklyLog decrementUpdateStatus(WeeklyLog weeklyLogToUpdate) {
        Habit habit = weeklyLogToUpdate.getHabit();

        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean wasCompleted = weeklyLogToUpdate.isCompleted(); // Check if it was previously completed

        System.out.println("was completed: " + wasCompleted);

        currentCount--;
        if (currentCount < 0) {
            throw new IllegalArgumentException("Current count cannot be less than 0! You can't decrement anymore");
        }

        // Update completion status based on current count vs target count
        if (currentCount < targetCount) {
            weeklyLogToUpdate.setCompleted(false);

            // Only decrement totalCount if it was previously completed
            habitService.updateHabitFromTrueToFalse(habit.getHabitId(), wasCompleted);
        }

        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
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
        return weeklyLogRepo.findAllByYearWeekAndUser(yearWeek, user);
    }

    @Override
    public WeeklyLog getWeeklyLogByHabitAndYearWeekAndUser(Habit habit, int yearWeek, User user) {
        return weeklyLogRepo.getWeeklyLogByHabitAndYearWeekAndUser(habit, yearWeek, user);
    }
}
