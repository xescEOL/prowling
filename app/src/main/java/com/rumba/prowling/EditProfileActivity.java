package com.rumba.prowling;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Matrix;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class EditProfileActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 1;
    private int photonum;
    private StorageReference storageRef;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        System.out.println("AddPhotos class - activity_editprofile");

        ImageView imgPhoto1 = (ImageView)findViewById(R.id.imgProfile1);
        ImageView imgPhoto2 = (ImageView)findViewById(R.id.imgProfile2);
        ImageView imgPhoto3 = (ImageView)findViewById(R.id.imgProfile3);
        ImageView imgPhoto4 = (ImageView)findViewById(R.id.imgProfile4);
        ImageView imgPhoto5 = (ImageView)findViewById(R.id.imgProfile5);
        ImageView imgPhoto6 = (ImageView)findViewById(R.id.imgProfile6);

        ImageView imgRemove1 = (ImageView)findViewById(R.id.imgRemove1);
        ImageView imgRemove2 = (ImageView)findViewById(R.id.imgRemove2);
        ImageView imgRemove3 = (ImageView)findViewById(R.id.imgRemove3);
        ImageView imgRemove4 = (ImageView)findViewById(R.id.imgRemove4);
        ImageView imgRemove5 = (ImageView)findViewById(R.id.imgRemove5);
        ImageView imgRemove6 = (ImageView)findViewById(R.id.imgRemove6);

        ImageView imgEdit1 = (ImageView)findViewById(R.id.imgEdit1);
        ImageView imgEdit2 = (ImageView)findViewById(R.id.imgEdit2);
        ImageView imgEdit3 = (ImageView)findViewById(R.id.imgEdit3);
        ImageView imgEdit4 = (ImageView)findViewById(R.id.imgEdit4);
        ImageView imgEdit5 = (ImageView)findViewById(R.id.imgEdit5);
        ImageView imgEdit6 = (ImageView)findViewById(R.id.imgEdit6);

        Button butSalir = (Button) findViewById(R.id.butSalir);

        updateImgEditRemove();


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
        imgEdit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 4;
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
        imgEdit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 5;
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
        imgEdit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 6;
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
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
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
            switch (photonum) {
                case 1:  imageView = (ImageView) findViewById(R.id.imgProfile1);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove1);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit1);
                    break;
                case 2:  imageView = (ImageView) findViewById(R.id.imgProfile2);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove2);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit2);
                    break;
                case 3:  imageView = (ImageView) findViewById(R.id.imgProfile3);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove3);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit3);
                    break;
                case 4:  imageView = (ImageView) findViewById(R.id.imgProfile4);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove4);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit4);
                    break;
                case 5:  imageView = (ImageView) findViewById(R.id.imgProfile5);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove5);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit5);
                    break;
                case 6:  imageView = (ImageView) findViewById(R.id.imgProfile6);
                         imgRemove = (ImageView)findViewById(R.id.imgRemove6);
                         imgEdit = (ImageView)findViewById(R.id.imgEdit6);
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
                    ImageView imgRemove4 = (ImageView)findViewById(R.id.imgRemove4);
                    ImageView imgRemove5 = (ImageView)findViewById(R.id.imgRemove5);
                    ImageView imgRemove6 = (ImageView)findViewById(R.id.imgRemove6);
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
                    if(imgRemove4.getVisibility() == View.VISIBLE){
                        cont++;
                    }
                    if(imgRemove5.getVisibility() == View.VISIBLE){
                        cont++;
                    }
                    if(imgRemove6.getVisibility() == View.VISIBLE){
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
        ImageView imgPhoto4 = (ImageView)findViewById(R.id.imgProfile4);
        ImageView imgPhoto5 = (ImageView)findViewById(R.id.imgProfile5);
        ImageView imgPhoto6 = (ImageView)findViewById(R.id.imgProfile6);

        ImageView imgRemove1 = (ImageView)findViewById(R.id.imgRemove1);
        ImageView imgRemove2 = (ImageView)findViewById(R.id.imgRemove2);
        ImageView imgRemove3 = (ImageView)findViewById(R.id.imgRemove3);
        ImageView imgRemove4 = (ImageView)findViewById(R.id.imgRemove4);
        ImageView imgRemove5 = (ImageView)findViewById(R.id.imgRemove5);
        ImageView imgRemove6 = (ImageView)findViewById(R.id.imgRemove6);

        ImageView imgEdit1 = (ImageView)findViewById(R.id.imgEdit1);
        ImageView imgEdit2 = (ImageView)findViewById(R.id.imgEdit2);
        ImageView imgEdit3 = (ImageView)findViewById(R.id.imgEdit3);
        ImageView imgEdit4 = (ImageView)findViewById(R.id.imgEdit4);
        ImageView imgEdit5 = (ImageView)findViewById(R.id.imgEdit5);
        ImageView imgEdit6 = (ImageView)findViewById(R.id.imgEdit6);
        if(imgRemove5.getVisibility() == View.VISIBLE){
            imgEdit6.setVisibility(View.VISIBLE);
        } else if(imgRemove4.getVisibility() == View.VISIBLE){
            imgEdit5.setVisibility(View.VISIBLE);
        } else if(imgRemove3.getVisibility() == View.VISIBLE){
            imgEdit4.setVisibility(View.VISIBLE);
        } else if(imgRemove2.getVisibility() == View.VISIBLE){
            imgEdit3.setVisibility(View.VISIBLE);
        } else if(imgRemove1.getVisibility() == View.VISIBLE){
            imgEdit2.setVisibility(View.VISIBLE);
        }else{
            imgEdit1.setVisibility(View.VISIBLE);
        }
    }

}
