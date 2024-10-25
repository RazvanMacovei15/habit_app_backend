package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.mapper.HabitMapper;
import maco.habit_backend.services.HabitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/habits")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HabitController {
    private HabitMapper habitMapper;

    private final HabitService habitService;

    @PostMapping("/create")
    public HabitDTO createHabit(@RequestBody HabitDTO habitDTO){
        Habit habit = habitMapper.mapFrom(habitDTO);
        Habit savedHabit = habitService.save(habit);
        return habitMapper.mapTo(savedHabit);
    }

    @GetMapping("/all")
    public List<HabitDTO> getAll(){
        return habitService
                .getAll()
                .stream()
                .map(habitMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HabitDTO getHabitById(@PathVariable int id){
        Habit habit = habitService.getById(id);
        return habitMapper.mapTo(habit);
    }
}
