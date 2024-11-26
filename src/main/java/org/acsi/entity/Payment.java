package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDateTime paymentDueDate; // Prazo que falta para pagamento expirar
    public LocalDateTime paymentDate; // Data em que foi pago
    public Double amount;
    public Boolean isPaid;

    @OneToOne
    @JoinColumn(name = "parking_session_id", nullable = false, unique = true)
    public ParkingSession parkingSession;
}
