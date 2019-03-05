package com.rumba.functions;

import android.content.Context;

import com.google.firebase.database.ServerValue;
import com.rumba.objects.LinePChat;
import com.rumba.objects.UserProfile;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public Date getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (int i = 0; i <tags.length; i++) {
            elements = elements.select(tags[i]);
        }
        return new java.util.Date((long)Long.parseLong(elements.text() + "000")*1000);
    }

    public String getDateHourMinuteSecNow() {
        String returnString = "201812010000";
        try {
            Date date = getTime();
            DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            returnString = hourFormat.format(date);
        }catch (Exception e){
            System.out.println("doc " + e.toString());
            Date date = new Date();
            DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            returnString = hourFormat.format(date);
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
        //System.out.println("Distancia de " + x1 + " " + x2 + " " + y1 + " " + y2 + " : " + dist);
        return dist;
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
