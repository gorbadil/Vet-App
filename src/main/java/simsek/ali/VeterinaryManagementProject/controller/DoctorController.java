package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.DoctorRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.DoctorResponse;
import simsek.ali.VeterinaryManagementProject.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    public final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<Page<DoctorResponse>> findAllDoctors (
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findAllDoctors(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> findDoctorById (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.findDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor (@RequestBody DoctorRequest doctorRequest){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.createDoctor(doctorRequest)) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor (@PathVariable Long id, @RequestBody DoctorRequest doctorRequest){
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.updateDoctor(id, doctorRequest));
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id){
        return doctorService.deleteDoctor(id);
    }
}
