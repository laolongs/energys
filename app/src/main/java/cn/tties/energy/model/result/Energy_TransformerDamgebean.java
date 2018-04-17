package cn.tties.energy.model.result;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_TransformerDamgebean {

    /**
     * result : {"damge":0,"consume":0}
     * errorMessage : 成功
     * errorCode : 0
     */

    private ResultBean result;
    private String errorMessage;
    private int errorCode;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * damge : 0
         * consume : 0
         */

        private int damge;
        private int consume;

        public int getDamge() {
            return damge;
        }

        public void setDamge(int damge) {
            this.damge = damge;
        }

        public int getConsume() {
            return consume;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }
    }
}
