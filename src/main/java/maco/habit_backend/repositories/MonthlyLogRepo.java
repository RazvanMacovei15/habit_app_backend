package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.MonthlyLog;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonthlyLogRepo extends JpaRepository<MonthlyLog, Integer> {
    List<MonthlyLog> findAllByUser(User user);
    List<MonthlyLog> findAllByMonthAndUserOrderByHabit(int month, User user);
    @Transactional
    void deleteByHabit_Id(int habitId);
}
