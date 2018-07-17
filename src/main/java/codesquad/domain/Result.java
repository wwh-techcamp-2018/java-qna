package codesquad.domain;


public class Result {
    private String resultMessage;
    private long id;

    public Result() {
        this.resultMessage = "";
        this.id = -1;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public long getId() {
        return id;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Result ok(Long id){
        this.id = id;
        return this;
    }
}
