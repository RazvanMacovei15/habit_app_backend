package maco.habit_backend.repositories;

import maco.habit_backend.entities.DailyProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyProgressEntryRepo extends JpaRepository<DailyProgressEntry, Integer> {
}
