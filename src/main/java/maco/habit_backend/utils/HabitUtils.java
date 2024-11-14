package maco.habit_backend.utils;

import maco.habit_backend.entities.Habit;

import java.time.LocalDate;

public class HabitUtils {
    public static void updateHabitAfterDecrement(Habit habit, boolean logWasCompleted) {
        if(habit.getCurrentStreak() == habit.getBestStreak()
        && habit.getBestStreak() != 0
        && habit.getDayOfBestStreak().equals(LocalDate.now())
        && logWasCompleted){
            habit.setBestStreak(habit.getBestStreak() - 1);
        }
        if(logWasCompleted && habit.getCurrentStreak() > 0){
            habit.setBestStreak(habit.getCurrentStreak() -1);
        }
        if(logWasCompleted && habit.getTotalCount() > 0){
            habit.setTotalCount(habit.getTotalCount() - 1);
        }
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
