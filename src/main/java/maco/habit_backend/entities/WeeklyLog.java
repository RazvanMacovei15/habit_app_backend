package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString(exclude = "habit")

@Entity
@Table(name = "weekly_logs")
public class WeeklyLog implements Log {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monthly_log_id_seq")
    @Column(name = "weekly_log_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "year_week")
    private int yearWeek;

    @Column(name = "week_start_day")
    private LocalDate weekStartDay;

    @Column(name = "week_end_day")
    private LocalDate weekEndDay ;

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
