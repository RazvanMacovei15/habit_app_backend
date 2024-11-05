package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.DailyLogDTO;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.DailyLogMapper;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.security.services.JwtService;
import maco.habit_backend.services.DailyLogService;
import maco.habit_backend.services.HabitService;
import org.springframework.http.ResponseEntity;
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

    private JwtService jwtService;

    private final UserRepo userRepo;

    private User getUserFromToken(String authHeader){
        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        String currentUsersEmail = jwtService.extractUsername(token);

        // Retrieve the User entity based on userId from HabitDTO
        return userRepo.findByEmail(currentUsersEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with EMAIL: " + currentUsersEmail));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<DailyLogDTO>> getAll() {
        List<DailyLog> dailyLogs = dailyLogService.getAll();

        List<DailyLogDTO> dailyLogDTOS = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();

        return ResponseEntity.ok(dailyLogDTOS);
    }

    @PatchMapping("/{dailyLogId}/update")
    public ResponseEntity<DailyLogDTO> updateDailyLogStatus(@PathVariable int dailyLogId) {
        DailyLog dailyLogToUpdate = dailyLogService.getById(dailyLogId);
        dailyLogToUpdate.setCompleted(!dailyLogToUpdate.isCompleted());
        DailyLog updatedDailyLog = dailyLogService.updateStatus(dailyLogToUpdate);
        DailyLogDTO updatedDailyLogDTO = dailyLogMapper.mapTo(updatedDailyLog);
        return ResponseEntity.ok(updatedDailyLogDTO);
    }

    @DeleteMapping("/{dailyLogId}/delete")
    public ResponseEntity<Integer> deleteDailyLog(@PathVariable int dailyLogId) {
        return ResponseEntity.ok(dailyLogService.deleteById(dailyLogId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<DailyLogDTO>> createDailyLogsOnGivenDate(@PathVariable LocalDate date, @RequestHeader("Authorization") String authHeader) {
        User user = getUserFromToken(authHeader);
        List<DailyLog> dailyLogs = dailyLogService.createDailyLogsOnGivenDate(date, user);
        List<DailyLogDTO> dailyLogDTOS = dailyLogs
                .stream()
                .map(dailyLogMapper::mapTo)
                .toList();
        return ResponseEntity.ok(dailyLogDTOS);
    }


}
