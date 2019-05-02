package com.rumba.objects;

public class LinePChat implements Comparable {
    private String msg, name, uid;
    private Double km;
    private long date;
    private boolean myMsg;
    private int color;

    public LinePChat(String msg, String name, String uid, Double km, boolean myMsg, long date, int color){
        this.msg = msg;
        this.name = name;
        this.uid = uid;
        this.km = km;
        this.myMsg = myMsg;
        this.date = date;
        this.color = color;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean getMyMsg() {
        return myMsg;
    }

    public void setMyMsg(boolean myMsg) {
        this.myMsg = myMsg;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int compareTo(Object o) {
        long compareage=((LinePChat)o).getDate();
        /* For Ascending order*/
        return (int)(this.getDate()-compareage);
    }

}
