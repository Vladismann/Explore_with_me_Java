package ru.practicum.ewm.event.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "locations", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float lat;
    private float lon;
}
