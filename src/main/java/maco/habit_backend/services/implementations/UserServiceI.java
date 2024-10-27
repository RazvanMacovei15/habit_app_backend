package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceI implements UserService {
    private final UserRepo userRepo;

    @Override
    public User save(User habit) {
        return userRepo.save(habit);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public List<UserHabitDTO> findHabitsByUserId(Long userId) {
        return userRepo.findHabitsByUserId(userId);
    }


}
