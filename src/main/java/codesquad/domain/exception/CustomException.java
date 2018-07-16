package codesquad.domain.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
    public CustomException(CustomErrorMessage errorMessage){
        this(errorMessage.getMessage());
    }
}