package maco.habit_backend.services;

import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;

import java.util.List;

public interface UserService {
    User save(User habit);
    List<User> getAll();
    List<UserHabitDTO> findHabitsByUserId(Long userId);
    List<User> allUsers();
}
