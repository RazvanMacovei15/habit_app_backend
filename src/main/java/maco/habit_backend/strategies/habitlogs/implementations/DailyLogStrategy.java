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
    public void createLog(Habit habit, User user) {
        DailyLog dailyLog = DailyLog.builder()
                        .habit(habit)
                        .date(LocalDate.now())
                        .isCompleted(false)
                        .user(user)
                        .build();

        dailyLogRepository.save(dailyLog);
    }
}
