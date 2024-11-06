package maco.habit_backend.services;

import maco.habit_backend.entities.WeeklyLog;

public interface WeeklyLogService {
    WeeklyLog createNewWeeklyLog(WeeklyLog weeklyLog);
    WeeklyLog updateWeeklyLog(WeeklyLog weeklyLog);
    WeeklyLog incrementCount(WeeklyLog weeklyLog);
    WeeklyLog decrementCount(WeeklyLog weeklyLog);
}
