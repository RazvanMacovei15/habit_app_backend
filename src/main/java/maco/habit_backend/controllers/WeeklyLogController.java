package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.WeeklyLogDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.mapper.WeeklyLogMapper;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.LogService;
import maco.habit_backend.services.WeeklyLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/weekly-logs")
public class WeeklyLogController {

    private final WeeklyLogService weeklyLogService;
    private final WeeklyLogMapper weeklyLogMapper;
    private final HabitService habitService;
    private final LogService logService;

    @PatchMapping("/{weeklyLogId}/addUpdate")
    public ResponseEntity<WeeklyLogDTO> addToWeeklyLog(@PathVariable int weeklyLogId, @AuthenticationPrincipal User user) {
        try{
            WeeklyLog weeklyLogToUpdate = weeklyLogService.getById(weeklyLogId);
            if (weeklyLogToUpdate == null) {
                throw new EntityNotFoundException("Weekly log with id " + weeklyLogId + " not found");
            }
            if(weeklyLogToUpdate.getUser().getUserId() != user.getUserId()){
                throw new EntityNotFoundException("This weekly log does not belong to the user");
            }
            WeeklyLog updatedWeeklyLog = (WeeklyLog) logService.addUpdate(weeklyLogToUpdate);
            WeeklyLogDTO updatedWeeklyLogDTO = weeklyLogMapper.mapTo(updatedWeeklyLog);
            return ResponseEntity.ok(updatedWeeklyLogDTO);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{weeklyLogId}/decrementUpdate")
    public ResponseEntity<WeeklyLogDTO> decrementWeeklyLog(@PathVariable int weeklyLogId, @AuthenticationPrincipal User user) {
        try{
            WeeklyLog weeklyLogToUpdate = weeklyLogService.getById(weeklyLogId);
            if (weeklyLogToUpdate == null) {
                throw new EntityNotFoundException("Weekly log with id " + weeklyLogId + " not found");
            }
            if(weeklyLogToUpdate.getUser().getUserId() != user.getUserId()){
                throw new EntityNotFoundException("This weekly log does not belong to the user");
            }
            WeeklyLog updatedWeeklyLog = (WeeklyLog) logService.decrementUpdate(weeklyLogToUpdate);
            WeeklyLogDTO updatedWeeklyLogDTO = weeklyLogMapper.mapTo(updatedWeeklyLog);
            return ResponseEntity.ok(updatedWeeklyLogDTO);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createWeeklyLogsOnGivenWeek/{yearWeek}")
    public ResponseEntity<List<WeeklyLogDTO>> createWeeklyLogsOnGivenWeek(@PathVariable int yearWeek, @AuthenticationPrincipal User user) {
        List<WeeklyLog> weeklyLogs = weeklyLogService.createWeeklyLogsOnGivenWeek(yearWeek, user);
        List<WeeklyLogDTO> weeklyLogDTOS = weeklyLogs
                .stream()
                .map(weeklyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(weeklyLogDTOS);
    }

    @GetMapping("/getWeeklyLogsByYearWeek/{yearWeek}")
    public ResponseEntity<List<WeeklyLogDTO>> getWeeklyLogsByYearWeek(@PathVariable int yearWeek, @AuthenticationPrincipal User user) {
        List<WeeklyLog> weeklyLogs = weeklyLogService.findAllByYearWeekAndUser(yearWeek, user);
        List<WeeklyLogDTO> weeklyLogDTOS = weeklyLogs
                .stream()
                .map(weeklyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(weeklyLogDTOS);
    }

    @GetMapping("/yearWeek/{yearWeek}/habit/{habitId}")
    public ResponseEntity<WeeklyLogDTO> getWeeklyLogByHabitAndYearWeek(@PathVariable int yearWeek, @PathVariable int habitId, @AuthenticationPrincipal User user) {
        Habit habit = habitService.getById(habitId).orElseThrow(() -> new EntityNotFoundException("Habit with id " + habitId + " not found"));
        WeeklyLog weeklyLog = weeklyLogService.getWeeklyLogByHabitAndYearWeekAndUser(habit, yearWeek, user);
        WeeklyLogDTO weeklyLogDTO = weeklyLogMapper.mapTo(weeklyLog);
        return ResponseEntity.ok(weeklyLogDTO);
    }


}