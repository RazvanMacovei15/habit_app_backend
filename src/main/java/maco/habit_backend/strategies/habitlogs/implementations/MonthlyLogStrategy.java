package maco.habit_backend.strategies.habitlogs.implementations;

import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.MonthlyLog;
import maco.habit_backend.entities.User;
import maco.habit_backend.repositories.MonthlyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.strategies.habitlogs.LogStrategy;

import java.time.LocalDate;

public class MonthlyLogStrategy implements LogStrategy {

    private final MonthlyLogRepo monthlyLogRepository;

    public MonthlyLogStrategy(MonthlyLogRepo monthlyLogRepository) {
        this.monthlyLogRepository = monthlyLogRepository;
    }

    @Override
    public void createLog(Habit habit, User user) {
        MonthlyLog monthlyLog = new MonthlyLog();
        monthlyLog.setHabit(habit);
        monthlyLog.setUser(user);
        monthlyLog.setMonth(LocalDate.now().getMonthValue());
        monthlyLog.setYear(LocalDate.now().getYear());
        monthlyLog.setCompleted(false); // Default initial state
        monthlyLogRepository.save(monthlyLog);
    }
}
