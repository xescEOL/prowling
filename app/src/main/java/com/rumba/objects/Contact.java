package com.rumba.objects;

public class Contact {
    private String name, imageThumb;
    private int tipo;

    public Contact(String name, String imageThumb, int tipo){
        this.name = name;
        this.imageThumb = imageThumb;
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getimageThumb() {
        return imageThumb;
    }

    public void setimageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
