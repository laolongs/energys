package cn.tties.energy.model.result;

import java.io.Serializable;

/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class Versionbean  implements Serializable{
    /**
     * date : 20170707
     * minVersionCode : 0
     * description : 无更新版本
     * versionName : 1.0.0
     * versionCode : 0
     * url : 无更新版本
     */

    private String date;
    private int minVersionCode;
    private String description;
    private String versionName;
    private int versionCode;
    private String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMinVersionCode() {
        return minVersionCode;
    }

    public void setMinVersionCode(int minVersionCode) {
        this.minVersionCode = minVersionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
