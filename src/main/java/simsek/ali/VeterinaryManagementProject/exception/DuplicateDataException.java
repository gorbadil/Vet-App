package simsek.ali.VeterinaryManagementProject.exception;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(Class entityClass) {
        super("This " + entityClass.getSimpleName() + " has already been registered. That's why this request causes duplicate data");
    }
}
