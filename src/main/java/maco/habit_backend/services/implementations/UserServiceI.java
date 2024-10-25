package maco.habit_backend.services.implementations;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.User;
import maco.habit_backend.repositories.UserRepo;
import maco.habit_backend.services.UserService;
import org.springframework.stereotype.Service;

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
}
