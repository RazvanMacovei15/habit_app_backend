package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyLogRepo extends JpaRepository<DailyLog, Integer> {

    int deleteById(int id);

    List<DailyLog> findAllByDate(LocalDate date);


}
