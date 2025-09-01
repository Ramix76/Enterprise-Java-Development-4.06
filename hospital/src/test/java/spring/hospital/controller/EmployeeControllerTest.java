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
import spring.hospital.service.EmployeeService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee employeeOnCall;
    private Employee employeeOn;
    private Employee employeeOff;

    @BeforeEach
    void setUp() {
        employeeOnCall = Employee.builder()
                .employeeId(1L)
                .department("Cardiology")
                .name("Alice")
                .status(Status.ON_CALL)
                .build();

        employeeOn = Employee.builder()
                .employeeId(2L)
                .department("Neurology")
                .name("Bob")
                .status(Status.ON)
                .build();

        employeeOff = Employee.builder()
                .employeeId(3L)
                .department("Oncology")
                .name("Charlie")
                .status(Status.OFF)
                .build();
    }

    @Test
    void getAllDoctors_shouldReturnOk_whenDoctorsExist() throws Exception {
        Mockito.when(employeeService.getAll())
                .thenReturn(Arrays.asList(employeeOnCall, employeeOn));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].status").value("ON"));
    }

    @Test
    void getAllDoctors_shouldReturnNoContent_whenNoDoctors() throws Exception {
        Mockito.when(employeeService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDoctorById_shouldReturnDoctor_whenFound() throws Exception {
        Mockito.when(employeeService.getById(1L))
                .thenReturn(Optional.of(employeeOnCall));

        mockMvc.perform(get("/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.department").value("Cardiology"))
                .andExpect(jsonPath("$.status").value("ON_CALL"));
    }

    @Test
    void getDoctorById_shouldReturnNotFound_whenNotFound() throws Exception {
        Mockito.when(employeeService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/doctors/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDoctorsByStatus_shouldReturnOk_whenFound() throws Exception {
        Mockito.when(employeeService.getByStatus(Status.ON))
                .thenReturn(Collections.singletonList(employeeOn));

        mockMvc.perform(get("/doctors/status/ON"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ON"))
                .andExpect(jsonPath("$[0].name").value("Bob"));
    }

    @Test
    void getDoctorsByStatus_shouldReturnNoContent_whenNoneFound() throws Exception {
        Mockito.when(employeeService.getByStatus(Status.OFF))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/doctors/status/OFF"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDoctorsByDepartment_shouldReturnOk_whenFound() throws Exception {
        Mockito.when(employeeService.getByDepartment("Cardiology"))
                .thenReturn(Collections.singletonList(employeeOnCall));

        mockMvc.perform(get("/doctors/department/Cardiology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].department").value("Cardiology"))
                .andExpect(jsonPath("$[0].status").value("ON_CALL"));
    }

    @Test
    void getDoctorsByDepartment_shouldReturnNoContent_whenNoneFound() throws Exception {
        Mockito.when(employeeService.getByDepartment("Pediatrics"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/doctors/department/Pediatrics"))
                .andExpect(status().isNoContent());
    }
}
