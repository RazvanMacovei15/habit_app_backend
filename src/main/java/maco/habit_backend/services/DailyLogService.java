package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {
    DailyLog save(DailyLog dailyLog);
    DailyLog getByIdAndUser(int id, User user);
    void deleteById(int id);
    List<DailyLog> getAll();
    DailyLog updateStatus(DailyLog dailyLog);
    List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user);
    List<DailyLog> getAllForUser(User user);
    List<DailyLog> getDailyLogByDateAndUser(LocalDate date, User user);
}
