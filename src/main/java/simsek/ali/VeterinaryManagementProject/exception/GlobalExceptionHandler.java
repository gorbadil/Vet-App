package simsek.ali.VeterinaryManagementProject.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(generateErrorResponse(404,exception,request));
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> entityAlreadyExistHandler(EntityAlreadyExistException exception, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateErrorResponse(400,exception,request));
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ErrorResponse> duplicateDataExceptionHandler(DuplicateDataException exception, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateErrorResponse(400,exception,request));
    }

    @ExceptionHandler(DoctorNotAvailableException.class)
    public ResponseEntity<ErrorResponse> doctorNotAvailableExceptionHandler(DoctorNotAvailableException exception, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generateErrorResponse(400,exception,request));
    }

    public ErrorResponse generateErrorResponse (int status, Exception ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(status);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getRequestURI());
        return errorResponse;
    }
}
