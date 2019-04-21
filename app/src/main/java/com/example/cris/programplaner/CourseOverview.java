package com.example.cris.programplaner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cris.programplaner.model.PlannedCourse;
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
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    PlannedCourse plannedCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courseoverview);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        plannedCourse = new PlannedCourse();

        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        listView = findViewById(R.id.listViewOverview);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.degree_overview_format,R.id.degreeOverviewInfo, list);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String course1 = ds.child("course1").getValue(String.class);
                    String course2 = ds.child("course2").getValue(String.class);
                    String course3 = ds.child("course3").getValue(String.class);
                    Log.d("TAG", course1 + " | " + course2 + " | " + course3);

                    if (course1 != null) {
                        list.add(course1);
                    }
                    if (course2 != null){
                        list.add(course2);
                    }
                    if (course3 != null) {
                        list.add(course3);
                    }
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

        //Toast.makeText(getApplicationContext(), "Total number of items are " + listView.getAdapter().getCount(), Toast.LENGTH_LONG).show();
    }

}
