package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.DailyLog;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyLogRepo extends JpaRepository<DailyLog, Integer>{
    @Query("SELECT dl FROM DailyLog dl " +
            "WHERE dl.date = :date " +
            "AND dl.user = :user " +
            "ORDER BY dl.habit.createdAt ASC")
    List<DailyLog> findAllByDateAndUserOrderByHabit(@Param("date") LocalDate date,
                                                    @Param("user") User user);
    DailyLog getDailyLogByHabitAndDateAndUser(Habit habit, LocalDate date, User user);
    @Transactional
    void deleteByHabit_Id(int habitId);
    @Query("SELECT DISTINCT dl.date FROM DailyLog dl WHERE dl.user = :user")
    List<LocalDate> findUniqueDates(@Param("user") User user);
}
