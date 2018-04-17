package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class DataFragmentbean {

    private List<Databean.DataListBean> dataList=new ArrayList<>();

    public List<Databean.DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<Databean.DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * sectorFengFee : 614061
         * sectorGuFee : 308181.536
         * cost : 0.6782
         * baseDate : 2018-01
         * declareType : 1
         * totalEnergy : 1423111
         * sectorJianValue : 0
         * sectorPingValue : 682290
         * feeSum : 922242.536
         * fouceSum : 12890.7525
         * maxMDDate : 2018-01-18 01:30:00
         * ap : 1583216
         * totalSum : 965133.2885
         * volume : 1000
         * sectorFengValue : 0
         * sectorPingFee : 0
         * sectorJianFee : 0
         * rate : 764.295
         * baseSum : 30000
         * maxMD : 4672
         * sectorGuValue : 740821
         * feeValue : 1423111
         * rp : 1583216
         */

        private double sectorFengFee;
        private double sectorGuFee;
        private double cost;
        private String baseDate;
        private double declareType;
        private double totalEnergy;
        private int sectorJianValue;
        private int sectorPingValue;
        private double feeSum;
        private double fouceSum;
        private String maxMDDate;
        private double ap;
        private double totalSum;
        private double volume;
        private int sectorFengValue;
        private double sectorPingFee;
        private double sectorJianFee;
        private double rate;
        private double baseSum;
        private double maxMD;
        private int sectorGuValue;
        private int feeValue;
        private double rp;

        public double getSectorFengFee() {
            return sectorFengFee;
        }

        public void setSectorFengFee(int sectorFengFee) {
            this.sectorFengFee = sectorFengFee;
        }

        public double getSectorGuFee() {
            return sectorGuFee;
        }

        public void setSectorGuFee(double sectorGuFee) {
            this.sectorGuFee = sectorGuFee;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public String getBaseDate() {
            return baseDate;
        }

        public void setBaseDate(String baseDate) {
            this.baseDate = baseDate;
        }

        public double getDeclareType() {
            return declareType;
        }

        public void setDeclareType(int declareType) {
            this.declareType = declareType;
        }

        public double getTotalEnergy() {
            return totalEnergy;
        }

        public void setTotalEnergy(int totalEnergy) {
            this.totalEnergy = totalEnergy;
        }

        public int getSectorJianValue() {
            return sectorJianValue;
        }

        public void setSectorJianValue(int sectorJianValue) {
            this.sectorJianValue = sectorJianValue;
        }

        public int getSectorPingValue() {
            return sectorPingValue;
        }

        public void setSectorPingValue(int sectorPingValue) {
            this.sectorPingValue = sectorPingValue;
        }

        public double getFeeSum() {
            return feeSum;
        }

        public void setFeeSum(double feeSum) {
            this.feeSum = feeSum;
        }

        public double getFouceSum() {
            return fouceSum;
        }

        public void setFouceSum(double fouceSum) {
            this.fouceSum = fouceSum;
        }

        public String getMaxMDDate() {
            return maxMDDate;
        }

        public void setMaxMDDate(String maxMDDate) {
            this.maxMDDate = maxMDDate;
        }

        public double getAp() {
            return ap;
        }

        public void setAp(int ap) {
            this.ap = ap;
        }

        public double getTotalSum() {
            return totalSum;
        }

        public void setTotalSum(double totalSum) {
            this.totalSum = totalSum;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public int getSectorFengValue() {
            return sectorFengValue;
        }

        public void setSectorFengValue(int sectorFengValue) {
            this.sectorFengValue = sectorFengValue;
        }

        public double getSectorPingFee() {
            return sectorPingFee;
        }

        public void setSectorPingFee(int sectorPingFee) {
            this.sectorPingFee = sectorPingFee;
        }

        public double getSectorJianFee() {
            return sectorJianFee;
        }

        public void setSectorJianFee(int sectorJianFee) {
            this.sectorJianFee = sectorJianFee;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getBaseSum() {
            return baseSum;
        }

        public void setBaseSum(int baseSum) {
            this.baseSum = baseSum;
        }

        public double getMaxMD() {
            return maxMD;
        }

        public void setMaxMD(int maxMD) {
            this.maxMD = maxMD;
        }

        public int getSectorGuValue() {
            return sectorGuValue;
        }

        public void setSectorGuValue(int sectorGuValue) {
            this.sectorGuValue = sectorGuValue;
        }

        public int getFeeValue() {
            return feeValue;
        }

        public void setFeeValue(int feeValue) {
            this.feeValue = feeValue;
        }

        public double getRp() {
            return rp;
        }

        public void setRp(int rp) {
            this.rp = rp;
        }
    }
}
