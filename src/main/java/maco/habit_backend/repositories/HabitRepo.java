package maco.habit_backend.repositories;

import maco.habit_backend.entities.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepo extends JpaRepository<Habit, Integer> {
}