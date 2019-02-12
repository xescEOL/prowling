package com.rumba.objects;

public class LinePChat {
    private String msg, name, uid;
    private Double km;
    private boolean myMsg;

    public LinePChat(String msg, String name, String uid, Double km, boolean myMsg){
        this.msg = msg;
        this.name = name;
        this.uid = uid;
        this.km = km;
        this.myMsg = myMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public boolean getMyMsg() {
        return myMsg;
    }

    public void setMyMsg(boolean myMsg) {
        this.myMsg = myMsg;
    }
}
