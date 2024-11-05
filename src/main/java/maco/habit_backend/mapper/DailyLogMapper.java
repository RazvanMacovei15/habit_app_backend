package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class DailyLogMapper {
    private final HabitMapper habitMapper;

    public DailyLogDTO mapTo(DailyLog dailyLog) {
        return DailyLogDTO.builder()
                .id(dailyLog.getId())
                .date(dailyLog.getDate())
                .habitDTO(habitMapper.mapTo(dailyLog.getHabit()))
                .completed(dailyLog.isCompleted())
                .build();
    }

    public DailyLog mapFrom(DailyLogDTO dailyLogDTO) {
        return DailyLog.builder()
                .id(dailyLogDTO.getId())
                .date(dailyLogDTO.getDate())
                .habit(habitMapper.mapFrom(dailyLogDTO.getHabitDTO()))
                .isCompleted(dailyLogDTO.isCompleted())
                .build();
    }
}
