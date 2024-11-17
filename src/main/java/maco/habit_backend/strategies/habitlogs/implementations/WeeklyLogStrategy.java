package maco.habit_backend.strategies.habitlogs.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

@AllArgsConstructor
public class WeeklyLogStrategy implements LogStrategy {
    private final WeeklyLogRepo weeklyLogRepository;

    @Override
    public void createNewHabitLog(Habit habit, User user) {
        List<Integer> yearWeeks = weeklyLogRepository.findUniqueYearWeeks(user);
        System.out.println(yearWeeks);
        for (Integer yearWeek : yearWeeks){
            WeeklyLog weeklyLog = new WeeklyLog();

            int week  = yearWeek % 100;

            // Get current date to calculate the week
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            // Define WeekFields with Monday as the first day of the week
            WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);


            //method to set the week start and end days to the correct week
            LocalDate weekStartDay = LocalDate.of(year, 1, 1)
                    .with(weekFields.weekOfYear(), week)
                    .with(weekFields.dayOfWeek(), 1);

            LocalDate weekEndDay = weekStartDay.plusDays(6);


            // Set the weekly log properties
            weeklyLog.setYearWeek(yearWeek);
            weeklyLog.setHabit(habit);
            weeklyLog.setUser(user);
            weeklyLog.setCurrentCount(0);
            weeklyLog.setCompleted(false);
            weeklyLog.setPreviousCompleted(false);
            weeklyLog.setWeekStartDay(weekStartDay);
            weeklyLog.setWeekEndDay(weekEndDay);
            weeklyLogRepository.save(weeklyLog);
            System.out.println(weeklyLog);
        }
    }
}
