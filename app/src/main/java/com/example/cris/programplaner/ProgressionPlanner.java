package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProgressionPlanner extends AppCompatActivity {

    String UserID;
    FirebaseAuth mAuth;
    File localFile;
    ImageView icon;
    DatabaseReference reff;
    TextView name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_term);
        icon = (ImageView)findViewById(R.id.imageprofile);
        name= (TextView)findViewById(R.id.name1);
        Button T1 = (Button) findViewById(R.id.term1);
        Button T2 = (Button) findViewById(R.id.term2);
        Button T3 = (Button) findViewById(R.id.term3);
        Button T4 = (Button) findViewById(R.id.term4);
        Button T5 = (Button) findViewById(R.id.term5);
        Button T6 = (Button) findViewById(R.id.term6);
        Button T7 = (Button) findViewById(R.id.term7);
        Button T8 = (Button) findViewById(R.id.term8);
        Button T9 = (Button) findViewById(R.id.term9);
        loadPhoto();
        loadname();

        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 1);
                startActivity(term1);
            }
        });

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 2);
                startActivity(term1);
            }
        });
        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 3);
                startActivity(term1);
            }
        });
        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 4);
                startActivity(term1);
            }
        });
        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 5);
                startActivity(term1);
            }
        });
        T6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 6);
                startActivity(term1);
            }
        });
        T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 7);
                startActivity(term1);
            }
        });
        T8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 8);
                startActivity(term1);
            }
        });
        T9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 9);
                startActivity(term1);
            }
        });

    }

    private void loadname() {
        reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.child("name").getValue().toString();
                name.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPhoto() {
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        StorageReference ref = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://infs3605team5database.appspot.com")
                .child("profile").child(UserID+".jpg");

        localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");

            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            icon.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        } catch (IOException e) {

        }
    }
}