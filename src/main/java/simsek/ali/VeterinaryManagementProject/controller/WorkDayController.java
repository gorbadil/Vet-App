package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.DoctorRequest;
import simsek.ali.VeterinaryManagementProject.dto.request.WorkDayRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.DoctorResponse;
import simsek.ali.VeterinaryManagementProject.dto.response.WorkDayResponse;
import simsek.ali.VeterinaryManagementProject.entity.WorkDay;
import simsek.ali.VeterinaryManagementProject.service.WorkDayService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/available-dates")
@RequiredArgsConstructor
public class WorkDayController {

    public final WorkDayService workDayService;

    @GetMapping
    public ResponseEntity<Page<WorkDayResponse>> findAllWorkDays (
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(workDayService.findAllWorkDays(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDayResponse> findWorkDayById (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(workDayService.findWorkDayById(id));
    }

    @PostMapping
    public ResponseEntity<WorkDayResponse> createWorkDay (@RequestBody WorkDayRequest workDayRequest){
        return ResponseEntity.status(HttpStatus.OK).body(workDayService.createWorkDay(workDayRequest)) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDayResponse> updateWorkDay (@PathVariable Long id, @RequestBody WorkDayRequest workDayRequest){
        return ResponseEntity.status(HttpStatus.OK).body(workDayService.updateWorkDay(id, workDayRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAvailableDate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(workDayService.deleteWorkDay(id));
    }
}
