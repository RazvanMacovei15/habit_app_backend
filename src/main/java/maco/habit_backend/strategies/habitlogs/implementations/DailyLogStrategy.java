package maco.habit_backend.strategies.habitlogs.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.strategies.habitlogs.LogStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class DailyLogStrategy implements LogStrategy {
    private final DailyLogRepo dailyLogRepository;

    @Transactional
    @Override
    public void createNewHabitLog(Habit habit, User user) {
        List<LocalDate> uniqueDates = dailyLogRepository.findUniqueDates(user);

        for(LocalDate date : uniqueDates){

            DailyLog dailyLog = dailyLogRepository.getDailyLogByHabitAndDateAndUser(habit, date, user);

            if(dailyLog == null){

                DailyLog newDailyLog = DailyLog.builder()
                        .habit(habit)
                        .date(date)
                        .isCompleted(false)
                        .previousCompleted(false)
                        .currentCount(0)
                        .user(user)
                        .build();
                dailyLogRepository.save(newDailyLog);
                System.out.println(newDailyLog);
            }
        }
    }
}
