package com.rumba.objects;

public class Contact {
    private String uid, msg;
    private boolean tipo;
    private long date;

    public Contact(String uid, boolean tipo, String msg, long date){
        this.uid = uid;
        this.tipo = tipo;
        this.msg = msg;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
