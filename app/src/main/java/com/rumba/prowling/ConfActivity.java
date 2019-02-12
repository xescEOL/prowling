package com.rumba.prowling;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;

public class ConfActivity extends Fragment {

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_conf, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ConfActivity class - activity_conf");
        Button btnlogout = (Button) view.findViewById(R.id.btnLogout);
        ImageView imgProf = (ImageView) view.findViewById(R.id.imgProfile);
        ImageView imgButEdit = (ImageView) view.findViewById(R.id.imgButtonEdit);
        imgProf.setImageResource(R.drawable.np);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + uid + "_1.jpg?alt=media")
                .into(imgProf);

        imgButEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getActivity()).clearDiskCache();
                    }
                }).start();
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    };
}