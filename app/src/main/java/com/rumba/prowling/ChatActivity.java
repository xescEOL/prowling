package com.rumba.prowling;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rumba.adapters.ListContactAdapter;
import com.rumba.objects.Contact;

import java.util.ArrayList;

public class ChatActivity extends Fragment {
    ArrayList<Contact> listaPartidosLiga = new ArrayList<>();
    ListContactAdapter itemsListaLiga;
    ListView layoutLigaActivity;

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

        itemsListaLiga = new ListContactAdapter(getContext(), listaPartidosLiga);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("contacts").whereArrayContains("usersID", "adeu").orderBy("lastUpdate", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println("document.getId()" + document.getData() + " - " + document.getId());
                                listaPartidosLiga.add(new Contact(document.get("lastUpdate").toString(),3));
                                layoutLigaActivity.setAdapter(itemsListaLiga);
                            }
                        } else {
                            System.out.println("Error getting documents: ");
                        }
                    }
                });
        layoutLigaActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                System.out.println(listaPartidosLiga.get(pos).getName());
            }
        });
    };
}