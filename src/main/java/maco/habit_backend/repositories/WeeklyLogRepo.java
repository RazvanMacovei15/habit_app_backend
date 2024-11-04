package maco.habit_backend.repositories;

import maco.habit_backend.entities.WeeklyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyLogRepo extends JpaRepository<WeeklyLog, Integer> {
}
