package maco.habit_backend.services;

import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogService {
    DailyLog getById(int id);
    List<DailyLog> findAllByDateAndUser(LocalDate date,User user);
    DailyLog getDailyLogByHabitAndDateAndUser(Habit habit, LocalDate date, User user);
    List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user);
}
