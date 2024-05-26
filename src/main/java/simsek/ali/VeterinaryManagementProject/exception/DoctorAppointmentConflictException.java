package simsek.ali.VeterinaryManagementProject.exception;

import java.time.LocalDate;

public class DoctorAppointmentConflictException extends RuntimeException {
    public DoctorAppointmentConflictException(LocalDate date) {
        super();
    }
}
