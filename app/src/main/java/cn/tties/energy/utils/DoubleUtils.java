package cn.tties.energy.utils;


import java.math.BigDecimal;

public class DoubleUtils {

    private static final int ROUND_HALF_UP_COUNT = 2;

    public static Double div(Double dou1, Double dou2) {
       return  new BigDecimal(dou1 / dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double mul(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 * dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double add(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 + dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double sub(Double dou1, Double dou2) {
        return  new BigDecimal(dou1 - dou2).setScale(ROUND_HALF_UP_COUNT, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    //判断几位数
    public static int getPositionNum(int d){
        String b=String.valueOf(d);
        return b.length();
    }
    //换算千
    public static int getThousandNum(int d){
        int n=0;
        if(d!=0){
            if(d<1000){
                return d;
            }else{
                n = (int)d/1000;
            }
        }
        return n;
    }
    //换算万
    public static String getNum(double d){
        double n=0;
        if(d!=0){
           if(d<10000){
               return String.format("%.1f", d)+"";
           }else{
               n = (double)d/10000;
           }
        }else{
            return 0+"";
        }
        return String.format("%.2f", n)+"万";
    }
    //负数
    public static String getNegative(double d){
        double n=0;
        if(d!=0){
            if(d>-10000){
                return String.format("%.1f", d)+"";
            }else{
                n = (double)d/10000;
            }
        }else{
            return 0+"";
        }
        return String.format("%.2f", n)+"万";
    }
    //因数
    public static String getRate(double d){
        double n=0;
        if(d!=0){
            return String.format("%.2f", d)+"";
        }
        return "0";
    }
    //保留精度值
    public static double getBigDecimal(double num,int size){
        BigDecimal b = new BigDecimal(num / size);
        double df = b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df;
    }
}