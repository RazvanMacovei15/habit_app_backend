package maco.habit_backend.services.implementations;

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

    @Override
    public WeeklyLog addUpdateStatus(WeeklyLog weeklyLogToUpdate) {
        Habit habit = weeklyLogToUpdate.getHabit();
        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean isCompleted = weeklyLogToUpdate.isCompleted();
        if(isCompleted){
            currentCount++;
        }
        if(!isCompleted && (currentCount < targetCount)){
            currentCount++;
            if(currentCount == targetCount){
                weeklyLogToUpdate.setCompleted(true);
            }
        }
        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
    }

    @Override
    public WeeklyLog decrementUpdateStatus(WeeklyLog weeklyLogToUpdate) {
        int currentCount = weeklyLogToUpdate.getCurrentCount();
        int targetCount = weeklyLogToUpdate.getHabit().getTargetCount();
        boolean isCompleted = weeklyLogToUpdate.isCompleted();
        System.out.println("Current count: " + currentCount);
        System.out.println("Target count: " + targetCount);
        System.out.println("Is completed: " + isCompleted);
        if(isCompleted){
            currentCount--;
            if(currentCount < targetCount){
                weeklyLogToUpdate.setCompleted(false);
            }
        }
        if(!isCompleted && (currentCount > 0)){
            currentCount--;
        }else{
            throw new IllegalArgumentException("Current count cannot be less than 0");
        }
        weeklyLogToUpdate.setCurrentCount(currentCount);
        return weeklyLogRepo.save(weeklyLogToUpdate);
    }

}
