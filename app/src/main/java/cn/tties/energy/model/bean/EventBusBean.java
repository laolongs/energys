package cn.tties.energy.model.bean;

import java.io.Serializable;

public class EventBusBean implements Serializable {

    public String kind;

    public Object obj;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public <T> T getObj() {
        return (T) obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
