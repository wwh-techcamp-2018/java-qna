package codesquad.service;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
    public CustomException(CustomErrorMessage errorMessage){
        super(errorMessage.getMessage());
    }
}
