package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/3/27
 * description：
 * author：guojlli
 */

public class Data_Pressbean {
    private List<LimitDataBean> limitData=new ArrayList<>();
    private List<MaxDataBean> maxData=new ArrayList<>();
    private List<MaxTimeDataBean> maxTimeData=new ArrayList<>();

    public List<LimitDataBean> getLimitData() {
        return limitData;
    }

    public void setLimitData(List<LimitDataBean> limitData) {
        this.limitData = limitData;
    }

    public List<MaxDataBean> getMaxData() {
        return maxData;
    }

    public void setMaxData(List<MaxDataBean> maxData) {
        this.maxData = maxData;
    }

    public List<MaxTimeDataBean> getMaxTimeData() {
        return maxTimeData;
    }

    public void setMaxTimeData(List<MaxTimeDataBean> maxTimeData) {
        this.maxTimeData = maxTimeData;
    }

    public static class LimitDataBean {
        /**
         * FREEZETIME : 2017-02-08 00:00:00
         * VULIMIT : 0
         */

        private String FREEZETIME;
        private int VULIMIT;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public int getVULIMIT() {
            return VULIMIT;
        }

        public void setVULIMIT(int VULIMIT) {
            this.VULIMIT = VULIMIT;
        }
    }

    public static class MaxDataBean {
        /**
         * FREEZETIME : 2017-02-08 00:00:00
         * VUMAX : 0.5
         */

        private String FREEZETIME;
        private double VUMAX;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public double getVUMAX() {
            return VUMAX;
        }

        public void setVUMAX(double VUMAX) {
            this.VUMAX = VUMAX;
        }
    }

    public static class MaxTimeDataBean {
        /**
         * FREEZETIME : 2017-02-08
         * VUMAXTIME : 09:16
         */

        private String FREEZETIME;
        private String VUMAXTIME;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public String getVUMAXTIME() {
            return VUMAXTIME;
        }

        public void setVUMAXTIME(String VUMAXTIME) {
            this.VUMAXTIME = VUMAXTIME;
        }
    }
}
