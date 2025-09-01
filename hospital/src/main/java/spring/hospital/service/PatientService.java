package spring.hospital.service;

import org.springframework.stereotype.Service;
import spring.hospital.enums.Status;
import spring.hospital.model.Patient;
import spring.hospital.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository repo) {
        this.patientRepository = repo;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public List<Patient> getByDateOfBirthRange(LocalDate start, LocalDate end) {
        return patientRepository.findByDateOfBirthBetween(start, end);
    }

    public List<Patient> getByAdmittingDoctorDepartment(String department) {
        return patientRepository.findByAdmittedBy_Department(department);
    }

    public List<Patient> getByAdmittingDoctorStatusOff() {
        return patientRepository.findByAdmittedBy_Status(Status.OFF);
    }
}
