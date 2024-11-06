package maco.habit_backend.services.implementations;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.HabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.exceptions.ResourceNotFoundException;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.services.HabitService;
import maco.habit_backend.strategies.habitlogs.LogStrategy;
import maco.habit_backend.strategies.habitlogs.factory.LogStrategyFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class HabitServiceI implements HabitService {

    private final HabitRepo habitRepo;

    private final LogStrategyFactory logStrategyFactory;

    @Override
    public Habit save(Habit habit) {
        Habit savedHabit = habitRepo.save(habit);

        LogStrategy logStrategy = logStrategyFactory.getStrategy(habit.getOccurrence());
        logStrategy.createLog(savedHabit);

        return savedHabit;
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
    @Override
    public void deleteAllForUser(User user) {
        System.out.println("Deleting all habits");
        habitRepo.deleteAllHabitsForUser(user);
    }

    @Override
    public List<Habit> getAllHabitsByUserId(int userId) {
        return habitRepo.findAllByUserUserId(userId);
    }

    @Override
    public Habit updateHabitDetails(int habitId, HabitDTO habitDTO) {
        Habit habitToUpdate = habitRepo.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with ID: " + habitId));

        habitToUpdate.setName(habitDTO.getHabitName());
        habitToUpdate.setDescription(habitDTO.getDescription());
        habitToUpdate.setOccurrence(habitDTO.getOccurrence());
        habitToUpdate.setType(habitDTO.getType());

        return habitRepo.save(habitToUpdate);
    }

    public List<Habit> getAllHabitsByOccurrenceAndUser(Occurrence occurrence, User user) {
        return habitRepo.findAllByOccurrenceAndUser(occurrence, user);
    }
}