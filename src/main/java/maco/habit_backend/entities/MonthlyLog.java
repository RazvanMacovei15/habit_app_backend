package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString

@Entity
@Table(name = "monthly_logs")
public class MonthlyLog implements Log{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthly_log_id_seq")
    @Column(name = "monthly_log_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "year")
    private int year;

    @Column(name = "month")
    private int month;

    @Column(name = "current_count")
    private int currentCount;

    @Column(name = "target_count")
    private int targetCount;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "previous_completed")
    private boolean previousCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
