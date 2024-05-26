package simsek.ali.VeterinaryManagementProject.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, Class entityClass) {
        super(entityClass.getSimpleName() + " with id:" + id + " does not found");
    }
}
