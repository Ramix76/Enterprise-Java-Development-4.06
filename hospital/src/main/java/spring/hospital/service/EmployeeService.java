package spring.hospital.service;

import org.springframework.stereotype.Service;
import spring.hospital.enums.Status;
import spring.hospital.model.Employee;
import spring.hospital.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repo) {
        this.employeeRepository = repo;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getByStatus(Status status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Employee> getByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
}
