package cn.tties.energy.model.result;

/**
 * Created by li on 2018/4/3
 * description：回复信息
 * author：guojlli
 */

public class Discussbean {
    /**
     * errorMessage : 成功
     * errorCode : 0
     */

    private String errorMessage;
    private int errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
