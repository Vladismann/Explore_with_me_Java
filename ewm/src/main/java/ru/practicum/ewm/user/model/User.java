package ru.practicum.ewm.user.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    //признак, что пользователь разрешает подписку на себя
    @Column(nullable = false)
    private boolean subscribers;
    //список подписок пользователя не стал хранить в модели, так как непонятно публичны они или нет, получаем все данные из БД
}
