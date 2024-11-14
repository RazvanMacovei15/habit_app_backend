package maco.habit_backend.services.implementations;

import lombok.Builder;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.Log;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.exceptions.ResourceNotFoundException;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.LogService;
import maco.habit_backend.utils.HabitUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Builder
@Component
public class LogServiceImpl implements LogService {
    private HabitService habitService;
    private DailyLogRepo dailyLogRepo;
    private WeeklyLogRepo weeklyLogRepo;
    private HabitUtils habitUtils;

    public Log addUpdate(Log log){
        Habit habitToUpdate = log.getHabit();
        if(habitToUpdate.getTargetCount() == 1){
            return handleSingleTargetLogCompletion(log);
        }
        return handleMultipleTargetLogCompletion(log);
    }

    public Log decrementUpdate(Log log){
        int currentLogCount = log.getCurrentCount();
        currentLogCount--;
        return handleMultiTargetLogDecrement(log, currentLogCount);
    }

    private Log handleMultiTargetLogDecrement(Log log, int count) {
        Habit habit = log.getHabit();
        boolean logWasCompleted = log.isCompleted();
        if(count < 0){
            throw new IllegalArgumentException("Current count cannot be negative");
        }
        if(count < habit.getTargetCount() && logWasCompleted){
            log.setCompleted(false);
            habitUtils.updateHabitAfterDecrement(habit);
        }
        habit.setUpdatedAt(LocalDateTime.now());
        log.setCurrentCount(count);
        return saveLog(log);
    }

    private Log handleSingleTargetLogCompletion(Log log) {
        Habit habitToUpdate = log.getHabit();
        int previousStreak = habitToUpdate.getCurrentStreak();
        if(!log.isCompleted() && log.getCurrentCount() == 0){
            log.setCurrentCount(1);
            log.setCompleted(true);
            HabitUtils.updateStreak(habitToUpdate, log.isPreviousCompleted(), previousStreak);
        } else if(log.isCompleted() && log.getCurrentCount() == 1){
            log.setCurrentCount(0);
            log.setCompleted(false);
            HabitUtils.decrementCountsIfNeeded(habitToUpdate);
        } else {
            throw new ResourceNotFoundException("Invalid log");
        }
        habitToUpdate.setUpdatedAt(LocalDateTime.now());
        return saveLog(log);
    }
    private Log handleMultipleTargetLogCompletion(Log log) {
        Habit habit = log.getHabit();
        int currentCount = log.getCurrentCount();
        currentCount++;
        if (currentCount == habit.getTargetCount()) {
            // Completing the habit after reaching target count
            log.setCompleted(true);
            HabitUtils.updateStreak(habit, log.isPreviousCompleted(), habit.getCurrentStreak());
        }
        log.setCurrentCount(currentCount);
        habit.setUpdatedAt(LocalDateTime.now());
        return saveLog(log);
   }

    private Log saveLog(Log log) {
        if (log instanceof DailyLog) {
            return dailyLogRepo.save((DailyLog) log);
        } else if (log instanceof WeeklyLog) {
            return weeklyLogRepo.save((WeeklyLog) log);
        } else {
            throw new IllegalArgumentException("Unsupported log type");
        }
    }
}
