package spring.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.hospital.enums.Status;
import spring.hospital.model.Employee;
import spring.hospital.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllDoctors() {
        List<Employee> doctors = employeeService.getAll();
        return doctors.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(doctors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getDoctorById(@PathVariable("id") Long id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> getDoctorsByStatus(@PathVariable Status status) {
        List<Employee> doctors = employeeService.getByStatus(status);
        return doctors.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(doctors);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getDoctorsByDepartment(@PathVariable String department) {
        List<Employee> doctors = employeeService.getByDepartment(department);
        return doctors.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(doctors);
    }
}



//package spring.hospital.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import spring.hospital.enums.Status;
//import spring.hospital.model.Employee;
//import spring.hospital.repository.EmployeeRepository;
//import spring.hospital.service.EmployeeService;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/doctors")
//public class EmployeeController {
//
//    private final EmployeeService employeeService;
//
//    @Autowired
//    public EmployeeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }
//
//    @GetMapping
//    public List<Employee> getAllDoctors() {
//        return employeeService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Employee> getDoctorById(@PathVariable("id") Long id) {
//        Optional<Employee> doctor = employeeService.getById(id);
//        if (doctor.isPresent()) {
//            return ResponseEntity.ok(doctor.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @GetMapping("/status/{status}")
//    public List<Employee> getDoctorsByStatus(@PathVariable Status status) {
//        return employeeService.getByStatus(status);
//    }
//
//    @GetMapping("/department/{department}")
//    public List<Employee> getDoctorsByDepartment(@PathVariable String department) {
//        return employeeService.getByDepartment(department);
//    }
//}
//
