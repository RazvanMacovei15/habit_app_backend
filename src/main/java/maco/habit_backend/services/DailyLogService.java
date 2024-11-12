package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {
    DailyLog save(DailyLog dailyLog);
    DailyLog getByIdAndUser(int id, User user);
    void deleteById(int id);
    List<DailyLog> getAllForUser(User user);
    List<DailyLog> getDailyLogByDateAndUser(LocalDate date, User user);
    DailyLog getDailyLogByHabitAndDate(int habitId, LocalDate date);
    //NEW SERVICES BASES ON NEW FORMAT
    DailyLog addUpdateStatus(DailyLog dailyLogToUpdate);
    DailyLog decrementUpdateStatus(DailyLog dailyLogToUpdate);
    List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user);
    DailyLog updateSingleTargetStatus(DailyLog dailyLogToUpdate);
}
