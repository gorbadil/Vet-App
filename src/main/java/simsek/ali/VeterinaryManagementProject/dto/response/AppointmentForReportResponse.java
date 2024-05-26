package simsek.ali.VeterinaryManagementProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentForReportResponse {

    private Long id;
    private LocalDateTime date;
    private String customerName;
    private String animalName;
    private String doctorName;

}
