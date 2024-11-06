package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.WeeklyLogDTO;
import maco.habit_backend.services.WeeklyLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/weekly-logs")
public class WeeklyLogController {

    private final WeeklyLogService weeklyLogService;

    @PostMapping("/create")
    public ResponseEntity<WeeklyLogDTO> createWeeklyLog(WeeklyLogDTO weeklyLogDTO){
        weeklyLogService.createNewWeeklyLog(weeklyLog);
    }

}
