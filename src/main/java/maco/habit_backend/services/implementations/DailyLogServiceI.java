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
    public DailyLog save(DailyLog dailyLog) {
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public DailyLog getByIdAndUser(int id, User user) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        dailyLogRepo.deleteById(id);
    }

    @Override
    public List<DailyLog> getAll() {
        return dailyLogRepo.findAll();
    }
    @Transactional
    @Override
    public DailyLog updateStatus(DailyLog dailyLog) {
        Habit habit = dailyLog.getHabit();
        if(dailyLog.isCompleted()){
            dailyLog.setCompleted(false);
            habitService.updateHabitFromTrueToFalse(habit.getHabitId());
        } else {
            dailyLog.setCompleted(true);
        }
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user) {
        List<Habit> habits = habitService.getAllHabitsByOccurrenceAndUser(Occurrence.DAILY, user);
        for (Habit habit : habits) {
            DailyLog dailyLog = DailyLog.builder()
                    .habit(habit)
                    .date(date)
                    .user(user)
                    .isCompleted(false)
                    .build();

            dailyLogRepo.save(dailyLog);
        }

        return dailyLogRepo.findAllByDate(date);
    }

    @Override
    public List<DailyLog> getAllForUser(User user) {
        return dailyLogRepo.findAllByUser(user);
    }

    @Override
    public List<DailyLog> getDailyLogByDateAndUser(LocalDate date, User user) {
        return dailyLogRepo.findAllByDateAndUser(date, user);
    }

    @Override
    public DailyLog getDailyLogByHabitAndDate(int habitId, LocalDate date) {
        Optional<Habit> habit = habitService.getById(habitId);
        return habit.map(value -> dailyLogRepo.getDailyLogByHabitAndDate(value, date)).orElse(null);
    }


}
