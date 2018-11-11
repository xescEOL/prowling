package com.rumba.objects;

public class Contact {
    private String name;
    private int tipo;

    public Contact(String name, int tipo){
        this.name = name;
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
