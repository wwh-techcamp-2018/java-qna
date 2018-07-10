package codesquad.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super("/");
    }
}
