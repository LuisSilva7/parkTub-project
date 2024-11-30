package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bonus")
public class Bonus extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String description;
    public Double discountPercentage;
    public int pointsRequired;

    @ManyToMany(mappedBy="bonus")
    public List<User> users = new ArrayList<>();

    public static Bonus findByPointsRequired(int points) {
        return find("pointsRequired", points).firstResult();
    }
}
