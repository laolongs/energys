package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_TransformerTemperaturebean {


    /**
     * result : [{"data":0,"time":"2018-01"},{"data":0,"time":"2018-02"},{"data":"123","time":"2018-03"},{"data":"123","time":"2018-04"},{"data":"123","time":"2018-05"},{"data":"123","time":"2018-06"},{"data":"123","time":"2018-07"},{"data":"123","time":"2018-08"},{"data":"123","time":"2018-09"},{"data":"123","time":"2018-10"},{"data":"123","time":"2018-11"},{"data":"123","time":"2018-12"}]
     * errorMessage : 成功
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
         * time : 2018-01
         */

        private double data;
        private String time;

        public double getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
