package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HabitRepo extends JpaRepository<Habit, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Habit h WHERE h.id = :habitId")
    void deleteHabit(@Param("habitId") int habitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Habit")
    void deleteAllHabits();

    List<Habit> findAllByUserId(int userId);

}
