package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.UserMapper;
import maco.habit_backend.services.UserService;
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


    @GetMapping("/me")
    public ResponseEntity<UserDTO> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();
        UserDTO currentUserDTO = userMapper.mapTo(currentUser);

        return ResponseEntity.ok(currentUserDTO);
    }





    @GetMapping("/{userId}/habits")
    public List<UserHabitDTO> getHabitsByUserId(@PathVariable Long userId){
        return userService
                .findHabitsByUserId(userId)
                .stream()
                .map(
                        userHabitDTO -> new UserHabitDTO(
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
