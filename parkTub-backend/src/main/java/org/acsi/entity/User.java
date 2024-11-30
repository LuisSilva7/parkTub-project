package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String email;

    public int points;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ParkingSession> parkingSessions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="user_bonus",
            joinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="bonus_id", referencedColumnName="id")
    )
    public List<Bonus> bonus = new ArrayList<>();

    public static User findByName(String name) {
        return find("name", name).firstResult();
    }
}
