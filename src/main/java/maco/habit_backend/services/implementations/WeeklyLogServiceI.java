package maco.habit_backend.services.implementations;

import lombok.AllArgsConstructor;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.repositories.WeeklyLogRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.WeeklyLogService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WeeklyLogServiceI implements WeeklyLogService {

    private final WeeklyLogRepo weeklyLogRepo;
    private final HabitService habitService;

    @Override
    public WeeklyLog createNewWeeklyLog(WeeklyLog weeklyLog) {
        return null;
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
}
