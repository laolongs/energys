package cn.tties.energy.common;

import android.util.Log;

import com.github.mikephil.charting.components.YAxis;

import java.util.ArrayList;

import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;

/**
 * Created by li on 2018/4/20
 * description：
 * author：guojlli
 */

public class MyAllTimeYear {
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> liststr = new ArrayList<String>();
    int year = DateUtil.getCurrentYear();
    int month = DateUtil.getCurrentMonth();
    private  ArrayList<String> createArrays() {
        for (int i = 0; i < 13; i++){
            if (month - i < 1) {
                String mon = month - i + 12 < 10 ? "0" + (month - i + 12) : month - i + 12+"";
                list.add((year - 1) + "-" + mon );
            } else {
                String mon = month - i < 10 ? "0" + (month - i) : month - i+"";
                list.add((year + "-" + mon ));
            }
        }
        return list;
    }
    private ArrayList<String> getTiemArrays() {
        for (int i = 0; i < 13; i++){
            if (month - i < 1) {
                String mon = month - i + 12 < 10 ? "0" + (month - i + 12) : month - i + 12+"";
                liststr.add((year - 1) + "年" + mon + "月");
            } else {
                String mon = month - i < 10 ? "0" + (month - i) : month - i+"";
                liststr.add((year + "年" + mon + "月"));
            }
        }
        return liststr;
    }
    public static int getTiemBase(int position){
        int year=0;
        switch (position){

            case 0:
                year= DateUtil.getCurrentYear();
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,DateUtil.getCurrentYear()+"-01");
                break;
            case 1:
                year=DateUtil.getCurrentYear()-1;
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,DateUtil.getCurrentYear()-1+"-01");

                break;
            case 2:
                year=DateUtil.getCurrentYear()-2;
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,DateUtil.getCurrentYear()-2+"-01");

                break;
            case 3:
                year=DateUtil.getCurrentYear()-3;
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,DateUtil.getCurrentYear()-3+"-01");

                break;
            case 4:
                year=DateUtil.getCurrentYear()-4;
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,DateUtil.getCurrentYear()-4+"-01");
                break;
            default:
                break;
        }
        return year;
    }
    public  String getTiemMonthBase(int position){
        ArrayList<String> arrays = createArrays();
        ArrayList<String> tiems = getTiemArrays();
        String year=null;
        switch (position){
            case 0:
                year= tiems.get(0);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(0));
                break;
            case 1:
                year= tiems.get(1);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(1));
                break;
            case 2:
                year= tiems.get(2);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(2));
                break;
            case 3:
                year= tiems.get(3);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(3));
                break;
            case 4:
                year= tiems.get(4);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(4));
                break;
            case 5:
                year= tiems.get(5);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(5));
                break;
            case 6:
                year= tiems.get(6);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(6));
                break;
            case 7:
                year= tiems.get(7);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(7));
                break;
            case 8:
                year= tiems.get(8);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(8));
                break;
            case 9:
                year= tiems.get(9);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(9));
                break;
            case 10:
                year= tiems.get(10);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(10));
                break;
            case 11:
                year= tiems.get(11);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(11));
                break;
            case 12:
                year= tiems.get(12);
                ACache.getInstance().put(Constants.CACHE_OPS_BASEDATE,arrays.get(12));
                break;
            default:
                break;
        }
        Log.i("----year-----------", "getTiemMonthBase: "+year);
        return year;
    }
}
