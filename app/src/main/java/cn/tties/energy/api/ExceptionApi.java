package cn.tties.energy.api;



public class ExceptionApi extends RuntimeException {

    private String mMessage;

    public ExceptionApi(String reason) {
        super(reason);
        this.mMessage = reason;
    }
    public String getmMessage() {
        return mMessage;
    }
}
