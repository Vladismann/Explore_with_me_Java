package ru.practicum.ewm.event.model;

import lombok.*;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    private String annotation;
    private String description;
    private int participantLimit;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Location location;
    @Column(nullable = false, name = "created_on")
    private LocalDateTime createdOn;
    @Column(nullable = false, name = "event_date")
    private LocalDateTime eventDate;
    @Column(nullable = false)
    private Boolean paid;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(nullable = false, name = "published_on")
    private LocalDateTime publishedOn;
}
