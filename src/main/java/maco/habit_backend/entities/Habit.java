package maco.habit_backend.entities;

import jakarta.persistence.*;
import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;

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
}
