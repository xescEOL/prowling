package com.rumba.objects;

public class PrivateChat {
    private String msg;
    private int tipo;
    private long date;

    public PrivateChat(String msg, int tipo, long date){
        this.tipo = tipo;
        this.msg = msg;
        this.date = date;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
