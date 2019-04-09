package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;

public class Popup extends AppCompatActivity {


    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        Button btnUpload = (Button) findViewById(R.id.btnUpload);

        //take picture button
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, 0);
            }
        });

        //upload images button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImage, RESULT_LOAD_IMAGE);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri selectdImage = data.getData();
            //imageView.setImageURI(selectdImage);
            Intent intent2 = new Intent(Popup.this, Register.class);
            intent2.putExtra("imagePath", selectdImage);
            startActivity(intent2);
            finish();
        } else {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
            byte[] byteArray = bStream.toByteArray();
            Intent intent1 = new Intent(Popup.this, Register.class);
            intent1.putExtra("Bitmap", byteArray);
            startActivity(intent1);
            finish();
        }
    }
}


