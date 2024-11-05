package maco.habit_backend.repositories;

import maco.habit_backend.entities.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyLogRepo extends JpaRepository<DailyLog, Integer> {

    int deleteById(int id);

    List<DailyLog> findAllByDate(LocalDate date);
}
