package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString(exclude = "habit")

@Entity
@Table(name = "daily_logs")
public class DailyLog implements Log{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_log_id_seq")
    @Column(name = "daily_log_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "current_count")
    private int currentCount;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "previous_completed")
    private boolean previousCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
