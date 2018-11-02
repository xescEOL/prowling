package com.rumba.objects;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

public class userprofile {

    String id, name, smallDesc, photo1, photo2, photo3, photo4, photo5, photo6;
    String localitation;
    long lastCon;

    public userprofile(){}

    public userprofile(String id, String name, String location, String smallDesc, String photo1, String photo2, String photo3, String photo4, String photo5, String photo6, long lastCon) {
        this.id = id;
        this.name = name;
        this.localitation = location;
        this.smallDesc = smallDesc;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.photo3 = photo3;
        this.photo4 = photo4;
        this.photo5 = photo5;
        this.photo6 = photo6;
        this.lastCon = lastCon;
    }

    public userprofile(String all) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        this.smallDesc = smallDesc;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public void setPhoto5(String photo5) {
        this.photo5 = photo5;
    }

    public String getPhoto6() {
        return photo6;
    }

    public void setPhoto6(String photo6) {
        this.photo6 = photo6;
    }

    public String getLocalitation() {
        return localitation;
    }

    public void setLocalitation(String localitation) {
        this.localitation = localitation;
    }

    public long getLastCon() {
        return lastCon;
    }

    public void setLastCon(long lastCon) {
        this.lastCon = lastCon;
    }
}
