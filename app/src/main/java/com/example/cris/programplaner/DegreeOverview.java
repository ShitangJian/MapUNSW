package com.example.cris.programplaner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cris.programplaner.model.PlannedCourse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DegreeOverview extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference ref;
    String UserID;
    ListView listview;
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
        listview = (ListView) findViewById(R.id.listViewOverview);
        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.search_format,R.id.listViewOverview,list);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    plannedCourse = ds.getValue(PlannedCourse.class);
                    list.add(plannedCourse.getCourse1());
                }
                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
