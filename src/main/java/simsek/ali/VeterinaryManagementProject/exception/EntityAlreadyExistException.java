package simsek.ali.VeterinaryManagementProject.exception;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(Class entityClass) {
        super("The "+ entityClass.getSimpleName() + " has already been saved.");
    }
}
