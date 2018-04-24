package cn.tties.energy.model.result;

import android.util.Log;

import org.xml.sax.helpers.LocatorImpl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.tties.energy.common.Constants;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.DateUtil;

/**
 * Created by li on 2018/4/10
 * description：
 * author：guojlli
 */

public class DataAllbean {
//    Map<String,Object> map=new HashMap<>();
//        map.put("userName","test");
//        map.put("password","E10ADC3949BA59ABBE56E057F20F883E");
//        map.put("objId","1486535776800");
//        map.put("objType","1");
//        map.put("baseDate","2017-03");
//        map.put("eleAccountId","54");
//        map.put("dateType","1");
        public String userName;
        public String password;
        public long objId;
        public int objType=1;
        public String baseData;
        public int eleAccountId;
        public int energyledgerId;
        //设置默认月份
        int mYear;
        int mMonth;


    public String getUserName() {
        userName = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_USERNAME);
        return userName;
    }


    public String getPassword() {
        password = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORDMD5);
        return password;
    }


    public long getObjId() {
        long energyledgerId = ACache.getInstance().getAsObject(Constants.CACHE_OPS_OBJID);
        if(objId==0){
            return energyledgerId;
        }
        return objId;
    }


    public int getObjType() {
        int objTypeDefault=ACache.getInstance().getAsObject(Constants.CACHE_OPS_OBJTYPE);
        Log.i("objTypeDefault", "getObjType: "+objTypeDefault);
        if(objTypeDefault==0){
            return objType;
        }
        return objTypeDefault;
    }


    public String getBaseData() {
       String baseDataDegault=ACache.getInstance().getAsString(Constants.CACHE_OPS_BASEDATE);
        if(baseDataDegault==null){
            return DateUtil.getCurrentYear()+"-"+DateUtil.getCurrentMonth();
        }
        return baseDataDegault;
    }
    //变压器温度比例（同页面有两个时间选择，所以单开一个）
    public String getTransformerTemperatureBaseData() {
        String baseDataDegault=ACache.getInstance().getAsString(Constants.CACHE_OPS_TRANSFORMERTEMPERATUREBASEDATE);
        if(baseDataDegault==null){
            return DateUtil.getCurrentYear()+"";
        }
        return baseDataDegault;
    }
    //变压器使用容量比例（同页面有两个时间选择，所以单开一个）
    public String getTransformerVolumeBaseData() {
        String baseDataDegault=ACache.getInstance().getAsString(Constants.CACHE_OPS_TRANSFORMERVOLUMEBASEDATE);
        if(baseDataDegault==null){
            return DateUtil.getCurrentYear()+"";
        }
        return baseDataDegault;
    }

    public int getEleAccountId() {
        int eleAccountIdDefault= ACache.getInstance().getAsObject(Constants.CACHE_OPS_ELEACCOUNTID);;
        if(eleAccountId==0){
            return eleAccountIdDefault;
        }
        return eleAccountId;
    }
    public long getEnergyledgerId() {
        long eleAccountIdDefault = ACache.getInstance().getAsObject(Constants.CACHE_OPS_ENERGYLEDGERID);
        if (energyledgerId == 0) {
            return eleAccountIdDefault;
        }
        return eleAccountId;

    }

}
