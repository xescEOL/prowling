package com.rumba.objects;

import android.location.Location;

import com.google.firebase.firestore.GeoPoint;

public class UserProfile {

    String id, name, smallDesc;
    String localitation;
    long lastCon;
    int numphoto;

    public UserProfile(){}

    public UserProfile(String id, String name, String location, String smallDesc, String numphoto, long lastCon) {
        this.id = id;
        this.name = name;
        this.localitation = location;
        this.smallDesc = smallDesc;
        this.numphoto = Integer.parseInt(numphoto);
        this.lastCon = lastCon;
    }

    public UserProfile(String all) {

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

    public int getNumphoto() {
        return numphoto;
    }

    public void setNumphoto(int numphoto) {
        this.numphoto = numphoto;
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
