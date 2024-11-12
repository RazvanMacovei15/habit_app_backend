package maco.habit_backend.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.services.DailyLogService;
import maco.habit_backend.services.HabitService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DailyLogServiceI implements DailyLogService {
    private final DailyLogRepo dailyLogRepo;

    private final HabitService habitService;
    @Override
    public DailyLog save(DailyLog dailyLog) {
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public DailyLog getByIdAndUser(int id, User user) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public DailyLog getById(int id) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        dailyLogRepo.deleteById(id);
    }

    @Override
    public DailyLog updateSingleTargetStatus(DailyLog dailyLogToUpdate) {
        return null;
    }

    @Override
    public List<DailyLog> getAllForUser(User user) {
        return dailyLogRepo.findAllByUser(user);
    }

    @Override
    public List<DailyLog> findAllByDateAndUser(LocalDate date, User user) {
        return dailyLogRepo.findAllByDateAndUser(date, user);
    }

    @Override
    public DailyLog addUpdateStatus(DailyLog dailyLogToUpdate) {
        Habit habit = dailyLogToUpdate.getHabit();

        int currentCount = dailyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean isPreviousDayCompleted = dailyLogToUpdate.isPreviousCompleted();

        boolean wasCompleted = dailyLogToUpdate.isCompleted(); // Check if it was previously completed

        System.out.println("was completed: " + wasCompleted);

        int habitTargetCount = habit.getTargetCount();

        if (habitTargetCount == 1) {
            // Toggle completion based on the current state of the daily log
            if (dailyLogToUpdate.isCompleted()) {
                // If it's currently completed, set to incomplete and decrement the count
                dailyLogToUpdate.setCompleted(false);
                dailyLogToUpdate.setCurrentCount(0);
                habitService.updateHabitFromTrueToFalse(habit.getHabitId(), wasCompleted);
            } else {
                // If it's currently incomplete, set to complete and increment the count
                dailyLogToUpdate.setCompleted(true);
                dailyLogToUpdate.setCurrentCount(1);
                habitService.updateHabitFromFalseToTrue(habit.getHabitId(), wasCompleted, habit.getCurrentStreak());
            }
        } else {
            currentCount++;
            if (currentCount == targetCount) {
                dailyLogToUpdate.setCompleted(true);
                habitService.updateHabitFromFalseToTrue(habit.getHabitId(), isPreviousDayCompleted, habit.getCurrentStreak());
            }
            dailyLogToUpdate.setCurrentCount(currentCount);
        }

        return dailyLogRepo.save(dailyLogToUpdate);    }

    @Override
    public DailyLog decrementUpdateStatus(DailyLog dailyLogToUpdate) {
        Habit habit = dailyLogToUpdate.getHabit();

        int currentCount = dailyLogToUpdate.getCurrentCount();
        int targetCount = habit.getTargetCount();
        boolean wasCompleted = dailyLogToUpdate.isCompleted(); // Check if it was previously completed

        System.out.println("was completed: " + wasCompleted);

        currentCount--;
        if (currentCount < 0) {
            throw new IllegalArgumentException("Current count cannot be less than 0! You can't decrement anymore");
        }

        // Update completion status based on current count vs target count
        if (currentCount < targetCount) {
            dailyLogToUpdate.setCompleted(false);

            // Only decrement totalCount if it was previously completed
            habitService.updateHabitFromTrueToFalse(habit.getHabitId(), wasCompleted);
        }

        dailyLogToUpdate.setCurrentCount(currentCount);
        return dailyLogRepo.save(dailyLogToUpdate);
    }

    @Override
    public List<DailyLog> createDailyLogsOnGivenDate(LocalDate date, User user) {

        List<Habit> habits = habitService.getAllHabitsByOccurrenceAndUser(Occurrence.DAILY, user);

        for (Habit habit : habits) {
            boolean isPreviousWeekCompleted;
            DailyLog previousDailyLog = dailyLogRepo.getDailyLogByHabitAndDateAndUser(habit, date.minusDays(1), user);


            if(previousDailyLog == null){
                isPreviousWeekCompleted = false;
            } else {
                isPreviousWeekCompleted = previousDailyLog.isCompleted();
            }
            DailyLog dailyLog = DailyLog.builder()
                    .habit(habit)
                    .date(date)
                    .user(user)
                    .isCompleted(false)
                    .currentCount(0)
                    .previousCompleted(isPreviousWeekCompleted)
                    .build();

            dailyLogRepo.save(dailyLog);
        }

        return dailyLogRepo.findAllByDate(date);
    }

    @Override
    public DailyLog getDailyLogByHabitAndDateAndUser(Habit habit, LocalDate date, User user) {
        return dailyLogRepo.getDailyLogByHabitAndDateAndUser(habit, date, user);
    }
}
