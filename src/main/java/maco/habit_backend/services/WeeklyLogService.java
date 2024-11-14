package maco.habit_backend.services;

import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;

import java.util.List;

public interface WeeklyLogService {
    WeeklyLog getById(int weeklyLogId);
    List<WeeklyLog> createWeeklyLogsOnGivenWeek(int yearWeek, User user);
    List<WeeklyLog> findAllByYearWeekAndUser(int yearWeek, User user);
    WeeklyLog getWeeklyLogByHabitAndYearWeekAndUser(Habit habit, int yearWeek, User user);
}
