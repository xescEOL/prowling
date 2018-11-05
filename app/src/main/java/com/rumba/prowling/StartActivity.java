package com.rumba.prowling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rumba.functions.UtilsFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class StartActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private UtilsFunctions utilsFunc = new UtilsFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        System.out.println("StartActivity class - activity_start");

        Button btnLogin = (Button)findViewById(R.id.login);
        Button btnSignUp = (Button)findViewById(R.id.signup);
        ImageView imgBG = (ImageView)findViewById(R.id.imageBG);

        final int numBG = changeBG(imgBG);


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("LastConnection", utilsFunc.getDateHourMinuteSecNow());
                    // Add a new document (asynchronously) in collection "cities" with id "LA"
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(user.getUid()).update(docData);
                    finish();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(StartActivity.this, "NO esta logueado.", Toast.LENGTH_SHORT).show();

                }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                intent.putExtra("BG", numBG);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                intent.putExtra("BG", numBG);
                startActivity(intent);
            }
        });

    }

    public int changeBG(ImageView imgBG){
        Random rand = new Random();

        int  n = rand.nextInt(9) + 1;

        switch (n){
            case 1: imgBG.setImageResource(R.drawable.backlogin1);
                n = R.drawable.backlogin1;
                break;
            case 2: imgBG.setImageResource(R.drawable.backlogin2);
                n = R.drawable.backlogin2;
                break;
            case 3: imgBG.setImageResource(R.drawable.backlogin3);
                n = R.drawable.backlogin3;
                break;
            case 4: imgBG.setImageResource(R.drawable.backlogin4);
                n = R.drawable.backlogin4;
                break;
            case 5: imgBG.setImageResource(R.drawable.backlogin5);
                n = R.drawable.backlogin5;
                break;
            case 6: imgBG.setImageResource(R.drawable.backlogin6);
                n = R.drawable.backlogin6;
                break;
            case 7: imgBG.setImageResource(R.drawable.backlogin7);
                n = R.drawable.backlogin7;
                break;
            case 8: imgBG.setImageResource(R.drawable.backlogin8);
                n = R.drawable.backlogin8;
                break;
            case 9: imgBG.setImageResource(R.drawable.backlogin9);
                n = R.drawable.backlogin9;
                break;
            default: imgBG.setImageResource(R.drawable.backlogin1);
                n = R.drawable.backlogin1;
                break;
        }
        return n;
    }



}

