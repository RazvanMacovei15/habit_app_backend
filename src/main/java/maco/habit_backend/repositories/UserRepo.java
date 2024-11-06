package maco.habit_backend.repositories;

import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.Habit;
import maco.habit_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    //query to retrieve
    @Query("SELECT new maco.habit_backend.dtos.UserHabitDTO(u.username, u.userId,h.habitId, h.name, h.currentStreak) " +
            "FROM User u " +
            "JOIN u.userHabits h " +
            "WHERE u.userId = :userId")
    List<UserHabitDTO> findHabitsByUser(User user);

    Optional<User> findByEmail(String email);


}
