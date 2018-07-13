package codesquad.service;

public enum CustomErrorMessage {

    BLANK("공란을 모두 채워주세요."),
    NOT_VALID_PATH("존재하지 않는 경로의 접근입니다"),
    NOT_AUTHORIZED("권한이 없습니다"),
    INCORRECT_ACCESS("비정상적 접근입니다"),
    LOGIN_FAILED("아이디 혹은 비밀번호가 틀렸습니다.");


    private String message;

    CustomErrorMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
