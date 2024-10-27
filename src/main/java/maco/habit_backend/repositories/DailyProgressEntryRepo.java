package maco.habit_backend.repositories;

import maco.habit_backend.entities.DailyCheckEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyProgressEntryRepo extends JpaRepository<DailyCheckEntry, Integer> {
}
