package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime paymentDueDate; // Prazo que falta para pagamento expirar
    private LocalDateTime paymentDate; // Data em que foi pago
    private Double amount; // Valor do pagamento
    private Boolean isPaid; // Status (pago ou pendente)

    //relacao com parkingSession
    @OneToOne
    private ParkingSession parkingSession; // Sessão associada
}
