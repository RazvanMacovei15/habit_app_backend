package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.WeeklyLogDTO;
import maco.habit_backend.entities.WeeklyLog;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeeklyLogMapper {
    private final HabitMapper habitMapper;
    private final UserMapper userMapper;

    public WeeklyLogDTO mapTo(WeeklyLog weeklyLog) {
        return WeeklyLogDTO.builder()
                .weeklyLogDTOId(weeklyLog.getWeeklyLogId())
                .build();
    }

    public WeeklyLog mapFrom(WeeklyLogDTO weeklyLogDTO) {
        return WeeklyLog.builder()
                .weeklyLogId(weeklyLogDTO.getWeeklyLogDTOId())
                .build();
    }
}
