package cn.tties.energy.model.result;

/**
 * Created by li on 2018/4/5
 * description：
 * author：guojlli
 */

public class Energy_BasePlanbean {

    /**
     * volumeValue : 1000
     * baseDate : 2017-03
     * bestFee : 30000
     * demandValue : 4998
     * demandFee : 40000
     * volumeFee : 30000
     * bestValue : 1000
     * bestType : 1
     */

    private double volumeValue;
    private String baseDate;
    private double bestFee;
    private double demandValue;
    private double demandFee;
    private double volumeFee;
    private double bestValue;
    private double bestType;

    public double getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(int volumeValue) {
        this.volumeValue = volumeValue;
    }

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public double getBestFee() {
        return bestFee;
    }

    public void setBestFee(int bestFee) {
        this.bestFee = bestFee;
    }

    public double getDemandValue() {
        return demandValue;
    }

    public void setDemandValue(int demandValue) {
        this.demandValue = demandValue;
    }

    public double getDemandFee() {
        return demandFee;
    }

    public void setDemandFee(int demandFee) {
        this.demandFee = demandFee;
    }

    public double getVolumeFee() {
        return volumeFee;
    }

    public void setVolumeFee(int volumeFee) {
        this.volumeFee = volumeFee;
    }

    public double getBestValue() {
        return bestValue;
    }

    public void setBestValue(int bestValue) {
        this.bestValue = bestValue;
    }

    public double getBestType() {
        return bestType;
    }

    public void setBestType(int bestType) {
        this.bestType = bestType;
    }
}
