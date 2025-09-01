package spring.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hospital.enums.Status;
import spring.hospital.model.Patient;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDateOfBirthBetween(LocalDate start, LocalDate end);
    List<Patient> findByAdmittedBy_Department(String department);
    List<Patient> findByAdmittedBy_Status(Status status);
}
