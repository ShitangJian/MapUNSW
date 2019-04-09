package com.example.cris.programplaner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Year1_term1 extends AppCompatActivity {

    private Button add1_1,add1_2,add1_3,comfirmcourse;
    private TextView course1_1, course1_2, course1_3;
    private DatabaseReference mDatabase;
    String UserID;
    FirebaseAuth mAuth;
    SemsterCourse scourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year1_term1);

        add1_1 = (Button)findViewById(R.id.add1_1);
        add1_2 = (Button)findViewById(R.id.add1_2);
        add1_3 = (Button)findViewById(R.id.add1_3);
        comfirmcourse = (Button)findViewById(R.id.comfirmcourse);

        course1_1 = (TextView)findViewById(R.id.course1_1);
        course1_2 = (TextView)findViewById(R.id.course1_2);
        course1_3 = (TextView)findViewById(R.id.course1_3);

        scourse = new SemsterCourse();
        mAuth = FirebaseAuth.getInstance();

        add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search1();
            }
        });

        add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search2();
            }
        });

        add1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search3();
            }
        });

        comfirmcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadcourse();
            }
        });
    }

    private void uploadcourse() {
        UserID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("User/");
        getValue();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(UserID).child("S1T1").setValue(scourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getValue() {
        scourse.setCourse1(course1_1.getText().toString());
        scourse.setCourse2(course1_2.getText().toString());
        scourse.setCourse3(course1_3.getText().toString());
    }

    private void Search1(){
        Intent intent = new Intent(Year1_term1.this,Search.class);
        startActivityForResult(intent,1);

    }

    private void Search2(){
        Intent intent = new Intent(Year1_term1.this,Search.class);
        startActivityForResult(intent,2);

    }

    private void Search3(){
        Intent intent = new Intent(Year1_term1.this,Search.class);
        startActivityForResult(intent,3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){
                String course1 = data.getStringExtra("course1");
                course1_1.setText(course1);
            }
        }

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                String course1 = data.getStringExtra("course1");
                course1_2.setText(course1);
            }
        }

        if(requestCode==3){
            if(resultCode==RESULT_OK){
                String course1 = data.getStringExtra("course1");
                course1_3.setText(course1);
            }
        }
    }

}
