package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.DailyLogMapper;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.services.DailyLogService;
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

    @GetMapping("/getAll")
    public ResponseEntity<List<DailyLogDTO>> getAll(@AuthenticationPrincipal User user) {

        List<DailyLog> dailyLogs = dailyLogService.getAllForUser(user);
        List<DailyLogDTO> dailyLogDTOS = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();

        return ResponseEntity.ok(dailyLogDTOS);
    }

    @PatchMapping("/{dailyLogId}/update")
    public ResponseEntity<DailyLogDTO> updateDailyLogStatus(@PathVariable int dailyLogId, @AuthenticationPrincipal User user) {
        DailyLog dailyLogToUpdate = dailyLogService.getByIdAndUser(dailyLogId, user);

        if (dailyLogToUpdate == null) {
            throw new EntityNotFoundException("Daily log with id " + dailyLogId + " not found");
        }

        DailyLog updatedDailyLog = dailyLogService.updateSingleTargetStatus(dailyLogToUpdate);
        DailyLogDTO updatedDailyLogDTO = dailyLogMapper.mapTo(updatedDailyLog);
        return ResponseEntity.ok(updatedDailyLogDTO);
    }

    @DeleteMapping("/{dailyLogId}/delete")
    public void deleteDailyLog(@PathVariable int dailyLogId) {
         dailyLogService.deleteById(dailyLogId);
    }

    @PostMapping("/date/{date}")
    public ResponseEntity<List<DailyLogDTO>> createDailyLogsOnGivenDate(@PathVariable LocalDate date,  @AuthenticationPrincipal User user) {
        List<DailyLog> dailyLogs = dailyLogService.createDailyLogsOnGivenDate(date, user);
        List<DailyLogDTO> dailyLogDTOS = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(dailyLogDTOS);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<DailyLogDTO>> getDailyLogByDate(@PathVariable LocalDate date, @AuthenticationPrincipal User user) {
        List<DailyLog> dailyLogs = dailyLogService.getDailyLogByDateAndUser(date, user);
        List<DailyLogDTO> dailyLogDTOs = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(dailyLogDTOs);
    }

    @GetMapping("/date/{date}/habit/{habitId}")
    public ResponseEntity<DailyLogDTO> getDailyLogByHabitAndDate(@PathVariable LocalDate date, @PathVariable int habitId) {
        DailyLog dailyLog = dailyLogService.getDailyLogByHabitAndDate(habitId, date);
        DailyLogDTO dailyLogDTO = dailyLogMapper.mapTo(dailyLog);
        return ResponseEntity.ok(dailyLogDTO);
    }
}
