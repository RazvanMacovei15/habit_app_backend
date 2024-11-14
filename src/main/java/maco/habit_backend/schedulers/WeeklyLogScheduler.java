package maco.habit_backend.schedulers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.repositories.WeeklyLogRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WeeklyLogScheduler {
    private final WeeklyLogRepo weeklyLogRepo;
    private final HabitRepo habitRepo;
    private final UserRepo userRepo;
    @Scheduled(cron = "0 5 0 * * SUN") //
    @Transactional // Ensure the operation is within a single transaction
    public void createWeeklyLogsOnMonday() {
        //TODO check if the list for the specific next week is already implemented
        LocalDate today = LocalDate.now();
        //extract year and week number from the current date
        int year = today.getYear();
        int weekNumber = today.get(WeekFields.ISO.weekOfWeekBasedYear());
        int yearWeek = year * 100 + weekNumber;
        List<User> users = userRepo.findAll();
        for (User user : users) {
            List<Habit> weeklyHabits = habitRepo.findAllByOccurrenceAndUser(Occurrence.WEEKLY, user);
            for (Habit habit : weeklyHabits) {
                boolean isPreviousWeekCompleted;
                WeeklyLog previousWeekLog = weeklyLogRepo.getWeeklyLogByHabitAndYearWeekAndUser(habit, yearWeek - 1, user);
                if(previousWeekLog == null){
                    isPreviousWeekCompleted = false;
                } else {
                    isPreviousWeekCompleted = previousWeekLog.isCompleted();
                }
                // Define WeekFields with Monday as the first day of the week
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
                // Get the first day of the week
                LocalDate firstDayOfWeek = LocalDate.of(year, 1, 1)
                        .with(weekFields.weekOfYear(), weekNumber)
                        .with(ChronoField.DAY_OF_WEEK, weekFields.getFirstDayOfWeek().getValue());
                // Get the last day of the week (Sunday for a week starting on Monday)
                LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);
                WeeklyLog weeklyLog = WeeklyLog.builder()
                        .yearWeek(yearWeek)
                        .habit(habit)
                        .user(user)
                        .currentCount(0)
                        .isCompleted(false)
                        .previousCompleted(isPreviousWeekCompleted)
                        .weekStartDay(firstDayOfWeek)
                        .weekEndDay(lastDayOfWeek)
                        .build();
                weeklyLogRepo.save(weeklyLog);
            }
        }
    }
}
