package com.rumba.prowling;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Matrix;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditProfileActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etBirth;
    private EditText etSmallDesc;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 1;
    private int photonum;
    private StorageReference storageRef;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        System.out.println("AddPhotos class - activity_editprofile");

        ImageView imgPhoto1 = (ImageView)findViewById(R.id.imgProfile1);
        ImageView imgPhoto2 = (ImageView)findViewById(R.id.imgProfile2);
        ImageView imgPhoto3 = (ImageView)findViewById(R.id.imgProfile3);

        ImageView imgRemove1 = (ImageView)findViewById(R.id.imgRemove1);
        ImageView imgRemove2 = (ImageView)findViewById(R.id.imgRemove2);
        ImageView imgRemove3 = (ImageView)findViewById(R.id.imgRemove3);

        ImageView imgEdit1 = (ImageView)findViewById(R.id.imgEdit1);
        ImageView imgEdit2 = (ImageView)findViewById(R.id.imgEdit2);
        ImageView imgEdit3 = (ImageView)findViewById(R.id.imgEdit3);

        ImageView butSalir = (ImageView) findViewById(R.id.imgback);

        etName = (EditText) findViewById(R.id.etName);
        etSmallDesc = (EditText) findViewById(R.id.etShortDesc);


        etName.setText(name);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        try {
                            etSmallDesc.setText(task.getResult().getData().get("SmallDesc").toString());

                        } catch (Exception e) {
                        }
                    }
                }
            }});


        updateImgEditRemove();

        etName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("Name", etName.getText().toString());
                // Add a new document (asynchronously) in collection "cities" with id "LA"
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(uid).update(docData);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        etSmallDesc.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Map<String, Object> docData = new HashMap<>();
                docData.put("SmallDesc", etSmallDesc.getText().toString());
                // Add a new document (asynchronously) in collection "cities" with id "LA"
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(uid).update(docData);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        imgEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(EditProfileActivity.this).clearDiskCache();
                    }
                }).start();
                try {
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    }else{
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 2;
                try {
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    }else{
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 3;
                try {
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    }else{
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        butSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }});
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            ImageView imageView = (ImageView) findViewById(R.id.imgProfile1);
            ImageView imgRemove = (ImageView)findViewById(R.id.imgRemove1);
            ImageView imgEdit = (ImageView)findViewById(R.id.imgEdit1);
            TextView txtnum = (TextView)findViewById(R.id.txtNumPhoto1);
            switch (photonum) {
                case 1:  imageView = (ImageView) findViewById(R.id.imgProfile1);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove1);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit1);
                         txtnum = (TextView)findViewById(R.id.txtNumPhoto1);
                    break;
                case 2:  imageView = (ImageView) findViewById(R.id.imgProfile2);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove2);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit2);
                         txtnum = (TextView)findViewById(R.id.txtNumPhoto2);
                    break;
                case 3:  imageView = (ImageView) findViewById(R.id.imgProfile3);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove3);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit3);
                         txtnum = (TextView)findViewById(R.id.txtNumPhoto3);
                    break;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Matrix matrix = new Matrix();
            matrix.postRotate(getImageRotation(selectedImage, getApplicationContext()));
            Bitmap rotated = Bitmap.createBitmap(BitmapFactory.decodeFile(picturePath), 0, 0, BitmapFactory.decodeFile(picturePath).getWidth(), BitmapFactory.decodeFile(picturePath).getHeight(), matrix, true);
            if(rotated.getWidth() < rotated.getHeight()){
                imageView.setImageBitmap((Bitmap.createScaledBitmap(rotated, 600, Math.round(600 / (rotated.getWidth() / (float) rotated.getHeight())), false)));
            }else{
                imageView.setImageBitmap((Bitmap.createScaledBitmap(rotated, Math.round(600 / (rotated.getHeight() / (float) rotated.getWidth())), 600, false)));
            }
            txtnum.setTextColor(Color.WHITE);
            imgEdit.setVisibility(View.GONE);
            imgRemove.setVisibility(View.VISIBLE);
            storageRef = FirebaseStorage.getInstance().getReference();
            // Create a reference to "mountains.jpg"
            StorageReference mountainsRef = storageRef.child("/IMG_Perfil/" + uid + "_" + photonum + ".jpg");
            // Get the data from an ImageView as bytes
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] data1 = baos.toByteArray();

            UploadTask uploadTask = mountainsRef.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    ImageView imgRemove1 = (ImageView)findViewById(R.id.imgRemove1);
                    ImageView imgRemove2 = (ImageView)findViewById(R.id.imgRemove2);
                    ImageView imgRemove3 = (ImageView)findViewById(R.id.imgRemove3);
                    int cont = 0;
                    if(imgRemove1.getVisibility() == View.VISIBLE){
                        cont++;
                    }
                    if(imgRemove2.getVisibility() == View.VISIBLE){
                        cont++;
                    }
                    if(imgRemove3.getVisibility() == View.VISIBLE){
                        cont++;
                    }
                    updateImgEditRemove();
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("NumPhotos", cont);
                    // Add a new document (asynchronously) in collection "cities" with id "LA"
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("users").document(uid).update(docData);
                }
            });

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    public float getImageRotation(Uri path, Context context) {
        try {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};

            Cursor cursor = context.getContentResolver().query(path, projection, null, null, null);

            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            cursor.close();

        } catch (Exception ex) {
            return 0f;
        }

        return 0f;
    }

    public void updateImgEditRemove(){
        ImageView imgPhoto1 = (ImageView)findViewById(R.id.imgProfile1);
        ImageView imgPhoto2 = (ImageView)findViewById(R.id.imgProfile2);
        ImageView imgPhoto3 = (ImageView)findViewById(R.id.imgProfile3);

        ImageView imgRemove1 = (ImageView)findViewById(R.id.imgRemove1);
        ImageView imgRemove2 = (ImageView)findViewById(R.id.imgRemove2);
        ImageView imgRemove3 = (ImageView)findViewById(R.id.imgRemove3);

        ImageView imgEdit1 = (ImageView)findViewById(R.id.imgEdit1);
        ImageView imgEdit2 = (ImageView)findViewById(R.id.imgEdit2);
        ImageView imgEdit3 = (ImageView)findViewById(R.id.imgEdit3);

        if(imgRemove3.getVisibility() == View.VISIBLE){
            imgEdit1.setVisibility(View.VISIBLE);
        } else if(imgRemove2.getVisibility() == View.VISIBLE && imgRemove3.getVisibility() != View.VISIBLE){
            imgEdit3.setVisibility(View.VISIBLE);
        } else if(imgRemove1.getVisibility() == View.VISIBLE && imgRemove3.getVisibility() != View.VISIBLE){
            imgEdit2.setVisibility(View.VISIBLE);
        }
    }
}
