package codesquad.domain;

import java.util.HashMap;
import java.util.Map;

public class Result {
//    private static Map<String, Result> resultType = new HashMap<>();
//    static{
//        resultType.put("success", new Result(true));
//        resultType.put("fail", new Result(false, ""));
//    }
    private static final Result SUCCESS = new Result(true);
    private boolean type;
    private String msg;
    private Result(boolean type){
        this.type = type;
    }
    private Result(boolean type, String msg){
        this.type = type;
        this.msg = msg;
    }

    public static Result ok(){
        return SUCCESS;//resultType.get("success");
    }

    public static Result fail(String msg){
        return new Result(false,msg);
    }

    public static Result getSUCCESS() {
        return SUCCESS;
    }

    public boolean isType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
