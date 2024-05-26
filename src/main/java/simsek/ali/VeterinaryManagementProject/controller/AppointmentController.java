package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.AppointmentRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.AppointmentResponse;
import simsek.ali.VeterinaryManagementProject.service.AppointmentService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> findAllAppointments(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
        ){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAllAppointments(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findAppointmentById (@PathVariable Long id){
        return ResponseEntity.ok().body(appointmentService.findAppointmentByIdResponse(id));
    }

    @GetMapping("/searchByDoctorAndDateRange")
    public ResponseEntity<Page<AppointmentResponse>> findAppointmentByDoctorIdAndDateRange (
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
        ){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.findAppointmentByDoctorIdAndDateRange(id, startDate, endDate, pageNumber, pageSize));
    }


    @GetMapping("/searchByAnimalAndDateRange")
    public ResponseEntity<Page<AppointmentResponse>> findAppointmentByAnimalIdAndDateRange (
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            ){
        return ResponseEntity.ok().body(appointmentService.findAppointmentByAnimalIdAndDateRange(id, startDate, endDate, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment (@RequestBody AppointmentRequest appointmentRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(appointmentRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment (@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.updateAppointment(id, appointmentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.deleteAppointment(id));
    }
}
