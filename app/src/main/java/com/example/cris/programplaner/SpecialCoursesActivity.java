package com.example.cris.programplaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cris.programplaner.model.CoreCourse;
import com.example.cris.programplaner.model.Course;
import com.example.cris.programplaner.model.Prerequisite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SpecialCoursesActivity extends AppCompatActivity {


    String course;
    FirebaseDatabase database;
    DatabaseReference refRe,refPre;
    ArrayList<String> list,listRe,listPre;
    String UserID;
    FirebaseAuth mAuth;
    Prerequisite prerequisite;
    String P1,P2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        course = RetrieveCourse.selectedCourse;
        prerequisite = new Prerequisite();
        database = FirebaseDatabase.getInstance();
        onSetTitle();
        createlist();
        findViewById(R.id.tv_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRe();

            }
        });
    }

    private void createlist() {
        listRe = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        refRe = FirebaseDatabase.getInstance().getReference("User").child(UserID);

        refRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                for (DataSnapshot ds3: dataSnapshot3.getChildren()) {
                    String course1 = ds3.child("course1").getValue(String.class);
                    String course2 = ds3.child("course2").getValue(String.class);
                    String course3 = ds3.child("course3").getValue(String.class);

                    if (course1 != null) {
                        listRe.add(course1);
                    }
                    if (course2 != null){
                        listRe.add(course2);
                    }
                    if (course3 != null) {
                        listRe.add(course3);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listPre = new ArrayList<>();
        refPre = database.getReference("Prerequisite");


        refPre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot ds2: dataSnapshot2.getChildren()) {
                    String Precourse = ds2.child("Code").getValue(String.class);
                    listPre.add(Precourse);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkPre() {
        if(listPre.contains(course)){



            refPre = FirebaseDatabase.getInstance().getReference("Prerequisite");
            refPre.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        if (dataSnapshot.child(course).child("P2").getValue().toString()!=null){
                            P1 = dataSnapshot.child(course).child("P1").getValue().toString();
                            P2 = dataSnapshot.child(course).child("P2").getValue().toString();
                            if (listRe.contains(P1)==false&&listRe.contains(P2)==false){
                                Toast.makeText(SpecialCoursesActivity.this, "You need to plan " + P1 +
                                        " and " +P2+ " before planning "+course, Toast.LENGTH_LONG).show();

                            }
                            else if(listRe.contains(P1)==false){
                                Toast.makeText(SpecialCoursesActivity.this, "You need to plan " + P1 + " before planning "+course, Toast.LENGTH_LONG).show();

                            }
                            else if(listRe.contains(P2)==false){
                                Toast.makeText(SpecialCoursesActivity.this, "You need to plan " + P2 + " before planning "+course, Toast.LENGTH_LONG).show();

                            }
                            else if(listRe.contains(P1)&&listRe.contains(P2)){
                                startActivity(new Intent(SpecialCoursesActivity.this,JoinPlanActivity.class));

                            }

                        }


                    }
                    catch(Exception e){
                        if (dataSnapshot.child(course).child("P1").getValue().toString()!=null){
                            P1 = dataSnapshot.child(course).child("P1").getValue().toString();
                            if(listRe.contains(P1)==false){
                                Toast.makeText(SpecialCoursesActivity.this, "You need to plan " + P1 + " before planning "+course, Toast.LENGTH_LONG).show();

                            }
                            else if(listRe.contains(P1)){
                                startActivity(new Intent(SpecialCoursesActivity.this,JoinPlanActivity.class));

                            }
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}
        else {
            startActivity(new Intent(SpecialCoursesActivity.this,JoinPlanActivity.class));

}
    }

    private void checkRe() {
        if (listRe.contains(course)) {
            Toast.makeText(SpecialCoursesActivity.this, "You have planned " + course + " already", Toast.LENGTH_LONG).show();

        }  else {
            checkPre();

        }
    }



    public void onSetTitle() {
        Toolbar toolbar = findViewById(R.id.at_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }
}
