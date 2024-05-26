package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.VaccinationRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.VaccinationResponse;
import simsek.ali.VeterinaryManagementProject.service.VaccinationService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/vaccinations")
@RequiredArgsConstructor
public class VaccinationController {

    private final VaccinationService vaccinationService;

    @GetMapping
    public ResponseEntity<Page<VaccinationResponse>> findAllVaccinations(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
        ){
        return ResponseEntity.status(HttpStatus.OK).body(vaccinationService.findAllVaccinations(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccinationResponse> findVaccinationById(@PathVariable Long id){

        return ResponseEntity.ok().body(vaccinationService.findVaccinationById(id));
    }

    @GetMapping("/searchByVaccinationRange")
    public ResponseEntity<Page<VaccinationResponse>> findAnimalsByVaccinationProtectionFinishDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            ){
        return ResponseEntity.status(HttpStatus.OK).body(vaccinationService.findAnimalsByVaccinationProtectionFinishDateRange(startDate, endDate, pageNumber, pageSize));
    }

    @GetMapping("/searchByAnimal")
    public ResponseEntity<Page<VaccinationResponse>> findVaccinationsByAnimal(
            @RequestParam Long id,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            ){
        return ResponseEntity.status(HttpStatus.OK).body(vaccinationService.findVaccinationsByAnimal(id, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<VaccinationResponse> createVaccination(@RequestBody VaccinationRequest vaccinationRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(vaccinationService.createVaccination(vaccinationRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccinationResponse> updateVaccination(@PathVariable Long id, @RequestBody VaccinationRequest vaccinationRequest){

        return ResponseEntity.status(HttpStatus.OK).body(vaccinationService.updateVaccination(id, vaccinationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVaccination(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(vaccinationService.deleteVaccination(id));
    }
}
