package maco.habit_backend.repositories;

import maco.habit_backend.entities.MonthlyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyLogRepo extends JpaRepository<MonthlyLog, Integer> {
}
