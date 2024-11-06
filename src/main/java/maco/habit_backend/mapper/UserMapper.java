package maco.habit_backend.mapper;

import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO mapTo(User user) {
        return UserDTO.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User mapFrom(UserDTO userDto) {
        return User.builder()
                .userId(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }
}
