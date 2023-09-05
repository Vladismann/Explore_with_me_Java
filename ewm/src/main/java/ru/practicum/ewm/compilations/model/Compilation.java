package ru.practicum.ewm.compilations.model;

import lombok.*;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "compilations")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean pinned;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}
