package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.services.HabitService;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class DailyLogMapper {

    private final HabitService habitService;
    private final HabitMapper habitMapper;

    public DailyLogDTO mapTo(DailyLog dailyLog) {
        return DailyLogDTO.builder()
                .id(dailyLog.getId())
                .date(dailyLog.getDate())
                .habitDTO(habitMapper.mapTo(dailyLog.getHabit()))
                .currentCount(dailyLog.getCurrentCount())
                .completed(dailyLog.isCompleted())
                .previousCompleted(dailyLog.isPreviousCompleted())
                .build();
    }

    public DailyLog mapFrom(DailyLogDTO dailyLogDTO) {
        return null;
    }
}
