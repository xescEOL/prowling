package com.rumba.prowling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.rumba.functions.DBFunctions;


public class InfoMsgActivity extends AppCompatActivity {
    private TextView etBack,txtName, txtDate, txtMsgChat;
    private ImageView imgProfile;
    private Button btnPrivateMessage, btnSeguir;
    private String uidIntent;
    private DBFunctions dbFunc = new DBFunctions();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomsg);
        System.out.println("InfoMsgActivity class - activity_infomsg");
        etBack = (TextView)findViewById(R.id.backText);
        txtName = (TextView)findViewById(R.id.txtName);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtMsgChat = (TextView)findViewById(R.id.txtMsgChat);
        imgProfile = (ImageView)findViewById(R.id.imgProfile);
        btnPrivateMessage = (Button)findViewById(R.id.btnPrivateMessage);
        btnSeguir = (Button)findViewById(R.id.btnSeguir);

        etBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uidIntent = getIntent().getStringExtra("uidIntent");
        String nameIntent = getIntent().getStringExtra("nameIntent");
        String msgIntent = getIntent().getStringExtra("msgIntent");
        String dateIntent = getIntent().getStringExtra("dateIntent");

        txtName.setText(nameIntent);
        txtDate.setText(dateIntent);
        txtMsgChat.setText(msgIntent);

        String thumb = "https://firebasestorage.googleapis.com/v0/b/prowling-rumba.appspot.com/o/IMG_Perfil%2F" + uidIntent + "_1.jpg?alt=media";
        Glide.with(getApplicationContext())
                .load(thumb)
                .into(imgProfile);

        btnPrivateMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InfoMsgActivity.this, PrivateChatActivity.class);
                intent.putExtra("uid1", uid);
                intent.putExtra("uid2", uidIntent);
                startActivity(intent);
            }
        });

        btnSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbFunc.insertFollowDB(uid, uidIntent);
            }
        });


    }
}
