package spring.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hospital.enums.Status;
import spring.hospital.model.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByStatus(Status status);
    List<Employee> findByDepartment(String department);
}
