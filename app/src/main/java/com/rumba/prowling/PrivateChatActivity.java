package com.rumba.prowling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumba.adapters.ListContactAdapter;
import com.rumba.adapters.PrivateChatAdapter;
import com.rumba.functions.DBFunctions;
import com.rumba.functions.UtilsFunctions;
import com.rumba.objects.Contact;
import com.rumba.objects.PrivateChat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateChatActivity extends AppCompatActivity {
    ArrayList<PrivateChat> contactsItems = new ArrayList<>();
    PrivateChatAdapter contactsItemsAdapter;
    ListView layoutLigaActivity;
    Button btnSend;
    EditText etxtSend;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String roomStr, uidIntent1, uidIntent2;
    TextView txtTitle;
    List<String> uidList;
    private UtilsFunctions utilsFunc = new UtilsFunctions();
    private DBFunctions dbFunc = new DBFunctions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privatechat);
        System.out.println("PrivateChatActivity class - activity_privatechat");
        layoutLigaActivity = (ListView)findViewById(R.id.linePrivateChat_list_view);
        btnSend = (Button) findViewById(R.id.btnSendMsgPrivate);
        etxtSend = (EditText) findViewById(R.id.etMsgPrivate);
        contactsItems.clear();
        contactsItemsAdapter = new PrivateChatAdapter(getApplicationContext(), contactsItems);
        uidIntent1 = getIntent().getStringExtra("uid1");
        uidIntent2 = getIntent().getStringExtra("uid2");
        roomStr = utilsFunc.getNameRoom(uidIntent1,uidIntent2);
        System.out.println("uid: " + uid + " - " + roomStr);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PrivateChat").whereEqualTo("room", roomStr).orderBy("datetime", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange document : snapshots.getDocumentChanges()) {
                            List<String> idsArrayStr = (List<String>) document.getDocument().get("ids");
                            System.out.println("resutl: " + document.getDocument().get("msg") + "ids: " + idsArrayStr.get(0));

                                    contactsItems.add(new PrivateChat(document.getDocument().get("msg").toString(), idsArrayStr.get(0).equals(uid) ? 1 : 0, Long.parseLong(document.getDocument().get("datetime").toString())));
                                    layoutLigaActivity.setAdapter(contactsItemsAdapter);
                                }

                    }
                });
        layoutLigaActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                System.out.println(contactsItems.get(pos).getMsg());
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                if(etxtSend.getText().toString().trim().length() != 0){
                    new AsyncTask<Void, Void, String>() {
                        @SuppressLint("WrongThread")
                        @Override
                        protected String doInBackground(Void... voids) {
                            String dateNow = "20181201000000";
                            try {
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


                                try {
                                    Date date = new java.util.Date((long)Long.parseLong(elements.text())*1000);
                                    @SuppressLint("SimpleDateFormat") DateFormat hourFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                    dateNow = hourFormat.format(date);
                                }catch (Exception e){
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
                            Map<String, Object> sendDB = new HashMap<>();
                            sendDB.put("msg", etxtSend.getText().toString());
                            sendDB.put("room", roomStr);
                            ArrayList<String> subjectsArrayList = new ArrayList<>();
                            subjectsArrayList.add(uid);
                            subjectsArrayList.add(uidIntent2);
                            sendDB.put("ids", subjectsArrayList);
                            sendDB.put("datetime", dateNow);


                            if(dbFunc.sendPrivateMsg(sendDB)){
                                Toast.makeText(PrivateChatActivity.this, "Error Send Msg Private",Toast.LENGTH_SHORT).show();
                            }else{
                                etxtSend.setText("");
                            }
                        }
                    }.execute();
                }
            }
        });
    };
}