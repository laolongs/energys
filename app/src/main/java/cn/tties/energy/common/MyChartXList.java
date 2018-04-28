package cn.tties.energy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2018/4/27
 * description：
 * author：guojlli
 */

public class MyChartXList {
    ArrayList<String> list=new ArrayList();
    ArrayList<String> listdate=new ArrayList();
    public ArrayList<String> getlist(){
        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                list.add("0" + (i + 1));
            } else {
                list.add("" + (i + 1));
            }
        }
        return list;
    }
    public ArrayList<String> getListdate(int month,int day){
        for (int i = 20; i < day; i++) {
            listdate.add(month+"-"+i);
            if(month==12){
                if(i>=day-1){
                    for (int j = 0; j <20 ; j++) {
                        if(j<9){
                            listdate.add(1+"-0" + (j + 1));
                        }else{
                            listdate.add(1+"-" + (j + 1));
                        }
                    }
                }
            }else{
                if(i>=day-1){
                    for (int j = 0; j <20 ; j++) {
                        if(j<9){
                            listdate.add((month+1)+"-0" + (j + 1));
                        }else{
                            listdate.add((month+1)+"-" + (j + 1));
                        }
                    }
                }
            }


        }
        return listdate;
    }
    public Map get(int year,int month){
//        int month=4;
//        int year=2018;
        int length = 31;
        switch(month) {
            case 3:
                length = 28;
                if (year % 4 == 0) {
                    length = 29;
                }
                break;
            case 5:
            case 7:
            case 8:
            case 12:
                length = 30;
                break;
        }
        List X=new ArrayList();
        Map XMap=new HashMap();
        for (int i = 20, j = 0; j < length; i++, j++) {
            int day = i > length ? i - length : i;
            int mon = i > length ? month : (month == 1 ? 12 : month - 1);
            String value = (mon < 10 ? "0" : "") + mon + "-" + (day < 10 ? "0" : "") + day;
            X.add(j,value);
            XMap.put(value,j);
        }
        Map map=new HashMap();
        map.put("X",X);
        map.put("XMap",XMap);
        return map;
    }
}
