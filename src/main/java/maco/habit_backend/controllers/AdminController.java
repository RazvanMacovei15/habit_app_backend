package maco.habit_backend.controllers;

import lombok.AllArgsConstructor;
import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.User;
import maco.habit_backend.mapper.UserMapper;
import maco.habit_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    private UserMapper userMapper;
    private final UserService userService;
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> allUsers() {
        List <UserDTO> users = userService.getAll()
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/createUser")
    public UserDTO createUser(@RequestBody UserDTO userDTO){
        User user = userMapper.mapFrom(userDTO);
        User savedUser = userService.save(user);
        return userMapper.mapTo(savedUser);
    }
}
