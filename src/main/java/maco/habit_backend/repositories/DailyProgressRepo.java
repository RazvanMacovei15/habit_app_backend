package maco.habit_backend.repositories;

import maco.habit_backend.entities.DailyProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyProgressRepo extends JpaRepository<DailyProgress, Integer> {
}
