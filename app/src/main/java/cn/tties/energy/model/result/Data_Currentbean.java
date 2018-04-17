package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/4
 * description：电流
 * author：guojlli
 */

public class Data_Currentbean {
    private List<DataListBean> dataList=new ArrayList<>();

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * meterId : 1486536312217
         * freezeTime : 2017-02-20 00:00:00
         * a : 923.867
         * b : 936.059
         * c : 593.493
         * d : null
         * dm : 784
         * max : 0
         * maxTime : null
         * min : 0
         * minTime : null
         * upperTime : 0
         * lowerTime : 0
         * limitRate : 0
         * fpy : 0
         * monTime : 0
         * md : null
         * hasOver : false
         */

        private long meterId;
        private String freezeTime;
        private double a;
        private double b;
        private double c;
        private Object d;
        private double dm;
        private double max;
        private Object maxTime;
        private double min;
        private Object minTime;
        private double upperTime;
        private double lowerTime;
        private double limitRate;
        private double fpy;
        private double monTime;
        private Object md;
        private boolean hasOver;

        public long getMeterId() {
            return meterId;
        }

        public void setMeterId(long meterId) {
            this.meterId = meterId;
        }

        public String getFreezeTime() {
            return freezeTime;
        }

        public void setFreezeTime(String freezeTime) {
            this.freezeTime = freezeTime;
        }

        public double getA() {
            return a;
        }

        public void setA(double a) {
            this.a = a;
        }

        public double getB() {
            return b;
        }

        public void setB(double b) {
            this.b = b;
        }

        public double getC() {
            return c;
        }

        public void setC(double c) {
            this.c = c;
        }

        public Object getD() {
            return d;
        }

        public void setD(Object d) {
            this.d = d;
        }

        public double getDm() {
            return dm;
        }

        public void setDm(int dm) {
            this.dm = dm;
        }

        public double getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public Object getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(Object maxTime) {
            this.maxTime = maxTime;
        }

        public double getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public Object getMinTime() {
            return minTime;
        }

        public void setMinTime(Object minTime) {
            this.minTime = minTime;
        }

        public double getUpperTime() {
            return upperTime;
        }

        public void setUpperTime(int upperTime) {
            this.upperTime = upperTime;
        }

        public double getLowerTime() {
            return lowerTime;
        }

        public void setLowerTime(int lowerTime) {
            this.lowerTime = lowerTime;
        }

        public double getLimitRate() {
            return limitRate;
        }

        public void setLimitRate(int limitRate) {
            this.limitRate = limitRate;
        }

        public double getFpy() {
            return fpy;
        }

        public void setFpy(int fpy) {
            this.fpy = fpy;
        }

        public double getMonTime() {
            return monTime;
        }

        public void setMonTime(int monTime) {
            this.monTime = monTime;
        }

        public Object getMd() {
            return md;
        }

        public void setMd(Object md) {
            this.md = md;
        }

        public boolean isHasOver() {
            return hasOver;
        }

        public void setHasOver(boolean hasOver) {
            this.hasOver = hasOver;
        }
    }

}
