package cn.tties.energy.model.httputils.params;


import cn.tties.energy.model.httputils.ClinetRequestParams;

/**
 * Created by li on 2018/3/13
 * description：
 * author：guojlli
 */

public class LoginParams extends ClinetRequestParams {

    public static final String INTERFACE = "login.htm";

    private Integer type;

    private Integer version;

    public Integer getType() {
        if (version == null) {
            return 3;
        }
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getVersion() {
        if (version == null) {
            return android.os.Build.VERSION.SDK_INT;
        }
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}