package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.WeeklyLogDTO;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import maco.habit_backend.mapper.WeeklyLogMapper;
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

    @GetMapping("/getAll")
        public ResponseEntity<List<WeeklyLogDTO>> getAll(@AuthenticationPrincipal User user) {
        List<WeeklyLog> weeklyLogs = weeklyLogService.findAll(user);
        List<WeeklyLogDTO> weeklyLogDTOS = weeklyLogs
                .stream()
                .map(weeklyLogMapper::mapTo)
                .toList();

        return ResponseEntity.ok(weeklyLogDTOS);
    }

    @PatchMapping("/{weeklyLogId}/addToCount")
    public ResponseEntity<WeeklyLogDTO> addToWeeklyLog(@PathVariable int weeklyLogId, @AuthenticationPrincipal User user) {

        try{
            WeeklyLog weeklyLogToUpdate = weeklyLogService.getById(weeklyLogId);

            if (weeklyLogToUpdate == null) {
                throw new EntityNotFoundException("Weekly log with id " + weeklyLogId + " not found");
            }

            if(weeklyLogToUpdate.getUser().getUserId() != user.getUserId()){
                throw new EntityNotFoundException("This weekly log does not belong to the user");
            }

            WeeklyLog updatedWeeklyLog = weeklyLogService.addUpdateStatus(weeklyLogToUpdate);

            WeeklyLogDTO updatedWeeklyLogDTO = weeklyLogMapper.mapTo(updatedWeeklyLog);

            return ResponseEntity.ok(updatedWeeklyLogDTO);

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{weeklyLogId}/decrementCount")
    public ResponseEntity<WeeklyLogDTO> decrementWeeklyLog(@PathVariable int weeklyLogId, @AuthenticationPrincipal User user) {

        try{
            WeeklyLog weeklyLogToUpdate = weeklyLogService.getById(weeklyLogId);

            if (weeklyLogToUpdate == null) {
                throw new EntityNotFoundException("Weekly log with id " + weeklyLogId + " not found");
            }

            if(weeklyLogToUpdate.getUser().getUserId() != user.getUserId()){
                throw new EntityNotFoundException("This weekly log does not belong to the user");
            }

            WeeklyLog updatedWeeklyLog = weeklyLogService.decrementUpdateStatus(weeklyLogToUpdate);

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


}