package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;

import java.util.List;

public interface DailyLogService {
    DailyLog save(DailyLog dailyLog);
    DailyLog getById(int id);
    int deleteById(int id);
    DailyLog update(DailyLog dailyLog);
    DailyLog updateById(int dailyLogId);
    List<DailyLog> getAll();
}
