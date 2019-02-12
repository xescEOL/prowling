package com.rumba.functions;

import android.content.Context;

import com.rumba.objects.UserProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class UtilsFunctions {
    static double PI_RAD = Math.PI / 180.0;

    public int lastConnectionIndex(List<UserProfile> list) {
        int returnInteger = list.size()-1;
        long compare = 0;
        for(int x=0;x<list.size();x++) {
            if(list.get(x).getLastCon()>compare){
                compare=list.get(x).getLastCon();
                returnInteger = x;
            }
        }
        return returnInteger;
    }

    public String getDateHourMinuteSecNow() {
        String returnString = "201812010000";
        java.util.Date fecha = new Date();
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            returnString = hourFormat.format(date);
        }catch (Exception e){

        }
        return returnString;
    }

    public List<Double> getCurrentLocation(final Context cont) {
        final GPSTracker gps = new GPSTracker(cont);
        List<Double> retLoc = new ArrayList<Double>();

        retLoc.add(gps.latitude);
        retLoc.add(gps.longitude);
        return retLoc;
    }
    public Double calculateDistanceBetweenPoints(double x1,double y1,double x2,double y2) {

        double theta = y1 - y2;
        double dist = Math.sin(deg2rad(x1)) * Math.sin(deg2rad(x2)) + Math.cos(deg2rad(x1)) * Math.cos(deg2rad(x2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;


        System.out.println("Distancia de " + x1 + " " + x2 + " " + y1 + " " + y2 + " : " + dist);
        return dist;


        /*double dLat  = Math.toRadians((41.448106 - 41.3818));
        double dLong = Math.toRadians((2.2464521 - 2.1685));


        double a = haversin(dLat) + Math.cos(Math.toRadians(Math.toRadians(41.3818))) * Math.cos(Math.toRadians(41.448106)) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));*/


/*
        double dLat  = Math.toRadians((x2 - x1));
        double dLong = Math.toRadians((y2 - y1));


        double a = haversin(dLat) + Math.cos(Math.toRadians(Math.toRadians(x1))) * Math.cos(Math.toRadians(x2)) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

return (6371 * c);*/



    }
    private static final double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    /**
     * <p>This function converts radians to decimal degrees.</p>
     *
     * @param rad - the radian to convert
     * @return the radian converted to decimal degrees
     */
    private static final double rad2deg(double rad)
    {
        return (rad * 180 / Math.PI);
    }
}
