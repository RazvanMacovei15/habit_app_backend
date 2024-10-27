package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.HabitMapper;
import maco.habit_backend.repositories.UserRepo;
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
    private final UserRepo userRepo;

    @PostMapping("/create")
    public HabitDTO createHabit(@RequestBody HabitDTO habitDTO){

        // Retrieve the User entity based on userId from HabitDTO
        User user = userRepo.findById(habitDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + habitDTO.getUserId()));

        Habit habit = habitMapper.createNewHabit(habitDTO, user);
        Habit savedHabit = habitService.save(habit);
        System.out.println("Habit saved: " + savedHabit.toString());
        return habitMapper.mapToNewHabitDTO(savedHabit);
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
