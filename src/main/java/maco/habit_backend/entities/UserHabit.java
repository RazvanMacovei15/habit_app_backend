package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "user_habits")
public class UserHabit {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_habit_id_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "current_streak")
    private int currentStreak;

    @Column(name = "best_streak")
    private int bestStreak;

    @Column(name = "total_count")
    private int totalCount;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
