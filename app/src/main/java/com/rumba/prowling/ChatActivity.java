package com.rumba.prowling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.rumba.objects.Contact;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Fragment {
    ArrayList<Contact> contactsItems = new ArrayList<>();
    ListContactAdapter contactsItemsAdapter;
    ListView layoutLigaActivity;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView txtTitle;
    List<String> uidList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ChatActivity class - activity_chat");
        layoutLigaActivity = (ListView) getActivity().findViewById(R.id.liga_list_view);
        txtTitle = (TextView)getActivity().findViewById(R.id.textList);
        uidList = new ArrayList<>();

        contactsItems.clear();
        contactsItemsAdapter = new ListContactAdapter(getContext(), contactsItems);
        System.out.println("uid: " + uid);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PrivateChat").whereArrayContains("ids", uid).orderBy("datetime", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> group = (List<String>) document.get("ids");
                                String otheruid;
                                boolean add = true;
                                if(group.get(0).equals(uid)) {
                                    otheruid = group.get(1);
                                    if (uidList.contains(group.get(1)))
                                        add = false;
                                }else{
                                    otheruid = group.get(0);
                                    if (uidList.contains(group.get(0)))
                                        add = false;
                                }
                                if(add) {
                                    if(group.get(0).equals(uid))
                                        uidList.add(group.get(1));
                                    else
                                        uidList.add(group.get(0));

                                    contactsItems.add(new Contact(otheruid, group.get(0).equals(uid), document.getData().get("msg").toString(), Long.parseLong(document.getData().get("datetime").toString())));
                                    layoutLigaActivity.setAdapter(contactsItemsAdapter);
                                }
                            }
                        } else {
                            System.out.println("Error getting documents: ");
                        }
                    }
                });
        layoutLigaActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                System.out.println(contactsItems.get(pos).getUid());
                Intent intent = new Intent(getActivity(), PrivateChatActivity.class);
                intent.putExtra("uid1", uid);
                intent.putExtra("uid2", contactsItems.get(pos).getUid());
                startActivity(intent);
            }
        });
    };
}