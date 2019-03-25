package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    String [] degree = {"Information System","double Degree","ddd"};
    ArrayAdapter<String>arrayAdapter;
    ImageView imageView;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        Button btnUpload = (Button)findViewById(R.id.btnUpload);
        imageView = (ImageView)findViewById(R.id.imageView);

        //take picture button
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto,0);
            }
        });

        //upload images button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImage,RESULT_LOAD_IMAGE);
            }
        });

        EditText Username = (EditText)findViewById(R.id.username);
        Button Login = (Button)findViewById(R.id.register);

        //spinner to select degree
        Spinner degreeSelect = (Spinner)findViewById(R.id.degreeSelect);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,degree);
        degreeSelect.setAdapter(arrayAdapter);

        //switch to Homepage by clicking login button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HomePage = new Intent(view.getContext(),MainActivity.class);
                startActivity(HomePage);
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && data != null){
            Uri selectdImage = data.getData();
            imageView.setImageURI(selectdImage);
        }
        else {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

}
