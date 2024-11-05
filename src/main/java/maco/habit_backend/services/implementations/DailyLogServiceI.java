package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.services.DailyLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class DailyLogServiceI implements DailyLogService {
    private final DailyLogRepo dailyLogRepo;
    private final HabitRepo habitRepo;
    @Override
    public DailyLog save(DailyLog dailyLog) {
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public DailyLog getById(int id) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public int deleteById(int id) {
        return dailyLogRepo.deleteById(id);
    }

    @Override
    public List<DailyLog> getAll() {
        return dailyLogRepo.findAll();
    }

    @Override
    public DailyLog updateStatus(DailyLog dailyLog) {
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user) {
        List<Habit> habits = habitRepo.findAllByOccurrenceAndUser(Occurrence.DAILY, user);
        for (Habit habit : habits) {
            DailyLog dailyLog = DailyLog.builder()
                    .habit(habit)
                    .date(date)
                    .isCompleted(false)
                    .build();

            dailyLogRepo.save(dailyLog);
        }

        return dailyLogRepo.findAllByDate(date);
    }
}
