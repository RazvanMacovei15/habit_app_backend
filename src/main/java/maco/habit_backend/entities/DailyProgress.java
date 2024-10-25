package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

@Entity
@Table(name = "daily_progress")
public class DailyProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_progress_id_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_habit_id")
    private UserHabit userHabit;

    @Column(name = "date")
    private String date;

    @Column(name = "completed")
    private boolean completed;
}
