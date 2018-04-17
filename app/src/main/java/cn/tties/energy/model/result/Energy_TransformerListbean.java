package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_TransformerListbean {
    /**
     * result : [{"flag":0,"createTime":"2018-01-20 18:05:18","name":"1#主变","pid":-1,"companyEquipmentId":476,"eleAccountId":61,"equipmentId":90}]
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
         * flag : 0
         * createTime : 2018-01-20 18:05:18
         * name : 1#主变
         * pid : -1
         * companyEquipmentId : 476
         * eleAccountId : 61
         * equipmentId : 90
         */

        private int flag;
        private String createTime;
        private String name;
        private int pid;
        private int companyEquipmentId;
        private int eleAccountId;
        private int equipmentId;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getCompanyEquipmentId() {
            return companyEquipmentId;
        }

        public void setCompanyEquipmentId(int companyEquipmentId) {
            this.companyEquipmentId = companyEquipmentId;
        }

        public int getEleAccountId() {
            return eleAccountId;
        }

        public void setEleAccountId(int eleAccountId) {
            this.eleAccountId = eleAccountId;
        }

        public int getEquipmentId() {
            return equipmentId;
        }

        public void setEquipmentId(int equipmentId) {
            this.equipmentId = equipmentId;
        }
    }
}
