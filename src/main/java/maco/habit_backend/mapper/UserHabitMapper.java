package maco.habit_backend.mapper;

import lombok.RequiredArgsConstructor;
import maco.habit_backend.dtos.UserHabitDTO;
import maco.habit_backend.entities.UserHabit;
import maco.habit_backend.repositories.HabitRepo;
import maco.habit_backend.repositories.UserRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserHabitMapper {
    private final UserRepo userRepository;
    private final HabitRepo habitRepository;
    public UserHabit createFreshUserHabit(UserHabitDTO userHabitDTO) {
        LocalDateTime now = LocalDateTime.now();
        int userId = userHabitDTO.getUserId();
        int habitId = userHabitDTO.getHabitId();

        return UserHabit.builder()
                .id(userHabitDTO.getId())
                .user(userRepository.findById(userId).get())
                .habit(habitRepository.findById(habitId).get())
                .currentStreak(0)
                .bestStreak(0)
                .totalCount(0)
                .dateCreated(now)
                .lastUpdated(now)
                .build();
    }

    // Entity to DTO
    public UserHabitDTO mapTo(UserHabit userHabit) {

        return UserHabitDTO.builder()
                .id(userHabit.getId())
                .userId(userHabit.getUser().getId())
                .habitId(userHabit.getHabit().getId())
                .currentStreak(userHabit.getCurrentStreak())
                .bestStreak(userHabit.getBestStreak())
                .totalCount(userHabit.getTotalCount())
                .dateCreated(userHabit.getDateCreated())
                .lastUpdated(userHabit.getLastUpdated())
                .build();
    }

    // DTO to Entity
    public UserHabit mapFrom(UserHabitDTO dto) {
        int userId = dto.getUserId();
        int habitId = dto.getHabitId();
        return UserHabit.builder()
                .id(dto.getId())
                .user(userRepository.findById(userId).get())
                .habit(habitRepository.findById(habitId).get())
                .currentStreak(dto.getCurrentStreak())
                .bestStreak(dto.getBestStreak())
                .totalCount(dto.getTotalCount())
                .dateCreated(dto.getDateCreated())
                .lastUpdated(dto.getLastUpdated())
                .build();
    }
}
