package maco.habit_backend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.WeeklyLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WeeklyLogServiceI implements WeeklyLogService {

    private final WeeklyLogRepo weeklyLogRepo;
    private final HabitService habitService;

    @Override
    public List<WeeklyLog> findAll(User user) {
        return weeklyLogRepo.findAllByUser(user);
    }

    @Override
    public WeeklyLog createNewWeeklyLog(WeeklyLog weeklyLog) {
        return weeklyLogRepo.save(weeklyLog);
    }

    @Override
    public WeeklyLog updateWeeklyLog(WeeklyLog weeklyLog) {
        return null;
    }

    @Override
    public WeeklyLog incrementCount(WeeklyLog weeklyLog) {
        return null;
    }

    @Override
    public WeeklyLog decrementCount(WeeklyLog weeklyLog) {
        return null;
    }

    @Override
    public WeeklyLog getById(int weeklyLogId) {
        return weeklyLogRepo.findById(weeklyLogId).orElse(null);
    }

    @Transactional
    @Override
    public WeeklyLog addUpdateStatus(WeeklyLog weeklyLogToUpdate) {

        Habit habit = weeklyLogToUpdate.getHabit();

        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean isPreviousWeekCompleted = weeklyLogToUpdate.isPreviousWeekCompleted();

        currentCount++;
        if (currentCount == targetCount) {
            weeklyLogToUpdate.setCompleted(true);
            habitService.updateHabitFromFalseToTrue(habit.getHabitId(), isPreviousWeekCompleted, habit.getCurrentStreak());
        }
        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
    }

    @Override
    public WeeklyLog decrementUpdateStatus(WeeklyLog weeklyLogToUpdate) {
        Habit habit = weeklyLogToUpdate.getHabit();

        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean isPreviousWeekCompleted = weeklyLogToUpdate.isPreviousWeekCompleted();

        currentCount--;
        if(currentCount < targetCount){
            habitService.updateHabitFromTrueToFalse(habit.getHabitId());
        }
        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
    }
}
