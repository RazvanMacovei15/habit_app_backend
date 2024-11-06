package maco.habit_backend.repositories;

import jakarta.transaction.Transactional;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import maco.habit_backend.enums.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepo extends JpaRepository<Habit, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Habit h WHERE h.id = :habitId")
    void deleteHabit(@Param("habitId") int habitId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Habit h WHERE h.user = :user")
    void deleteAllHabitsForUser(User user);

    List<Habit> findAllByUserUserId(int userId);

    List<Habit> findAllByOccurrenceAndUser(Occurrence occurrence, User user);
    List<Habit> findAllByOccurrence(Occurrence occurrence);
}
