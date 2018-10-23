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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rumba.functions.*;

import com.google.firebase.firestore.FirebaseFirestore;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MatchActivity extends Fragment {
    private DBFunctions dbFunc = new DBFunctions();
    private FirebaseAuth mAuth;
    ImageView matchIMG, photoRight, photoLeft;
    TextView numPhoto;
    LinearLayout photoMatch, buttonsMatch, progressBar;
    String uIDmatch;
    ArrayList<String> prova = new ArrayList<String>();





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

        dbFunc.SaveLocation(getContext());
        photoMatch.setVisibility(View.VISIBLE);
        buttonsMatch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);




        /*
        CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("my-collection");
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
const geoQuery = geoFirestore.query({
                center: new firebase.firestore.GeoPoint(10.38, 2.41),
                radius: 10.5
  });
        db.collection("users")
                .whereGreaterThan("Localizacion", new GeoPoint(0, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                prova.add(document.getId());
                            }
                            Toast.makeText(getContext(), prova.get(1), Toast.LENGTH_SHORT).show();
                            photoMatch.setVisibility(View.VISIBLE);
                            buttonsMatch.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            //Error
                        }
                    }
                });

*/

    };



}