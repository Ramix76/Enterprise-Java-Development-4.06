package spring.hospital.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.hospital.enums.Status;
import spring.hospital.model.Employee;
import spring.hospital.model.Patient;
import spring.hospital.repository.EmployeeRepository;
import spring.hospital.repository.PatientRepository;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(EmployeeRepository employeeRepo, PatientRepository patientRepo) {
        return args -> {

            Employee e1 = new Employee(356712L, "cardiology", "Alonso Flores", Status.ON_CALL);
            Employee e2 = new Employee(564134L, "immunology", "Sam Ortega", Status.ON);
            Employee e3 = new Employee(761527L, "cardiology", "German Ruiz", Status.OFF);
            Employee e4 = new Employee(166552L, "pulmonary", "Maria Lin", Status.ON);
            Employee e5 = new Employee(156545L, "orthopaedic", "Paolo Rodriguez", Status.ON_CALL);
            Employee e6 = new Employee(172456L, "psychiatric", "John Paul Armes", Status.OFF);

            employeeRepo.saveAll(java.util.List.of(e1, e2, e3, e4, e5, e6));

            patientRepo.save(new Patient(null, "Jaime Jordan", LocalDate.of(1984, 3, 2), e2));
            patientRepo.save(new Patient(null, "Marian Garcia", LocalDate.of(1972, 1, 12), e2));
            patientRepo.save(new Patient(null, "Julia Dusterdieck", LocalDate.of(1954, 6, 11), e1));
            patientRepo.save(new Patient(null, "Steve McDuck", LocalDate.of(1931, 11, 10), e3));
            patientRepo.save(new Patient(null, "Marian Garcia", LocalDate.of(1999, 2, 15), e6));
        };
    }
}
