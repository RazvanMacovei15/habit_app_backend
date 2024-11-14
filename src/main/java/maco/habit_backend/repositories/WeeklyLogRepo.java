package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.entities.WeeklyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeeklyLogRepo extends JpaRepository<WeeklyLog, Integer> {
    List<WeeklyLog> findAllByUser(User user);
    List<WeeklyLog> findAllByYearWeekAndUserOrderByHabit(int yearWeek, User user);
    @Query("SELECT w FROM WeeklyLog w WHERE w.habit = ?1 AND w.yearWeek = ?2 AND w.user = ?3")
    WeeklyLog getWeeklyLogByHabitAndYearWeekAndUser(Habit habit, int yearWeek, User user);
    @Transactional
    void deleteByHabit_Id(int habitId);
}