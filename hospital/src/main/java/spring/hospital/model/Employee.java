package spring.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.hospital.enums.Status;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    private String department;
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;
}