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
        return dailyLogRepo.save(dailyLog);
    }

    @Override
    public DailyLog getById(int id) {
        return dailyLogRepo.findById(id).orElse(null);
    }

    @Override
    public int deleteById(int id) {
        return dailyLogRepo.deleteById(id);
    }

//    @Override
//    public DailyLog update(DailyLog dailyLog) {
//        return dailyLogRepo.update(dailyLog);
//    }
//
//    @Override
//    public DailyLog updateById(int dailyLogId) {
//        return dailyLogRepo.updateById(dailyLogId);
//    }

    @Override
    public List<DailyLog> getAll() {
        return dailyLogRepo.findAll();
    }

    @Override
    public DailyLog updateStatus(DailyLog dailyLog) {
        return dailyLogRepo.save(dailyLog);
    }
}
