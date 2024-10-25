package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.UserHabit;
import maco.habit_backend.mapper.UserHabitMapper;
import maco.habit_backend.services.UserHabitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/userHabits")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserHabitController {

    private final UserHabitService userHabitService;

    private final UserHabitMapper userHabitMapper;

    @PostMapping("/create")
    public UserHabitDTO createUserHabit(@RequestBody UserHabitDTO userHabitDTO){
        UserHabit userHabit = userHabitMapper.createFreshUserHabit(userHabitDTO);
        UserHabit savedUserHabit = userHabitService.save(userHabit);
        return userHabitMapper.mapTo(savedUserHabit);
    }

    @GetMapping("/all")
    public List<UserHabitDTO> getAll(){
        return userHabitService
                .getAll()
                .stream()
                .map(userHabitMapper::mapTo)
                .collect(Collectors.toList());
    }
}
