package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_Monthlybean {

    /**
     * result : [{"companyId":23,"reportId":33,"reportName":"testcs4月月度安全运维报告单.doc","createTime":"2018-04-02 17:13:33","reportPath":"\\word\\generateWord\\testcs\\4\\testcs4月月度安全运维报告单.doc","type":1}]
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
         * companyId : 23
         * reportId : 33
         * reportName : testcs4月月度安全运维报告单.doc
         * createTime : 2018-04-02 17:13:33
         * reportPath : \word\generateWord\testcs\4\testcs4月月度安全运维报告单.doc
         * type : 1
         */

        private int companyId;
        private int reportId;
        private String reportName;
        private String createTime;
        private String reportPath;
        private int type;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getReportId() {
            return reportId;
        }

        public void setReportId(int reportId) {
            this.reportId = reportId;
        }

        public String getReportName() {
            return reportName;
        }

        public void setReportName(String reportName) {
            this.reportName = reportName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReportPath() {
            return reportPath;
        }

        public void setReportPath(String reportPath) {
            this.reportPath = reportPath;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
