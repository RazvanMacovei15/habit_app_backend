package maco.habit_backend.repositories;

import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    //query to retrieve
    @Query("SELECT new maco.habit_backend.dtos.UserHabitDTO(u.username, u.email, h.name, h.currentStreak) " +
            "FROM User u " +
            "JOIN u.userHabits h " +
            "WHERE u.id = :userId")
    List<UserHabitDTO> findHabitsByUserId(@Param("userId") Long userId);

}
