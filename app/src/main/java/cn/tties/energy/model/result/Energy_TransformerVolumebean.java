package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_TransformerVolumebean {


    /**
     * errorMessage : 成功
     * result : [{"data":0,"baseDate":"2017-01"},{"data":0,"baseDate":"2017-02"},{"data":0,"baseDate":"2017-03"},{"data":0,"baseDate":"2017-04"},{"data":0,"baseDate":"2017-04"},{"data":0,"baseDate":"2017-05"},{"data":0,"baseDate":"2017-06"},{"data":0,"baseDate":"2017-07"},{"data":0,"baseDate":"2017-08"},{"data":0,"baseDate":"2017-10"},{"data":0,"baseDate":"2017-11"},{"data":0,"baseDate":"2017-12"}]
     * errorCode : 0
     */

    private String errorMessage;
    private int errorCode;
    private List<ResultBean> result=new ArrayList<>();

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : 0
         * baseDate : 2017-01
         */

        private double data;
        private String baseDate;

        public double getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public String getBaseDate() {
            return baseDate;
        }

        public void setBaseDate(String baseDate) {
            this.baseDate = baseDate;
        }
    }
}
