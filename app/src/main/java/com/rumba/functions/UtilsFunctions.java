package com.rumba.functions;

import android.content.Context;

import com.rumba.objects.UserProfile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UtilsFunctions {

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
}
