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
public class DailyProgressEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_progress_id_seq")
    private int id;

    @Column(name = "date")
    private String date;

    @Column(name = "completed")
    private boolean completed;
}
