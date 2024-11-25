package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "bonus")
public class Bonus extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
    private Double discountPercentage;
    private int pointsRequired;

    private Boolean isActive;

    //relacao com user
    @ManyToOne
    private User user; // Usuário associado (se aplicável)
}
