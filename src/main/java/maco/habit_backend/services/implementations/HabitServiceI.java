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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HabitServiceI implements HabitService {

    private final HabitRepo habitRepo;

    private final LogStrategyFactory logStrategyFactory;

    @Override
    public Habit save(Habit habit, User user) {
        Habit savedHabit = habitRepo.save(habit);

        LogStrategy logStrategy = logStrategyFactory.getStrategy(habit.getOccurrence());
        logStrategy.createNewHabitLog(savedHabit, user);

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

    @Override
    public Habit updateHabitFromTrueToFalse(int habitId, boolean shouldDecrementTotalCount) {
        Habit habitToUpdate = habitRepo.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with ID: " + habitId));

        habitToUpdate.setUpdatedAt(LocalDateTime.now());

        if (habitToUpdate.getCurrentStreak() == habitToUpdate.getBestStreak()
                && habitToUpdate.getBestStreak() != 0
                && habitToUpdate.getDayOfBestStreak().equals(LocalDate.now())
                && shouldDecrementTotalCount){
            habitToUpdate.setBestStreak(habitToUpdate.getBestStreak() - 1);
        }
        if (shouldDecrementTotalCount && habitToUpdate.getCurrentStreak() > 0) {
            habitToUpdate.setCurrentStreak(habitToUpdate.getCurrentStreak() - 1);
        }

        // Only decrement totalCount if shouldDecrementTotalCount is true
        if (shouldDecrementTotalCount && habitToUpdate.getTotalCount() > 0) {
            habitToUpdate.setTotalCount(habitToUpdate.getTotalCount() - 1);
        }

        return habitRepo.save(habitToUpdate);
    }

    @Override
    public Habit updateHabitFromFalseToTrue(int habitId, boolean previousLogCompleted, int previousStreak) {
        Habit habitToUpdate = habitRepo.findById(habitId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit not found with ID: " + habitId));

        int newStreak = previousLogCompleted ? previousStreak + 1 : 1;
        habitToUpdate.setCurrentStreak(newStreak);

        // Update best streak if current streak exceeds it
        if (newStreak > habitToUpdate.getBestStreak()) {
            habitToUpdate.setBestStreak(newStreak);
        }

        // Increment total count and update last modified timestamp
        habitToUpdate.setTotalCount(habitToUpdate.getTotalCount() + 1);
        habitToUpdate.setUpdatedAt(LocalDateTime.now());

        return habitRepo.save(habitToUpdate);
    }


    @Override
    public void deleteAllHabitsForUser(User user) {

    }


}
