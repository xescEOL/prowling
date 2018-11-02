package com.rumba.functions;

import android.content.Context;

import com.rumba.objects.userprofile;

import java.util.List;

public class UtilsFunctions {

    public int lastConnectionIndex(List<userprofile> list) {
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
}
