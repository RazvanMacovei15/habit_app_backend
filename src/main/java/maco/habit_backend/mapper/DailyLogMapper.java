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

    public DailyLogDTO mapTo(DailyLog dailyLog) {
        return DailyLogDTO.builder()
                .id(dailyLog.getDailyLogId())
                .date(dailyLog.getDate())
                .completed(dailyLog.isCompleted())
                .build();
    }

    public DailyLog mapFrom(DailyLogDTO dailyLogDTO) {
        return DailyLog.builder()
                .dailyLogId(dailyLogDTO.getId())
                .date(dailyLogDTO.getDate())
                .isCompleted(dailyLogDTO.isCompleted())
                .build();
    }
}
