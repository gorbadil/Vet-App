package simsek.ali.VeterinaryManagementProject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsek.ali.VeterinaryManagementProject.dto.request.ReportRequest;
import simsek.ali.VeterinaryManagementProject.dto.response.ReportResponse;
import simsek.ali.VeterinaryManagementProject.entity.Report;
import simsek.ali.VeterinaryManagementProject.service.ReportService;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Page<ReportResponse>> findAllReports (
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.findAllReports(pageNumber,pageSize));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> findReportById (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.findReportById(id));
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport (@RequestBody ReportRequest reportRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(reportRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateReport (@PathVariable Long id, @RequestBody ReportRequest reportRequest){
        return ResponseEntity.status(HttpStatus.OK).body(reportService.updateReport(id, reportRequest));
    }

    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id){
        return reportService.deleteReport(id);
    }

}
