package com.rumba.prowling;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumba.adapters.ListContactAdapter;
import com.rumba.adapters.PrivateChatAdapter;
import com.rumba.functions.UtilsFunctions;
import com.rumba.objects.Contact;
import com.rumba.objects.PrivateChat;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatActivity extends AppCompatActivity {
    ArrayList<PrivateChat> contactsItems = new ArrayList<>();
    PrivateChatAdapter contactsItemsAdapter;
    ListView layoutLigaActivity;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView txtTitle;
    List<String> uidList;
    private UtilsFunctions utilsFunc = new UtilsFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privatechat);
        System.out.println("PrivateChatActivity class - activity_privatechat");
        layoutLigaActivity = (ListView)findViewById(R.id.linePrivateChat_list_view);
        contactsItems.clear();
        contactsItemsAdapter = new PrivateChatAdapter(getApplicationContext(), contactsItems);
        String uidIntent1 = getIntent().getStringExtra("uid1");
        String uidIntent2 = getIntent().getStringExtra("uid2");

        System.out.println("uid: " + uid + " - " + utilsFunc.getNameRoom(uidIntent1,uidIntent2));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PrivateChat").whereEqualTo("room", utilsFunc.getNameRoom(uidIntent1,uidIntent2)).orderBy("datetime", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("resutl: " + document.get("msg"));

                                    contactsItems.add(new PrivateChat(document.getData().get("msg").toString(), 1, Long.parseLong(document.getData().get("datetime").toString())));
                                    layoutLigaActivity.setAdapter(contactsItemsAdapter);
                                }
                        } else {
                            System.out.println("Error getting documents: ");
                        }
                    }
                });
        layoutLigaActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                System.out.println(contactsItems.get(pos).getMsg());
            }
        });
    };
}