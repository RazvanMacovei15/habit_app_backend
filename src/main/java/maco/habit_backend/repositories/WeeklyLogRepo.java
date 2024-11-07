package maco.habit_backend.repositories;

import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyLogRepo extends JpaRepository<WeeklyLog, Integer> {
    List<WeeklyLog> findAllByUser(User user);
}
