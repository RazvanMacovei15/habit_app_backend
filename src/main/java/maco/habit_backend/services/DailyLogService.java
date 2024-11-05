package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {
    DailyLog save(DailyLog dailyLog);
    DailyLog getById(int id);
    int deleteById(int id);
    List<DailyLog> getAll();
    DailyLog updateStatus(DailyLog dailyLog);
    //create a daily for every habit with occurrence=DAILY and a given date
    List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user);
}
