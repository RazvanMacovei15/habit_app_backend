package maco.habit_backend.services.implementations;

import jakarta.transaction.Transactional;
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
    public Optional<Habit> getById(int id) {
        //return Habit optional
        return habitRepo.findById(id);
    }
    @Transactional
    @Override
    public void deleteById(int id) {
        System.out.println("Deleting habit with ID: " + id);
        habitRepo.deleteHabit(id);
    }

    @Override
    public Habit updateHabitStatus(int habitId) {
        Habit habitToUpdate = habitRepo.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with ID: " + habitId));

        habitToUpdate.setCompleted(!habitToUpdate.isCompleted());
        habitToUpdate.setUpdatedAt(LocalDateTime.now());

        return habitRepo.save(habitToUpdate);

    }

    public void deleteAll() {
        System.out.println("Deleting all habits");
        habitRepo.deleteAllHabits();
    }

}
