package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.DailyLogMapper;
import maco.habit_backend.services.DailyLogService;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/daily-logs")
public class DailyLogController {
    private final DailyLogService dailyLogService;
    private final DailyLogMapper dailyLogMapper;
    private final HabitService habitService;
    private final LogService logService;

    @PatchMapping("/{dailyLogId}/addUpdate")
    public ResponseEntity<DailyLogDTO> addUpdateDailyLog(@PathVariable int dailyLogId, @AuthenticationPrincipal User user) {
        try {
            DailyLog dailyLogToUpdate = dailyLogService.getById(dailyLogId);
            // Check if daily log exists
            if (dailyLogToUpdate == null) {
                throw new EntityNotFoundException("Daily log with id " + dailyLogId + " not found");
            }
            // Check if the daily log belongs to the user
            if (dailyLogToUpdate.getUser().getUserId() != user.getUserId()) {
                throw new EntityNotFoundException("This daily log does not belong to the user");
            }
            DailyLog updatedDailyLog = (DailyLog) logService.addUpdate(dailyLogToUpdate);
            // Map to DTO
            DailyLogDTO updatedDailyLogDTO = dailyLogMapper.mapTo(updatedDailyLog);
            return ResponseEntity.ok(updatedDailyLogDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{dailyLogId}/decrementUpdate")
    public ResponseEntity<DailyLogDTO> decrementDailyLog(@PathVariable int dailyLogId, @AuthenticationPrincipal User user) {
        try {
            DailyLog dailyLogToUpdate = dailyLogService.getById(dailyLogId);
            if (dailyLogToUpdate == null) {
                throw new EntityNotFoundException("Daily log with id " + dailyLogId + " not found");
            }
            if (dailyLogToUpdate.getUser().getUserId() != user.getUserId()) {
                throw new EntityNotFoundException("This daily log does not belong to the user");
            }
            DailyLog updatedDailyLog = (DailyLog) logService.decrementUpdate(dailyLogToUpdate);
            DailyLogDTO updatedDailyLogDTO = dailyLogMapper.mapTo(updatedDailyLog);
            return ResponseEntity.ok(updatedDailyLogDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/date/{date}")
    public ResponseEntity<List<DailyLogDTO>> createDailyLogsOnGivenDate(@PathVariable LocalDate date, @AuthenticationPrincipal User user) {
        List<DailyLog> dailyLogs = dailyLogService.createDailyLogsOnGivenDate(date, user);
        List<DailyLogDTO> dailyLogDTOS = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(dailyLogDTOS);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<DailyLogDTO>> getDailyLogsByDate(@PathVariable LocalDate date, @AuthenticationPrincipal User user) {
        List<DailyLog> dailyLogs = dailyLogService.findAllByDateAndUser(date, user);
        List<DailyLogDTO> dailyLogDTOs = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(dailyLogDTOs);
    }

    @GetMapping("/date/{date}/habit/{habitId}")
    public ResponseEntity<DailyLogDTO> getDailyLogByHabitAndDate(@PathVariable LocalDate date, @PathVariable int habitId, @AuthenticationPrincipal User user) {
        Habit habit = habitService.getById(habitId).orElseThrow(() -> new EntityNotFoundException("Habit with id " + habitId + " not found"));
        DailyLog dailyLog = dailyLogService.getDailyLogByHabitAndDateAndUser(habit, date, user);
        DailyLogDTO dailyLogDTO = dailyLogMapper.mapTo(dailyLog);
        return ResponseEntity.ok(dailyLogDTO);
    }
}
