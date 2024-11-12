package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {
    DailyLog save(DailyLog dailyLog);
    DailyLog getByIdAndUser(int id, User user);
    DailyLog getById(int id);
    void deleteById(int id);
    List<DailyLog> getAllForUser(User user);
    List<DailyLog> findAllByDateAndUser(LocalDate date,User user);
    DailyLog getDailyLogByHabitAndDateAndUser(Habit habit, LocalDate date, User user);
    //NEW SERVICES BASES ON NEW FORMAT
    DailyLog addUpdateStatus(DailyLog dailyLogToUpdate);
    DailyLog decrementUpdateStatus(DailyLog dailyLogToUpdate);
    List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user);
    DailyLog updateSingleTargetStatus(DailyLog dailyLogToUpdate);
}
