package com.rumba.prowling;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.rumba.adapters.PChatAdapter;
import com.rumba.functions.DBFunctions;
import com.rumba.functions.Prowling;
import com.rumba.functions.UtilsFunctions;
import com.rumba.objects.LinePChat;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.GeoQueryEventListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PChatActivity extends Fragment {
    ArrayList<LinePChat> listaPChatLine = new ArrayList<>();

    PChatAdapter itemsChatLine;
    ListView listViewPChatActivity;
    EditText etMsg;
    TextView lblKm, nameMencionar;
    LinearLayout laySend, layKm, layoutMencionar;
    Button btnSend, btnKm;
    ImageButton btnMencionarExit;
    SeekBar barKm;
    private FirebaseAuth mAuth;
    String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    String citeUid = "";
    int userColorName = 0;
    List<Double> myLocation = new ArrayList<Double>();
    ImageView imgLoading;
    int progressKm = 0;
    private DBFunctions dbFunc = new DBFunctions();
    private UtilsFunctions utilsFunc = new UtilsFunctions();
    private View layout;
    private int positionListTap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_pchat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("LocationActivity class - activity_location");
        listViewPChatActivity = (ListView) getActivity().findViewById(R.id.linePChat_list_view);
        btnSend = (Button) view.findViewById(R.id.btnSendMsg);
        btnKm = (Button) view.findViewById(R.id.btnKm);
        btnMencionarExit = (ImageButton) view.findViewById(R.id.btnMencionarExit);
        barKm = (SeekBar) view.findViewById(R.id.barKm);
        etMsg = (EditText) view.findViewById(R.id.etMsg);
        lblKm = (TextView) view.findViewById(R.id.lblKm);
        nameMencionar = (TextView)view.findViewById(R.id.txtMencionarName);
        laySend = (LinearLayout) view.findViewById(R.id.layoutSend);
        layKm = (LinearLayout) view.findViewById(R.id.layoutKm);
        imgLoading = (ImageView) view.findViewById(R.id.imgLoading);
        layoutMencionar = (LinearLayout) view.findViewById(R.id.layoutMencionar);
        myLocation = utilsFunc.getCurrentLocation(getContext());
        mAuth = FirebaseAuth.getInstance();
        listaPChatLine.clear();
        itemsChatLine = new PChatAdapter(getContext(), listaPChatLine);

        if(((Prowling) getActivity().getApplicationContext()).getUserName() != null){
            name = ((Prowling) getActivity().getApplicationContext()).getUserName();
        }else{
            name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            ((Prowling) getActivity().getApplicationContext()).setUserName(name);
        }
        if(((Prowling) getActivity().getApplicationContext()).getColorName() != 0){
            userColorName = ((Prowling) getActivity().getApplicationContext()).getColorName();
        }else{
            DocumentReference docRef = db.collection("users").document(myUid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            try {
                                if(task.getResult().getData().get("ColorName") != null){
                                    userColorName = task.getResult().getLong("ColorName").intValue();
                                    ((Prowling) getActivity().getApplicationContext()).setColorName(userColorName);
                                }else{
                                    userColorName = 0;
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }});
        }

        Glide.with(getContext())
                .load(R.drawable.loadinggif)
                .into(imgLoading);

        LayoutInflater inflater = (LayoutInflater) PChatActivity.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout (no need for root id, using entire layout)
        layout = inflater.inflate(R.layout.popup_pchattap,null);
        float density = PChatActivity.this.getResources().getDisplayMetrics().density;
// create a focusable PopupWindow with the given layout and correct size
        final PopupWindow pw = new PopupWindow(layout, (int)density*240, (int)density*430, true);

        CollectionReference geoFirestoreRef = FirebaseFirestore.getInstance().collection("PChat");
        GeoFirestore geoFirestore = new GeoFirestore(geoFirestoreRef);
        List<Double> currentLocation = utilsFunc.getCurrentLocation(getContext());
        GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(currentLocation.get(0), currentLocation.get(1)), 15);
        //GeoQuery geoQuery = geoFirestore.queryAtLocation(new GeoPoint(41.47978, 2.3188), 15);
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
                                long dateElement = Long.parseLong(document.getId().split("_")[0]);
                                if(uid.equals(myUid)){
                                    myMsg = true;
                                }
                                int colorName = 0;
                                if(task.getResult().getLong("ColorName") != null){
                                    colorName = task.getResult().getLong("ColorName").intValue();
                                }
                                List<Double> locationData = (List<Double>) document.get("l");
                                listaPChatLine.add(new LinePChat(task.getResult().getData().get("Msg").toString(),task.getResult().getData().get("Name").toString(),uid,utilsFunc.calculateDistanceBetweenPoints(locationData.get(0),locationData.get(1), myLocation.get(0),myLocation.get(1)),myMsg,dateElement, colorName));
                                //listaPChatLine.add(new LinePChat(task.getResult().getData().get("Msg").toString(),task.getResult().getData().get("Name").toString(),uid,utilsFunc.calculateDistanceBetweenPoints(locationData.get(0),locationData.get(1), 41.4667,2.2667),myMsg));

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

            @SuppressLint("DefaultLocale")
            @Override
            public void onKeyMoved(String documentID, GeoPoint location) {
                System.out.println(String.format("Document %s moved within the search area to [%f,%f]", documentID, location.getLatitude(), location.getLongitude()));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("Ordenando lista");
                Collections.sort(listaPChatLine);
                listViewPChatActivity.setAdapter(itemsChatLine);
                imgLoading.setVisibility(View.GONE);
            }

            @Override
            public void onGeoQueryError(Exception exception) {
                System.err.println("There was an error with this query: " + exception.getLocalizedMessage());
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if(etMsg.getText().toString().toString().trim().length() != 0){
                    //etMsg.setBackgroundColor(Color.GREEN);
                    etMsg.setEnabled(false);
                    etMsg.setTextColor(Color.GRAY);
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                            String dateNow = "20181201000000";
                            try {
                                String url = "https://time.is/Unix_time_now";
                                Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
                                String[] tags = new String[]{
                                        "div[id=time_section]",
                                        "div[id=clock0_bg]"
                                };
                                Elements elements = doc.select(tags[0]);
                                for (int i = 0; i < tags.length; i++) {
                                    elements = elements.select(tags[i]);
                                }


                                try {
                                    Date date = new java.util.Date((long) Long.parseLong(elements.text()) * 1000);
                                    @SuppressLint("SimpleDateFormat") DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                    dateNow = hourFormat.format(date);
                                } catch (Exception e) {
                                    System.out.println("doc " + e.toString());
                                    Date date = new Date();
                                    @SuppressLint("SimpleDateFormat") DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                    dateNow = hourFormat.format(date);
                                }
                                return dateNow;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            return dateNow;
                        }

                        @Override
                        protected void onPostExecute(String dateNow) {
                            Map<String, Object> userDB = new HashMap<>();
                            userDB.put("Name", name);
                            userDB.put("Msg", etMsg.getText().toString());
                            userDB.put("Km", progressKm);
                            userDB.put("ColorName", userColorName);
                            if(!citeUid.equals("")){
                                userDB.put("Cite", citeUid);
                            }
                            if (dbFunc.sendMsgDB(userDB, dateNow + "_" + myUid)) {
                                Toast.makeText(getContext(), "Error send message", Toast.LENGTH_SHORT).show();
                                etMsg.setEnabled(true);
                            } else {
                                dbFunc.saveLocationMessage(getContext(), dateNow + "_" + myUid);
                                //etMsg.setBackgroundColor(Color.TRANSPARENT);
                                etMsg.setEnabled(true);
                                etMsg.setTextColor(getResources().getColor(R.color.colorGris));
                                etMsg.setText("");
                            }
                        }
                    }.execute();
                }
            }
        });
        
        listViewPChatActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
                System.out.println("List " + position);
                //if(!listaPChatLine.get(position).getUid().equals(myUid)) {
                    pw.dismiss();
                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                    ((TextView) layout.findViewById(R.id.txtNamePop)).setText( listaPChatLine.get(position).getName());
                    ((TextView) layout.findViewById(R.id.txtMsgPop)).setText( listaPChatLine.get(position).getMsg());
                    Glide.with(getContext())
                            .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + listaPChatLine.get(position).getUid() + "_1.jpg?alt=media")
                            .into(((ImageView)layout.findViewById(R.id.imgPerfilPop)));
                    positionListTap = position;
               // }
            }
        });

        //POPUP: Sortir si es prem fora de la finestra
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });
        pw.setOutsideTouchable(true);

        //POPUP: Sortir si es prem el buto sortir
        ((Button) layout.findViewById(R.id.btnClose)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pw.dismiss();
            }
        });

        ((Button) layout.findViewById(R.id.btnProfilePop)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoMsgActivity.class);
                intent.putExtra("uidIntent", listaPChatLine.get(positionListTap).getUid());
                intent.putExtra("nameIntent", listaPChatLine.get(positionListTap).getName());
                intent.putExtra("dateIntent", utilsFunc.dateLongToDate(listaPChatLine.get(positionListTap).getDate()));
                intent.putExtra("kmIntent", listaPChatLine.get(positionListTap).getKm());
                intent.putExtra("msgIntent", listaPChatLine.get(positionListTap).getMsg());
                startActivity(intent);
            }
        });

        ((Button) layout.findViewById(R.id.btnPrivatePop)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivateChatActivity.class);
                intent.putExtra("uid1", myUid);
                intent.putExtra("uid2", listaPChatLine.get(positionListTap).getUid());
                startActivity(intent);
            }
        });

        ((Button) layout.findViewById(R.id.btnMencionarPop)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                citeUid = listaPChatLine.get(positionListTap).getUid();
                nameMencionar.setText("@" + listaPChatLine.get(positionListTap).getName());
                layoutMencionar.setVisibility(View.VISIBLE);
                pw.dismiss();
            }
        });

        btnMencionarExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citeUid = "";
                nameMencionar.setText("@");
                layoutMencionar.setVisibility(View.GONE);
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
        listViewPChatActivity.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                listViewPChatActivity.setSelection(itemsChatLine.getCount() - 1);
            }
        });
    }
}