package com.example.cris.programplaner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseOverview extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference ref;
    String UserID;
    ListView listview;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courseoverview);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dSnapshot: dataSnapshot.getChildren()){
                    for (DataSnapshot ds: dSnapshot.getChildren()) {
                        String courseEnrolled = ds.child("course1").getValue(String.class);
                        list.add(courseEnrolled);
                        Log.d("TAG", courseEnrolled);
                    }
                    Log.d("TAG", list.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}
