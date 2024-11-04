package maco.habit_backend.strategies.habitlogs.factory;

import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.repositories.MonthlyLogRepo;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.strategies.habitlogs.LogStrategy;
import maco.habit_backend.strategies.habitlogs.implementations.DailyLogStrategy;
import maco.habit_backend.strategies.habitlogs.implementations.MonthlyLogStrategy;
import maco.habit_backend.strategies.habitlogs.implementations.WeeklyLogStrategy;
import org.springframework.stereotype.Component;

@Component
public class LogStrategyFactory {
    private final DailyLogRepo dailyLogRepository;
    private final WeeklyLogRepo weeklyLogRepository;
    private final MonthlyLogRepo monthlyLogRepository;

    public LogStrategyFactory(DailyLogRepo dailyLogRepository,
                              WeeklyLogRepo weeklyLogRepository,
                              MonthlyLogRepo monthlyLogRepository) {
        this.dailyLogRepository = dailyLogRepository;
        this.weeklyLogRepository = weeklyLogRepository;
        this.monthlyLogRepository = monthlyLogRepository;
    }

    public LogStrategy getStrategy(Occurrence occurrence) {
        return switch (occurrence) {
            case DAILY -> new DailyLogStrategy(dailyLogRepository);
            case WEEKLY -> new WeeklyLogStrategy(weeklyLogRepository);
            case MONTHLY -> new MonthlyLogStrategy(monthlyLogRepository);
            default -> throw new IllegalArgumentException("Unknown occurrence type: " + occurrence);
        };
    }
}
