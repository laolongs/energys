package cn.tties.energy.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by li on 2018/3/30
 * description：
 * author：guojlli
 */

public class OpsLoginbean implements Serializable {

    /**
     * errorMessage : 成功
     * result : {"maintUser":{"createTime":"2018-02-28 13:46:59","staffId":205,"status":0,"staffTel":"0","staffNo":"energy_hfhx888","staffName":"hfhx888","profilePhoto":"\\images\n头像2.jpg","loginPwd":"$shiro1$SHA-256$500000$pB7H71jbNphwIlel+wiYqQ==$vmNopfnsw6cBNBdJWDVJDmovgF3Rquy6SPbraafB74Q="},"companyName":"湖丰化纤","energyLedgerList":[{"energyLedgerId":1519798576927,"eleAccountId":69,"companyId":40,"eleNo":"1701722618"}]}
     * errorCode : 0
     */

    private String errorMessage;
    private ResultBean result;
    private int errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class ResultBean implements Serializable {
        /**
         * maintUser : {"createTime":"2018-02-28 13:46:59","staffId":205,"status":0,"staffTel":"0","staffNo":"energy_hfhx888","staffName":"hfhx888","profilePhoto":"\\images\n头像2.jpg","loginPwd":"$shiro1$SHA-256$500000$pB7H71jbNphwIlel+wiYqQ==$vmNopfnsw6cBNBdJWDVJDmovgF3Rquy6SPbraafB74Q="}
         * companyName : 湖丰化纤
         * energyLedgerList : [{"energyLedgerId":1519798576927,"eleAccountId":69,"companyId":40,"eleNo":"1701722618"}]
         */

        private MaintUserBean maintUser;
        private String companyName;
        private List<EnergyLedgerListBean> energyLedgerList;

        public MaintUserBean getMaintUser() {
            return maintUser;
        }

        public void setMaintUser(MaintUserBean maintUser) {
            this.maintUser = maintUser;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public List<EnergyLedgerListBean> getEnergyLedgerList() {
            return energyLedgerList;
        }

        public void setEnergyLedgerList(List<EnergyLedgerListBean> energyLedgerList) {
            this.energyLedgerList = energyLedgerList;
        }

        public static class MaintUserBean implements Serializable {
            /**
             * createTime : 2018-02-28 13:46:59
             * staffId : 205
             * status : 0
             * staffTel : 0
             * staffNo : energy_hfhx888
             * staffName : hfhx888
             * profilePhoto : \images
             头像2.jpg
             * loginPwd : $shiro1$SHA-256$500000$pB7H71jbNphwIlel+wiYqQ==$vmNopfnsw6cBNBdJWDVJDmovgF3Rquy6SPbraafB74Q=
             */

            private String createTime;
            private int staffId;
            private int status;
            private String staffTel;
            private String staffNo;
            private String staffName;
            private String profilePhoto;
            private String loginPwd;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getStaffId() {
                return staffId;
            }

            public void setStaffId(int staffId) {
                this.staffId = staffId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStaffTel() {
                return staffTel;
            }

            public void setStaffTel(String staffTel) {
                this.staffTel = staffTel;
            }

            public String getStaffNo() {
                return staffNo;
            }

            public void setStaffNo(String staffNo) {
                this.staffNo = staffNo;
            }

            public String getStaffName() {
                return staffName;
            }

            public void setStaffName(String staffName) {
                this.staffName = staffName;
            }

            public String getProfilePhoto() {
                return profilePhoto;
            }

            public void setProfilePhoto(String profilePhoto) {
                this.profilePhoto = profilePhoto;
            }

            public String getLoginPwd() {
                return loginPwd;
            }

            public void setLoginPwd(String loginPwd) {
                this.loginPwd = loginPwd;
            }
        }

        public static class EnergyLedgerListBean  implements Serializable{
            /**
             * energyLedgerId : 1519798576927
             * eleAccountId : 69
             * companyId : 40
             * eleNo : 1701722618
             */

            private long energyLedgerId;
            private int eleAccountId;
            private int companyId;
            private String eleNo;
            boolean ischeck;

            public boolean isIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public long getEnergyLedgerId() {
                return energyLedgerId;
            }

            public void setEnergyLedgerId(long energyLedgerId) {
                this.energyLedgerId = energyLedgerId;
            }

            public int getEleAccountId() {
                return eleAccountId;
            }

            public void setEleAccountId(int eleAccountId) {
                this.eleAccountId = eleAccountId;
            }

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public String getEleNo() {
                return eleNo;
            }

            public void setEleNo(String eleNo) {
                this.eleNo = eleNo;
            }
        }
    }
}
