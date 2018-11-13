package com.rumba.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;

import com.rumba.functions.GPSTracker.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class DBFunctions extends Thread{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef;
    public ArrayList<String> listReturn = new ArrayList<String>();
    Bitmap bitmapRet;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    boolean exit;


        boolean errCode = true;

        public boolean signUpUserDB(Map<String, Object> hMap, String uID) {
            errCode = false;
            // Add a new document with a generated ID
            try {
                db.collection("users").document(uID)
                        .set(hMap);
            } catch (Exception e) {
                errCode = true;
            }
            return errCode;
        }

    public boolean matchCreateDB(Map<String, Object> hMap) {
        errCode = false;
        // Add a new document with a generated ID
        try {
            db.collection("contacts").document()
                    .set(hMap);
        } catch (Exception e) {
            errCode = true;
        }
        return errCode;
    }

        public boolean saveLocation(final Context cont) {
            final GPSTracker gps = new GPSTracker(cont);
            CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("users");
            GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
            errCode = false;
            try {
                geoFirestore.setLocation(user.getUid(), new GeoPoint(gps.latitude, gps.longitude), new GeoFirestore.CompletionListener() {
                    @Override
                    public void onComplete(Exception exception) {

                        if (exception == null) {
                            System.out.println("Location saved on server successfully!");
                        }
                    }
                });
            } catch (Exception e) {
                errCode = true;
            }
            return errCode;
        }


        public ArrayList<String> getUsersRadiusLoc(double lat, double lng, double radius) {
            CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("users");
            GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
            GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(lat, lng), radius);
            exit = false;
            geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String documentID, GeoPoint location) {
                    System.out.println(String.format("Document %s entered the search area at [%f,%f]", documentID, location.getLatitude(), location.getLongitude()));
                    listReturn.add(documentID);
                }

                @Override
                public void onKeyExited(String documentID) {
                    System.out.println(String.format("Document %s is no longer in the search area", documentID));
                }

                @Override
                public void onKeyMoved(String documentID, GeoPoint location) {
                    System.out.println(String.format("Document %s moved within the search area to [%f,%f]", documentID, location.getLatitude(), location.getLongitude()));
                }

                @Override
                public void onGeoQueryReady() {
                    System.out.println("All initial data has been loaded and events have been fired!");
                    exit = true;
                }

                @Override
                public void onGeoQueryError(Exception exception) {
                    System.err.println("There was an error with this query: " + exception.getLocalizedMessage());
                }
            });
            SystemClock.sleep(2000);
            //geoQuery.removeAllListeners();
            return listReturn;
        }
}
