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
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.nio.channels.SelectableChannel;


public class AddPhotosActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 1;
    private int photonum;
    private StorageReference storageRef;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphotos);
        System.out.println("AddPhotos class - activity_addphotos");

        ImageView imgPhoto1 = (ImageView)findViewById(R.id.imgProfile1);
        ImageView imgPhoto2 = (ImageView)findViewById(R.id.imgProfile2);
        ImageView imgPhoto3 = (ImageView)findViewById(R.id.imgProfile3);
        ImageView imgPhoto4 = (ImageView)findViewById(R.id.imgProfile4);
        ImageView imgPhoto5 = (ImageView)findViewById(R.id.imgProfile5);
        ImageView imgPhoto6 = (ImageView)findViewById(R.id.imgProfile6);

        imgPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 1;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
        imgPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 2;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
        imgPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 3;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
        imgPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 4;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
        imgPhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 5;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
        imgPhoto6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photonum = 6;
                try {
                    if (ActivityCompat.checkSelfPermission(AddPhotosActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddPhotosActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            ImageView imageView = (ImageView) findViewById(R.id.imgProfile1);
            switch (photonum) {
                case 1:  imageView = (ImageView) findViewById(R.id.imgProfile1);
                    break;
                case 2:  imageView = (ImageView) findViewById(R.id.imgProfile2);
                    break;
                case 3:  imageView = (ImageView) findViewById(R.id.imgProfile3);
                    break;
                case 4:  imageView = (ImageView) findViewById(R.id.imgProfile4);
                    break;
                case 5:  imageView = (ImageView) findViewById(R.id.imgProfile5);
                    break;
                case 6:  imageView = (ImageView) findViewById(R.id.imgProfile6);
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

}
