package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.UserHabit;
import maco.habit_backend.repositories.UserHabitRepo;
import maco.habit_backend.services.UserHabitService;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class UserHabitServiceI implements UserHabitService {
    private final UserHabitRepo userHabitRepo;

    @Override
    public UserHabit save(UserHabit userHabit) {
        return userHabitRepo.save(userHabit);
    }

    @Override
    public List<UserHabit> getAll() {
        return userHabitRepo.findAll();
    }
}
