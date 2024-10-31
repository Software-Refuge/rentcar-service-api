package uz.carapp.rentcarapp.rest.errors;

public class BadRequestCustomException extends RuntimeException {

    private  String defaultMessage;
    private  String  entityName;
    private  String errorKey;

    public BadRequestCustomException(String defaultMessage, String entityName, String errorKey) {
        this.defaultMessage = defaultMessage;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
