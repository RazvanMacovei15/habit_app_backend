package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.HabitMapper;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.security.services.JwtService;
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
    private JwtService jwtService;
    private final HabitService habitService;
    private final UserRepo userRepo;

    private User getUserFromToken(String authHeader){
        // Extract the token from the Authorization header
        String token = authHeader.substring(7);

        String currentUsersEmail = jwtService.extractUsername(token);

        // Retrieve the User entity based on userId from HabitDTO
        return userRepo.findByEmail(currentUsersEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with EMAIL: " + currentUsersEmail));
    }

    @PostMapping("/create")
    public HabitDTO createHabit(@RequestBody HabitDTO habitDTO,@RequestHeader("Authorization") String authHeader){
        User user =getUserFromToken(authHeader);

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

    @PatchMapping("/{habitId}/update")
    public HabitDTO updateHabitStatus(@PathVariable int habitId){
        Habit updatedHabit = habitService.updateHabitStatus(habitId);
        return habitMapper.mapToNewHabitDTO(updatedHabit);
    }
}
