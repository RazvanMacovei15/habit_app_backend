package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.HabitMapper;
import maco.habit_backend.mapper.UserMapper;
import maco.habit_backend.models.UserHabit;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private UserMapper userMapper;

    private final UserService userService;
    private final HabitService habitService;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Log the authentication details
        System.out.println("Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        System.out.println("Principal: " + principal);

        if (principal instanceof User) {
            User currentUser = (User) principal;
            System.out.println("Current User: " + currentUser);
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authenticated user not found");
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        User user = userMapper.mapFrom(userDTO);
        User savedUser = userService.save(user);
        return userMapper.mapTo(savedUser);
    }

    @GetMapping("/all")
    public List<UserDTO> getAll(){
        return userService
                .getAll()
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/habits")
    public List<UserHabit> getHabitsByUserId(@PathVariable Long userId){
        return userService
                .findHabitsByUserId(userId)
                .stream()
                .map(
                        userHabitDTO -> new UserHabit(
                                userHabitDTO.getUsername(),
                                userHabitDTO.getUserId(),
                                userHabitDTO.getHabitId(),
                                userHabitDTO.getHabitName(),
                                userHabitDTO.isHabitCompleted(),
                                userHabitDTO.getCurrentStreak()
                        )
                )
                .collect(Collectors.toList());
    }
}
