package maco.habit_backend.utils;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.repositories.HabitRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class HabitUtils {
    private final HabitRepo habitRepo;
    public void updateHabitAfterDecrement(Habit habit) {
        if (habit.getCurrentStreak() == habit.getBestStreak()
                && habit.getBestStreak() != 0
                && habit.getDayOfBestStreak().equals(LocalDate.now())
        ) {
            habit.setBestStreak(habit.getBestStreak() - 1);
        }
        if (habit.getCurrentStreak() > 0) {
            habit.setCurrentStreak(habit.getCurrentStreak() - 1);
        }
        if (habit.getTotalCount() > 0) {
            habit.setTotalCount(habit.getTotalCount() - 1);
        }
        System.out.println(habit);
        habitRepo.save(habit);
    }

    public static void decrementCountsIfNeeded(Habit habit) {
        if (habit.getCurrentStreak() > 0) {
            habit.setCurrentStreak(habit.getCurrentStreak() - 1);
        }
        if (habit.getBestStreak() > 0 && habit.getDayOfBestStreak().equals(LocalDate.now())) {
            habit.setBestStreak(habit.getBestStreak() - 1);
        }
        if (habit.getTotalCount() > 0) {
            habit.setTotalCount(habit.getTotalCount() - 1);
        }
    }

    public static void updateStreak(Habit habit, boolean previousLogCompleted, int previousStreak) {
        int newStreak = previousLogCompleted ? previousStreak + 1 : 1;
        habit.setCurrentStreak(newStreak);
        if (newStreak > habit.getBestStreak()) {
            habit.setBestStreak(newStreak);
            habit.setDayOfBestStreak(LocalDate.now());
        }
        habit.setTotalCount(habit.getTotalCount() + 1);
    }
}
