package com.rumba.prowling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumba.adapters.PChatAdapter;
import com.rumba.functions.DBFunctions;
import com.rumba.functions.UtilsFunctions;
import com.rumba.objects.Contact;
import com.rumba.objects.LinePChat;
import com.rumba.objects.UserProfile;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class PChatActivity extends Fragment {
    ArrayList<LinePChat> listaPChatLine = new ArrayList<>();

    PChatAdapter itemsChatLine;
    ListView layoutPChatActivity;
    EditText etMsg;
    TextView lblKm;
    LinearLayout laySend, layKm;
    Button btnSend, btnKm;
    SeekBar barKm;
    private FirebaseAuth mAuth;
    String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    List<Double> myLocation = new ArrayList<Double>();
    int progressKm = 0;
    private DBFunctions dbFunc = new DBFunctions();
    private UtilsFunctions utilsFunc = new UtilsFunctions();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_pchat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("LocationActivity class - activity_location");
        layoutPChatActivity = (ListView) getActivity().findViewById(R.id.linePChat_list_view);
        btnSend = (Button) view.findViewById(R.id.btnSendMsg);
        btnKm = (Button) view.findViewById(R.id.btnKm);
        barKm = (SeekBar) view.findViewById(R.id.barKm);
        etMsg = (EditText) view.findViewById(R.id.etMsg);
        lblKm = (TextView) view.findViewById(R.id.lblKm);
        laySend = (LinearLayout) view.findViewById(R.id.layoutSend);
        layKm = (LinearLayout) view.findViewById(R.id.layoutKm);
        myLocation = utilsFunc.getCurrentLocation(getContext());
        mAuth = FirebaseAuth.getInstance();
        listaPChatLine.clear();
        itemsChatLine = new PChatAdapter(getContext(), listaPChatLine);

        System.out.println("dateeee: " + ServerValue.TIMESTAMP);

        CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("PChat");
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        List<Double> currentLocation = utilsFunc.getCurrentLocation(getContext());
        //GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(currentLocation.get(0), currentLocation.get(1)), 10000.11);
        GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(41.3818, 2.1685), 15);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String documentID, GeoPoint location) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("PChat").document(documentID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                boolean myMsg = false;
                                String uid = document.getId().split("_")[1];
                                if(uid.equals(myUid)){
                                    myMsg = true;
                                }
                                List<Double> locationData = (List<Double>) document.get("l");
                                listaPChatLine.add(new LinePChat(task.getResult().getData().get("Msg").toString(),task.getResult().getData().get("Name").toString(),uid,utilsFunc.calculateDistanceBetweenPoints(locationData.get(0),locationData.get(1), myLocation.get(0),myLocation.get(1)),myMsg));
                                //listaPChatLine.add(new LinePChat(task.getResult().getData().get("Msg").toString(),task.getResult().getData().get("Name").toString(),uid,utilsFunc.calculateDistanceBetweenPoints(locationData.get(0),locationData.get(1), 41.4667,2.2667),myMsg));

                                layoutPChatActivity.setAdapter(itemsChatLine);
                                scrollMyListViewToBottom();

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



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time =  utilsFunc.getDateHourMinuteSecNow();
                Map<String, Object> userDB = new HashMap<>();
                userDB.put("Name", name);
                userDB.put("Msg", etMsg.getText().toString());
                userDB.put("Km", progressKm);
                if(dbFunc.sendMsgDB(userDB, time + "_" + myUid)){
                    Toast.makeText(getContext(), "Error SignUp",Toast.LENGTH_SHORT).show();
                }else {
                    dbFunc.saveLocationMessage(getContext(), time + "_" + myUid);
                    etMsg.setText("");
                }
            }
        });

        lblKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laySend.setVisibility(View.GONE);
                layKm.setVisibility(View.VISIBLE);
            }
        });
        btnKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laySend.setVisibility(View.VISIBLE);
                layKm.setVisibility(View.GONE);
            }
        });

        barKm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progressKm = progresValue/10;
                btnKm.setText(progressKm + "KM");
                lblKm.setText(progressKm + "km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    };


    private void scrollMyListViewToBottom() {
        layoutPChatActivity.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                layoutPChatActivity.setSelection(itemsChatLine.getCount() - 1);
            }
        });
    }
}