package spring.hospital.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.hospital.model.Patient;
import spring.hospital.service.PatientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService service) {
        this.patientService = service;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAll();
        return patients.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return (patient != null)
                ? ResponseEntity.ok(patient)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/dob")
    public ResponseEntity<List<Patient>> getPatientsByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<Patient> patients = patientService.getByDateOfBirthRange(startDate, endDate);

        return patients.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(patients);
    }

    @GetMapping("/admittedByDepartment/{department}")
    public ResponseEntity<List<Patient>> getPatientsByAdmittingDoctorDepartment(
            @PathVariable String department) {

        List<Patient> patients = patientService.getByAdmittingDoctorDepartment(department);
        return patients.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(patients);
    }

    @GetMapping("/admittedByDoctorOff")
    public ResponseEntity<List<Patient>> getPatientsWithDoctorStatusOff() {
        List<Patient> patients = patientService.getByAdmittingDoctorStatusOff();
        return patients.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(patients);
    }
}