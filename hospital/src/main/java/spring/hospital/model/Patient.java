package spring.hospital.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String name;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "admitted_by")
    private Employee admittedBy;
}
