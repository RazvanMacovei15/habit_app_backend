package maco.habit_backend.mapper;

import maco.habit_backend.dtos.UserDTO;
import maco.habit_backend.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO mapTo(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User mapFrom(UserDTO hotelDto) {
        return User.builder()
                .id(hotelDto.getId())
                .username(hotelDto.getUsername())
                .email(hotelDto.getEmail())
                .build();
    }
}
