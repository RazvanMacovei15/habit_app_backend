package maco.habit_backend.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.mapper.HabitMapper;
import maco.habit_backend.mapper.UserMapper;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.security.services.JwtService;
import maco.habit_backend.services.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/habits")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class HabitController {

    private HabitMapper habitMapper;
    private final HabitService habitService;

    @PostMapping("/create")
    public ResponseEntity<HabitDTO> createHabit(@RequestBody HabitDTO habitDTO, @AuthenticationPrincipal User user){
        System.out.println("User in create habit controller: " + user.getEmail());

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
    public ResponseEntity<String> deleteHabit(@PathVariable("habitId") int habitId){
        Optional<Habit> habitOptional = habitService.getById(habitId);
        System.out.println("Habit optional: " + habitOptional.toString());
        if(habitOptional.isPresent()){
            habitService.deleteById(habitId);
            return ResponseEntity.ok("Habit deleted with ID: " + habitId);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<HabitDTO>> getAll( @AuthenticationPrincipal User user){

        List<HabitDTO> habits =  habitService
                .getAllHabitsByUserId(user.getUserId())
                .stream()
                .map(habitMapper::mapTo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> getHabitById(@PathVariable int id){
        Optional<Habit> habitOptional = habitService.getById(id);
        return habitOptional.map(habit -> ResponseEntity.ok(habitMapper.mapTo(habit))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll( @AuthenticationPrincipal User user){

        habitService.deleteAllForUser(user);
        return ResponseEntity.ok("All habits deleted");
    }

    @PatchMapping("/{habitId}/updateDetails")
    public ResponseEntity<HabitDTO> updateHabitDetails(@PathVariable int habitId, @RequestBody HabitDTO habitDTO){
        Habit updatedHabit = habitService.updateHabitDetails(habitId, habitDTO);
        HabitDTO updatedHabitDTO = habitMapper.mapToNewHabitDTO(updatedHabit);
        return ResponseEntity.ok(updatedHabitDTO);
    }

    @GetMapping("/allByOccurrence/{occurrence}")
    public ResponseEntity<List<HabitDTO>> getAllByOccurrence(@PathVariable Occurrence occurrence, @AuthenticationPrincipal User user){

        List<HabitDTO> habits =  habitService
                .getAllHabitsByOccurrenceAndUser(occurrence, user)
                .stream()
                .map(habitMapper::mapTo)
                .collect(Collectors.toList());

        return ResponseEntity.ok(habits);
    }


}
