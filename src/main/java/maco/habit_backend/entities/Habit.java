package maco.habit_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "habit_id_seq")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "occurrence")
    private Occurrence occurrence;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    private Set<UserHabit> userHabits;
}
