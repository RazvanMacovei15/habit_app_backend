package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class DailyLogMapper {
    private final HabitMapper habitMapper;
    private final UserMapper userMapper;

    public DailyLogDTO mapTo(DailyLog dailyLog) {
        return DailyLogDTO.builder()
                .id(dailyLog.getDailyLogId())
                .date(dailyLog.getDate())
                .habitDTO(habitMapper.mapTo(dailyLog.getHabit()))
                .userDTO(userMapper.mapTo(dailyLog.getUser()))
                .completed(dailyLog.isCompleted())
                .build();
    }

    public DailyLog mapFrom(DailyLogDTO dailyLogDTO) {
        return DailyLog.builder()
                .dailyLogId(dailyLogDTO.getId())
                .date(dailyLogDTO.getDate())
                .habit(habitMapper.mapFrom(dailyLogDTO.getHabitDTO()))
                .isCompleted(dailyLogDTO.isCompleted())
                .build();
    }
}
