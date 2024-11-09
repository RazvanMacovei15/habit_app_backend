package maco.habit_backend.services;

import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;

import java.util.List;

public interface WeeklyLogService {
    List<WeeklyLog> findAll(User user);
    WeeklyLog createNewWeeklyLog(WeeklyLog weeklyLog);
    WeeklyLog updateWeeklyLog(WeeklyLog weeklyLog);
    WeeklyLog getById(int weeklyLogId);
    WeeklyLog addUpdateStatus(WeeklyLog weeklyLogToUpdate);
    WeeklyLog decrementUpdateStatus(WeeklyLog weeklyLogToUpdate);
    List<WeeklyLog> createWeeklyLogsOnGivenWeek(int yearWeek, User user);
}
