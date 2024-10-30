package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.exceptions.ResourceNotFoundException;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.services.HabitService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class HabitServiceI implements HabitService {

    private final HabitRepo habitRepo;

    @Override
    public Habit save(Habit habit) {
        return habitRepo.save(habit);
    }

    @Override
    public List<Habit> getAll() {
        return habitRepo.findAll();
    }

    @Override
    public Habit getById(int id) {
        return habitRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int id) {
        habitRepo.deleteById(id);
    }

    @Override
    public Habit updateHabitStatus(int habitId) {
        Habit habitToUpdate = habitRepo.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with ID: " + habitId));

        habitToUpdate.setCompleted(!habitToUpdate.isCompleted());
        habitToUpdate.setUpdatedAt(LocalDateTime.now());

        return habitRepo.save(habitToUpdate);

    }


}
