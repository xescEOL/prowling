package com.rumba.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.imperiumlabs.geofirestore.GeoFirestore;
import com.rumba.functions.GPSTracker.*;

import java.util.Map;

public class DBFunctions extends Thread{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef;
    Bitmap bitmapRet;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();



    boolean errCode = true;

    public boolean SignUpUserDB(Map<String, Object> hMap, String uID){
        errCode = false;
        // Add a new document with a generated ID
        try {
            db.collection("users").document(uID)
                    .set(hMap);
        }catch (Exception e){
            errCode = true;
        }
        return errCode;
    }

    public boolean SaveLocation(final Context cont){
        final GPSTracker gps = new GPSTracker(cont);
        CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("users");
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        errCode = false;
        try {
                geoFirestore.setLocation(user3.getUid(), new GeoPoint(gps.latitude, gps.longitude), new GeoFirestore.CompletionListener() {
            @Override
            public void onComplete(Exception exception) {

                if (exception == null){
                    System.out.println("Location saved on server successfully!");
                }
            }
        });
        }catch (Exception e){
            errCode = true;
        }
        return errCode;
    }



}
