package cn.tties.energy.model.httputils;


import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;

import cn.tties.energy.common.Constants;
import cn.tties.energy.utils.ACache;
import cn.tties.energy.utils.EncryptUtil;
import cn.tties.energy.utils.StringUtil;
import okhttp3.Call;



public class ClinetRequestParams implements Serializable {

    private String userName;

    private String password;

    public String getUserName() {
        if (StringUtil.isEmpty(userName)) {
            userName = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_USERNAME);
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        if (StringUtil.isEmpty(password)) {
            password = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
            password = EncryptUtil.MD5Encrypt(password).toUpperCase();
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryptUtil.MD5Encrypt(password).toUpperCase();
    }

    public Call getParams() {
        String url = null;
        Call call=null;
//        OkHttpClient client=new OkHttpClient.Builder().build();
        HashMap<String,Object> map=new HashMap<>();
//        FormBody.Builder body=new FormBody.Builder();



        try {
            url = Constants.BASE_RUL + (String) this.getClass().getField("INTERFACE").get(null);

            Field[] field = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                String tempName = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].toString();
                if (type.contains("private")) {
                    Method m = this.getClass().getMethod("get" + tempName);
                    Object obj = m.invoke(this);
                    if (obj != null) {
//                        param.addParameter(name, obj);
                        map.put(name,obj);
                    }
                }
            }
            Method u = this.getClass().getMethod("getUserName");
            Object obju = u.invoke(this);
//            param.addParameter("userName", obju);
            map.put("userName",obju);
            Method p = this.getClass().getMethod("getPassword");
            Object objp = p.invoke(this);
//            param.addParameter("password", objp);
            map.put("password",objp);
//            Log.d("发送请求", param.toString());
//            for (String key:map.keySet()) {
//                body.add(key,map.get(key)+"");
//            }
//            Request request=new Request.Builder().url(url).post(body.build()).build();
//            Log.i("----request-------", "url: "+url.toString());
//            Log.i("----request-------", "getParams: "+request.toString());
//            call=client.newCall(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return OkHttpUtils.getInstance().getCall(url,map);
    }
}
