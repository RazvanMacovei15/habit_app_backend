package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.WeeklyLogDTO;
import maco.habit_backend.entities.WeeklyLog;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeeklyLogMapper {
    private final HabitMapper habitMapper;

    public WeeklyLogDTO mapTo(WeeklyLog weeklyLog) {
        return WeeklyLogDTO.builder()
                .weeklyLogDTOId(weeklyLog.getWeeklyLogId())
                .habitDTO(habitMapper.mapTo(weeklyLog.getHabit()))
                .yearWeek(weeklyLog.getYearWeek())
                .weekStartDay(weeklyLog.getWeekStartDay())
                .weekEndDay(weeklyLog.getWeekEndDay())
                .currentCount(weeklyLog.getCurrentCount())
                .targetCount(weeklyLog.getTargetCount())
                .isCompleted(weeklyLog.isCompleted())
                .build();
    }

    public WeeklyLog mapFrom(WeeklyLogDTO weeklyLogDTO) {
        return WeeklyLog.builder()
                .weeklyLogId(weeklyLogDTO.getWeeklyLogDTOId())
                .habit(habitMapper.mapFrom(weeklyLogDTO.getHabitDTO()))
                .yearWeek(weeklyLogDTO.getYearWeek())
                .weekStartDay(weeklyLogDTO.getWeekStartDay())
                .weekEndDay(weeklyLogDTO.getWeekEndDay())
                .currentCount(weeklyLogDTO.getCurrentCount())
                .targetCount(weeklyLogDTO.getTargetCount())
                .isCompleted(weeklyLogDTO.isCompleted())
                .build();
    }
}
