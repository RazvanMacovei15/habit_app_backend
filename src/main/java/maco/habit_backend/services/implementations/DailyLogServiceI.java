package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.repositories.DailyLogRepo;
import maco.habit_backend.services.DailyLogService;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class DailyLogServiceI implements DailyLogService {
    private final DailyLogRepo dailyLogRepo;
    @Override
    public DailyLog save(DailyLog dailyLog) {
        return null;
    }

    @Override
    public DailyLog getById(int id) {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public DailyLog update(DailyLog dailyLog) {
        return null;
    }

    @Override
    public DailyLog updateById(int dailyLogId) {
        return null;
    }

    @Override
    public List<DailyLog> getAll() {
        return dailyLogRepo.findAll();
    }
}
