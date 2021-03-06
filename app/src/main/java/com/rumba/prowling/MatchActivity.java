package com.rumba.prowling;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.rumba.functions.*;
import com.rumba.objects.*;

import com.google.firebase.firestore.FirebaseFirestore;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;

import java.io.File;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatchActivity extends Fragment {
    private DBFunctions dbFunc = new DBFunctions();
    private UtilsFunctions utilsFunc = new UtilsFunctions();
    private FirebaseAuth mAuth;
    ImageView matchIMG, photoRight, photoLeft, buttonLike, buttonDislike, buttonSuperLike;
    TextView numPhoto, nameProfile, smallDesc;
    LinearLayout photoMatch, buttonsMatch, progressBar;
    List<UserProfile> listUsers = new ArrayList<>(30);
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    List<String> usersHistory = new ArrayList<String>();
    int indexElementCurrentUser;


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
        LinearLayout linear = (LinearLayout)getActivity().findViewById(R.id.linearLayoutMatch);
        System.out.println("MatchActivity class - activity_match");


        photoRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (numPhoto.getText().toString()) {
                    case "1/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/6");
                        break;
                    case "1/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/5");
                        break;
                    case "1/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/4");
                        break;
                    case "1/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/3");
                        break;
                        case "1/2":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/2");
                        break;
                    case "1/1":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/1");
                        break;

                    case "2/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/6");
                        break;
                    case "2/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/5");
                        break;
                    case "2/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/4");
                        break;
                    case "2/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/3");
                        break;
                    case "2/2":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/2");
                        break;

                    case "3/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/6");
                        break;
                    case "3/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/5");
                        break;
                    case "3/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/4");
                        break;
                    case "3/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/3");
                        break;

                    case "4/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_5.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("5/6");
                        break;
                    case "4/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_5.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("5/5");
                        break;
                    case "4/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/4");
                        break;

                    case "5/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_6.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("6/6");
                        break;
                    case "5/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/5");
                        break;

                    case "6/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/6");
                        break;
                        default:
                            Glide.with(getContext() /* context */)
                                    .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                    .into(matchIMG);
                            numPhoto.setText("1/1");
                            break;
                }
            }
        });

        photoLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (numPhoto.getText().toString()) {
                    case "1/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_6.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("6/6");
                        break;
                    case "1/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_5.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("5/5");
                        break;
                    case "1/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/4");
                        break;
                    case "1/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/3");
                        break;
                    case "1/2":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/2");
                        break;
                    case "1/1":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/1");
                        break;

                    case "2/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/6");
                        break;
                    case "2/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/5");
                        break;
                    case "2/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/4");
                        break;
                    case "2/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/3");
                        break;
                    case "2/2":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/2");
                        break;

                    case "3/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/6");
                        break;
                    case "3/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/5");
                        break;
                    case "3/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/4");
                        break;
                    case "3/3":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_2.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("2/3");
                        break;

                    case "4/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/6");
                        break;
                    case "4/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/5");
                        break;
                    case "4/4":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_3.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("3/4");
                        break;

                    case "5/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/6");
                        break;
                    case "5/5":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_4.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("4/5");
                        break;

                    case "6/6":
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_5.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("5/6");
                        break;
                    default:
                        Glide.with(getContext() /* context */)
                                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                .into(matchIMG);
                        numPhoto.setText("1/1");
                        break;
                }
            }
        });

        buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uidMatch = listUsers.get(indexElementCurrentUser).getId().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(uidMatch).collection("Matches").document(uid);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                try {
                                    if (Integer.parseInt(task.getResult().getData().get("Tipo").toString()) == 1) {

                                        List usersID = new ArrayList();
                                        usersID.add(uid);
                                        usersID.add(uidMatch);
                                        Map<String, Object> matchDB = new HashMap<>();
                                        matchDB.put("lastUpdate", utilsFunc.getDateHourMinuteSecNow());
                                        matchDB.put("usersID", usersID);

                                        dbFunc.matchCreateDB(matchDB);
                                        System.out.println("MAAAATCH");
                                        Toast.makeText(getContext(), "MAAAAATCH!!!!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }});
                LinearLayout linear = (LinearLayout)getActivity().findViewById(R.id.linearLayoutMatch);
                String hex = Integer.toHexString((int) 145);
                linear.setBackgroundColor(Color.parseColor("#" + hex + "4CC700"));
                ImageView matchLikeDislikeIMG = (ImageView)getActivity().findViewById(R.id.imgMatchTransp);
                matchLikeDislikeIMG.setImageResource(R.drawable.match_agree_nofx);
                matchLikeDislikeIMG.setVisibility(View.VISIBLE);
                Map<String, Object> docData = new HashMap<>();
                docData.put("Tipo", 1);
                docData.put("Date", utilsFunc.getDateHourMinuteSecNow());
                // Add a new document (asynchronously) in collection "cities" with id "LA"
                FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                db2.collection("users").document(uid).collection("Matches").document(listUsers.get(indexElementCurrentUser).getId().toString()).set(docData);
                listUsers.remove(indexElementCurrentUser);
                indexElementCurrentUser = utilsFunc.lastConnectionIndex(listUsers);
                //pausamos 4 decimas para que se vea lo que hemos escogido (foto con marca de agua roja)
                new CountDownTimer(400, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        if (listUsers.size() > 0) {
                            smallDesc.setText(listUsers.get(indexElementCurrentUser).getSmallDesc().toString());
                            nameProfile.setText(listUsers.get(indexElementCurrentUser).getName().toString());
                            numPhoto.setText("1/"+listUsers.get(indexElementCurrentUser).getNumphoto()+"");
                            Glide.with(getContext())
                                    .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                    .into(matchIMG);
                        } else {
                            photoMatch.setVisibility(View.GONE);
                            buttonsMatch.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        LinearLayout linear = (LinearLayout) getActivity().findViewById(R.id.linearLayoutMatch);
                        ImageView matchLikeDislikeIMG = (ImageView) getActivity().findViewById(R.id.imgMatchTransp);
                        linear.setBackgroundColor(Color.parseColor("#00000000"));
                        matchLikeDislikeIMG.setVisibility(View.GONE);
                    }
                }.start();
            }});

        buttonDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uidMatch = listUsers.get(indexElementCurrentUser).getId().toString();
                LinearLayout linear = (LinearLayout)getActivity().findViewById(R.id.linearLayoutMatch);
                String hex = Integer.toHexString((int) 145);
                linear.setBackgroundColor(Color.parseColor("#" + hex + "C60000"));
                ImageView matchLikeDislikeIMG = (ImageView)getActivity().findViewById(R.id.imgMatchTransp);
                matchLikeDislikeIMG.setImageResource(R.drawable.match_noagree_nofx);
                matchLikeDislikeIMG.setVisibility(View.VISIBLE);
                Map<String, Object> docData = new HashMap<>();
                docData.put("Tipo", 0);
                docData.put("Date", utilsFunc.getDateHourMinuteSecNow());
                // Add a new document (asynchronously) in collection "cities" with id "LA"
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(uid).collection("Matches").document(uidMatch).set(docData);
                listUsers.remove(indexElementCurrentUser);
                indexElementCurrentUser = utilsFunc.lastConnectionIndex(listUsers);
                //pausamos 4 decimas para que se vea lo que hemos escogido (foto con marca de agua verde)
                new CountDownTimer(400, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }
                    public void onFinish() {
                        if(listUsers.size()>0) {
                            Glide.with(getContext())
                                    .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                    .into(matchIMG);
                            smallDesc.setText(listUsers.get(indexElementCurrentUser).getSmallDesc().toString());
                            nameProfile.setText(listUsers.get(indexElementCurrentUser).getName().toString());
                            numPhoto.setText("1/"+listUsers.get(indexElementCurrentUser).getNumphoto()+"");
                        }else{
                            photoMatch.setVisibility(View.GONE);
                            buttonsMatch.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        LinearLayout linear = (LinearLayout)getActivity().findViewById(R.id.linearLayoutMatch);
                        ImageView matchLikeDislikeIMG = (ImageView)getActivity().findViewById(R.id.imgMatchTransp);
                        linear.setBackgroundColor(Color.parseColor("#00000000"));
                        matchLikeDislikeIMG.setVisibility(View.GONE);
                    }
                }.start();
            }});

        //dbFunc.SaveLocation(getContext());
// OJOOOOOOOOOOOOOOOOO  LA FUNCIO ANTERIOR BORRA LES ALTRES DADES!!! SHA DE MIRAR!!!!!! <-------



        db.collection("users").document(uid).collection("Matches")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            usersHistory.add(uid);
                            //Añadimos los usuarios que ya hemos valorado anteriormente
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    usersHistory.add(document.getId().toString());
                            }
                            System.out.println(" finish list history users");

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
                                                    //Solo valoramos a los usuarios que no los hemos valorado anteriormente
                                                    if(!usersHistory.contains(task.getResult().getId())){
                                                        listUsers.add(new UserProfile(task.getResult().getId(),task.getResult().getData().get("Name").toString(), task.getResult().getData().get("l").toString(), task.getResult().getData().get("SmallDesc").toString(),task.getResult().getData().get("NumPhotos").toString(),Long.parseLong(task.getResult().getData().get("LastConnection").toString())));
                                                        indexElementCurrentUser = listUsers.size()-1;
                                                        System.out.println("DocumentSnapshot data: " + listUsers.get(indexElementCurrentUser).getId().toString());
                                                        smallDesc.setText(listUsers.get(indexElementCurrentUser).getSmallDesc().toString());
                                                        nameProfile.setText(listUsers.get(indexElementCurrentUser).getName().toString());
                                                        numPhoto.setText("1/"+listUsers.get(indexElementCurrentUser).getNumphoto()+"");
                                                        try {
                                                            Glide.with(getContext())
                                                                    .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listUsers.get(indexElementCurrentUser).getId() + "_1.jpg?alt=media")
                                                                    .into(matchIMG);
                                                        }catch (Exception e){

                                                        }
                                                        photoMatch.setVisibility(View.VISIBLE);
                                                        buttonsMatch.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
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






                        } else {
                            System.out.println("Error getting documents: "+ task.getException());
                        }
                    }
                });






    };

}