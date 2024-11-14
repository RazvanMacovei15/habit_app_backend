package maco.habit_backend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.services.DailyLogService;
import maco.habit_backend.services.HabitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DailyLogServiceI implements DailyLogService {
    private final DailyLogRepo dailyLogRepo;
    private final HabitService habitService;

    @Override
    public DailyLog getById(int id) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public List<DailyLog> findAllByDateAndUser(LocalDate date, User user) {
        return dailyLogRepo.findAllByDateAndUserOrderByHabit(date, user);
    }

    @Override
    public List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user) {
        List<Habit> habits = habitService.getAllHabitsByOccurrenceAndUser(Occurrence.DAILY, user);
        for (Habit habit : habits) {
            boolean isPreviousWeekCompleted;
            DailyLog previousDailyLog = dailyLogRepo.getDailyLogByHabitAndDateAndUser(habit, date.minusDays(1), user);
            if(previousDailyLog == null){
                isPreviousWeekCompleted = false;
            } else {
                isPreviousWeekCompleted = previousDailyLog.isCompleted();
            }
            DailyLog dailyLog = DailyLog.builder()
                    .habit(habit)
                    .date(date)
                    .user(user)
                    .isCompleted(false)
                    .currentCount(0)
                    .previousCompleted(isPreviousWeekCompleted)
                    .build();
            dailyLogRepo.save(dailyLog);
        }
        return dailyLogRepo.findAllByDate(date);
    }

    @Override
    public DailyLog getDailyLogByHabitAndDateAndUser(Habit habit, LocalDate date, User user) {
        return dailyLogRepo.getDailyLogByHabitAndDateAndUser(habit, date, user);
    }
}
