package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/3/28
 * description：
 * author：guojlli
 */

public class Data_Nobean {

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
         * FREEZETIME : 2017-03-01 00:00:00
         * IULIMIT : 0
         */

        private String FREEZETIME;
        private int IULIMIT;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public int getIULIMIT() {
            return IULIMIT;
        }

        public void setIULIMIT(int IULIMIT) {
            this.IULIMIT = IULIMIT;
        }
    }

    public static class MaxDataBean {
        /**
         * FREEZETIME : 2017-03-01 00:00:00
         * IUMAX : 6.6
         */

        private String FREEZETIME;
        private double IUMAX;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public double getIUMAX() {
            return IUMAX;
        }

        public void setIUMAX(double IUMAX) {
            this.IUMAX = IUMAX;
        }
    }

    public static class MaxTimeDataBean {
        /**
         * FREEZETIME : 2017-03-01
         * IUMAXTIME : 08:25
         */

        private String FREEZETIME;
        private String IUMAXTIME;

        public String getFREEZETIME() {
            return FREEZETIME;
        }

        public void setFREEZETIME(String FREEZETIME) {
            this.FREEZETIME = FREEZETIME;
        }

        public String getIUMAXTIME() {
            return IUMAXTIME;
        }

        public void setIUMAXTIME(String IUMAXTIME) {
            this.IUMAXTIME = IUMAXTIME;
        }
    }
}
