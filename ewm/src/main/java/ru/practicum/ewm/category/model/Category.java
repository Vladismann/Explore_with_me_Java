package ru.practicum.ewm.category.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
}
