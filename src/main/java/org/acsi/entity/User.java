package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;

    private int points; // Pontos acumulados

    //relacao com parkingSession
    private Boolean hasActiveSession; // Indicador se tem check-in ativo

    //relacao com bonus
}
