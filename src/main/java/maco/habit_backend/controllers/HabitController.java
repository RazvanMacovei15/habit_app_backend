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
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HabitDTO> createHabit(@RequestBody HabitDTO habitDTO,@RequestHeader("Authorization") String authHeader){
        User user = getUserFromToken(authHeader);

        Habit habit = habitMapper.createNewHabit(habitDTO, user);
        Habit savedHabit = habitService.save(habit);
        System.out.println("Habit saved: " + savedHabit.toString());
        HabitDTO savedHabitDTO = habitMapper.mapToNewHabitDTO(savedHabit);
        return ResponseEntity.ok(savedHabitDTO);
    }

    @PatchMapping("/{habitId}/update")
    public ResponseEntity<HabitDTO> updateHabitStatus(@PathVariable int habitId){
        Habit updatedHabit = habitService.updateHabitStatus(habitId);
        HabitDTO updatedHabitDTO = habitMapper.mapToNewHabitDTO(updatedHabit);
        return ResponseEntity.ok(updatedHabitDTO);
    }

    @DeleteMapping("/{habitId}/delete")
    public ResponseEntity<String> deleteHabit(@PathVariable int habitId){
        habitService.deleteById(habitId);
        return ResponseEntity.ok("Habit deleted with ID: " + habitId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HabitDTO>> getAll(){
        List<HabitDTO> habits =  habitService
                .getAll()
                .stream()
                .map(habitMapper::mapTo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{id}")
    public HabitDTO getHabitById(@PathVariable int id){
        Habit habit = habitService.getById(id);
        return habitMapper.mapTo(habit);
    }


}
