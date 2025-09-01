package spring.hospital.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import spring.hospital.enums.Status;
import spring.hospital.model.Employee;
import spring.hospital.model.Patient;
import spring.hospital.service.PatientService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        Employee doctorOn = Employee.builder()
                .employeeId(1L)
                .department("Cardiology")
                .name("Alice")
                .status(Status.ON)
                .build();

        Employee doctorOff = Employee.builder()
                .employeeId(2L)
                .department("Neurology")
                .name("Bob")
                .status(Status.OFF)
                .build();

        patient1 = Patient.builder()
                .patientId(1L)
                .name("Charlie")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .admittedBy(doctorOn)
                .build();

        patient2 = Patient.builder()
                .patientId(2L)
                .name("Diana")
                .dateOfBirth(LocalDate.of(2000, 6, 15))
                .admittedBy(doctorOff)
                .build();
    }

    @Test
    void getAllPatients_shouldReturnOk_whenPatientsExist() throws Exception {
        Mockito.when(patientService.getAll()).thenReturn(List.of(patient1, patient2));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Charlie"))
                .andExpect(jsonPath("$[1].name").value("Diana"));
    }

    @Test
    void getAllPatients_shouldReturnNoContent_whenNoPatients() throws Exception {
        Mockito.when(patientService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPatientById_shouldReturnOk_whenPatientFound() throws Exception {
        Mockito.when(patientService.getById(1L)).thenReturn(patient1);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charlie"))
                .andExpect(jsonPath("$.admittedBy.name").value("Alice"));
    }

    @Test
    void getPatientById_shouldReturnNotFound_whenPatientNotFound() throws Exception {
        Mockito.when(patientService.getById(99L)).thenReturn(null);

        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPatientsByDateRange_shouldReturnOk_whenPatientsFound() throws Exception {
        Mockito.when(patientService.getByDateOfBirthRange(
                        LocalDate.of(1985, 1, 1), LocalDate.of(1995, 12, 31)))
                .thenReturn(List.of(patient1));

        mockMvc.perform(get("/patients/dob")
                        .param("start", "1985-01-01")
                        .param("end", "1995-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Charlie"));
    }

    @Test
    void getPatientsByDateRange_shouldReturnNoContent_whenNoneFound() throws Exception {
        Mockito.when(patientService.getByDateOfBirthRange(
                        LocalDate.of(2010, 1, 1), LocalDate.of(2020, 12, 31)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients/dob")
                        .param("start", "2010-01-01")
                        .param("end", "2020-12-31"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPatientsByAdmittingDoctorDepartment_shouldReturnOk_whenFound() throws Exception {
        Mockito.when(patientService.getByAdmittingDoctorDepartment("Cardiology"))
                .thenReturn(List.of(patient1));

        mockMvc.perform(get("/patients/admittedByDepartment/Cardiology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].admittedBy.department").value("Cardiology"));
    }

    @Test
    void getPatientsByAdmittingDoctorDepartment_shouldReturnNoContent_whenNoneFound() throws Exception {
        Mockito.when(patientService.getByAdmittingDoctorDepartment("Oncology"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients/admittedByDepartment/Oncology"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPatientsWithDoctorStatusOff_shouldReturnOk_whenFound() throws Exception {
        Mockito.when(patientService.getByAdmittingDoctorStatusOff())
                .thenReturn(List.of(patient2));

        mockMvc.perform(get("/patients/admittedByDoctorOff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].admittedBy.status").value("OFF"))
                .andExpect(jsonPath("$[0].name").value("Diana"));
    }

    @Test
    void getPatientsWithDoctorStatusOff_shouldReturnNoContent_whenNoneFound() throws Exception {
        Mockito.when(patientService.getByAdmittingDoctorStatusOff())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/patients/admittedByDoctorOff"))
                .andExpect(status().isNoContent());
    }
}