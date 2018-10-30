package com.rumba.prowling;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.core.ApiFuture;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.api.core.ApiFutures;
import com.google.firestore.v1beta1.WriteResult;
import com.rumba.functions.*;
import com.rumba.objects.*;

import com.google.firebase.firestore.FirebaseFirestore;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class MatchActivity extends Fragment {
    private DBFunctions dbFunc = new DBFunctions();
    private FirebaseAuth mAuth;
    ImageView matchIMG, photoRight, photoLeft, buttonLike, buttonDislike, buttonSuperLike;
    TextView numPhoto, nameProfile, smallDesc;
    LinearLayout photoMatch, buttonsMatch, progressBar;
    String uIDmatch;
    List<userprofile> listUsers = new ArrayList<>(30);







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_match, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        matchIMG = (ImageView) getActivity().findViewById(R.id.imgMatch);
        photoRight = (ImageView) getActivity().findViewById(R.id.imgArrowRight);
        photoLeft = (ImageView) getActivity().findViewById(R.id.imgArrowLeft);
        numPhoto = (TextView) getActivity().findViewById(R.id.txtNumPhoto);
        buttonLike = (ImageView) getActivity().findViewById(R.id.imgButtonLike);
        buttonDislike = (ImageView) getActivity().findViewById(R.id.imgButtonDislike);
        buttonSuperLike = (ImageView) getActivity().findViewById(R.id.imgButtonSuper);
        nameProfile = (TextView) getActivity().findViewById(R.id.txtName);
        smallDesc = (TextView) getActivity().findViewById(R.id.txtSmallDesc);
        photoMatch = (LinearLayout) getActivity().findViewById(R.id.linearLayoutPhoto);
        buttonsMatch = (LinearLayout) getActivity().findViewById(R.id.linearLayoutButtons);
        progressBar = (LinearLayout) getActivity().findViewById(R.id.linearLayoutProgressMatch);


        Glide.with(this /* context */)
                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho1.jpg?alt=media&token=2b31300a-98ed-4f34-a55b-66e2fa4ba91f")
                .into(matchIMG);

        photoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numPhoto.getText().equals("1/6")) {
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho1.jpg?alt=media&token=2b31300a-98ed-4f34-a55b-66e2fa4ba91f")
                            .into(matchIMG);
                    numPhoto.setText("2/6");
                }else if (numPhoto.getText().equals("2/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho2.jpg?alt=media&token=eb5b06b3-72cc-4914-b139-6b75d6aaa5a4")
                            .into(matchIMG);
                    numPhoto.setText("3/6");
                }else if (numPhoto.getText().equals("3/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho3.jpg?alt=media&token=757b5741-dba4-41ca-b48c-9e781cff7944")
                            .into(matchIMG);
                    numPhoto.setText("4/6");
                }else if (numPhoto.getText().equals("4/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho5.jpg?alt=media&token=8a734d00-c165-4435-a2d8-a7005497b5e1")
                            .into(matchIMG);
                    numPhoto.setText("5/6");
                }else if (numPhoto.getText().equals("5/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho4.jpg?alt=media&token=a84fdad2-42bc-4adc-b898-724fe04750b4")
                            .into(matchIMG);
                    numPhoto.setText("6/6");
                }else if (numPhoto.getText().equals("6/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho6.jpg?alt=media&token=f8447a10-3846-487d-9ab2-59b172743ef4")
                            .into(matchIMG);
                    numPhoto.setText("1/6");
                }
            }
        });

        photoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numPhoto.getText().equals("3/6")) {
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho1.jpg?alt=media&token=2b31300a-98ed-4f34-a55b-66e2fa4ba91f")
                            .into(matchIMG);
                    numPhoto.setText("2/6");
                }else if (numPhoto.getText().equals("4/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho2.jpg?alt=media&token=eb5b06b3-72cc-4914-b139-6b75d6aaa5a4")
                            .into(matchIMG);
                    numPhoto.setText("3/6");
                }else if (numPhoto.getText().equals("5/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho3.jpg?alt=media&token=757b5741-dba4-41ca-b48c-9e781cff7944")
                            .into(matchIMG);
                    numPhoto.setText("4/6");
                }else if (numPhoto.getText().equals("6/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho5.jpg?alt=media&token=8a734d00-c165-4435-a2d8-a7005497b5e1")
                            .into(matchIMG);
                    numPhoto.setText("5/6");
                }else if (numPhoto.getText().equals("1/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho4.jpg?alt=media&token=a84fdad2-42bc-4adc-b898-724fe04750b4")
                            .into(matchIMG);
                    numPhoto.setText("6/6");
                }else if (numPhoto.getText().equals("2/6")){
                    Glide.with(getContext() /* context */)
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2Fpho6.jpg?alt=media&token=f8447a10-3846-487d-9ab2-59b172743ef4")
                            .into(matchIMG);
                    numPhoto.setText("1/6");
                }
            }
        });

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("Tipo", 1);
                docData.put("Date", "20181031");
// Add a new document (asynchronously) in collection "cities" with id "LA"
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document("HcMoRe2ej2h0IeLvj60nzKsHe7t1").collection("Matches").document(listUsers.get(listUsers.size()-1).getId().toString()).set(docData);
                System.out.println("guardando datooos!");
            }});

        buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }});

        dbFunc.SaveLocation(getContext());

        photoMatch.setVisibility(View.VISIBLE);
        buttonsMatch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("users");
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(42.111, 2.311), 10000.11);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String documentID, GeoPoint location) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(documentID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                listUsers.add(new userprofile(task.getResult().getId(),task.getResult().getData().get("Name").toString(), task.getResult().getData().get("l").toString(), task.getResult().getData().get("SmallDesc").toString(),task.getResult().getData().get("Photo1").toString(),task.getResult().getData().get("Photo2").toString(),task.getResult().getData().get("Photo3").toString(),task.getResult().getData().get("Photo4").toString(),task.getResult().getData().get("Photo5").toString(),task.getResult().getData().get("Photo6").toString()));
                                System.out.println("DocumentSnapshot data: " + listUsers.get(listUsers.size()-1).getId().toString());
                                smallDesc.setText(listUsers.get(listUsers.size()-1).getSmallDesc().toString());
                                nameProfile.setText(listUsers.get(listUsers.size()-1).getName().toString());
                            } else {
                                System.out.println("No such document");
                            }
                        } else {
                            System.out.println("get failed with "+task.getException());
                        }
                    }
                });

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

            }

            @Override
            public void onGeoQueryError(Exception exception) {
                System.err.println("There was an error with this query: " + exception.getLocalizedMessage());
            }
        });

    };



}