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
                .id(weeklyLog.getWeeklyLogId())
                .habitDTO(habitMapper.mapTo(weeklyLog.getHabit()))
                .yearWeek(weeklyLog.getYearWeek())
                .weekStartDay(weeklyLog.getWeekStartDay())
                .weekEndDay(weeklyLog.getWeekEndDay())
                .currentCount(weeklyLog.getCurrentCount())
                .isCompleted(weeklyLog.isCompleted())
                .isPreviousWeekCompleted(weeklyLog.isPreviousCompleted())
                .build();
    }

    public WeeklyLog mapFrom(WeeklyLogDTO weeklyLogDTO) {
        return WeeklyLog.builder()
                .weeklyLogId(weeklyLogDTO.getId())
                .habit(habitMapper.mapFrom(weeklyLogDTO.getHabitDTO()))
                .yearWeek(weeklyLogDTO.getYearWeek())
                .weekStartDay(weeklyLogDTO.getWeekStartDay())
                .weekEndDay(weeklyLogDTO.getWeekEndDay())
                .currentCount(weeklyLogDTO.getCurrentCount())
                .isCompleted(weeklyLogDTO.isCompleted())
                .previousCompleted(weeklyLogDTO.isPreviousWeekCompleted())
                .build();
    }
}
