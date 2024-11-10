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
@ToString

@Entity
@Table(name = "daily_logs")
public class DailyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_log_id_seq")
    @Column(name = "daily_log_id")
    private int dailyLogId;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "is_previous_day_completed")
    private boolean isPreviousDayCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
