package ru.practicum.ewm.user.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    private LocalDateTime created;
}
