package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private int id;

    @ManyToOne
    @JoinColumn(name = "habitId")
    private Habit habit;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "is_completed")
    private boolean isCompleted;
}
