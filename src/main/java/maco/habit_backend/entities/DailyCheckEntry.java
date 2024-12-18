package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

@Entity
@Table(name = "daily_check_entries")
public class DailyCheckEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_progress_id_seq")
    private int id;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "is_completed")
    private boolean isCompleted;
}
