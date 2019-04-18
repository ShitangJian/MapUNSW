package com.example.cris.programplaner;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cris.programplaner.model.CoreCourse;
import com.example.cris.programplaner.model.Prerequisite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class course_overview extends MainActivity {

    FirebaseDatabase database;
    DatabaseReference refDatabase;
    DatabaseReference refOverview;
    DatabaseReference refPrerequisite;
    CoreCourse course;
    Prerequisite prerequisite;
    String SelectedCourse = RetrieveCourse.selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);


        course = new CoreCourse();
        prerequisite = new Prerequisite();

        //TO DO
        // 1: Determine Pre-requisites
        database = FirebaseDatabase.getInstance();
        refDatabase = FirebaseDatabase.getInstance().getReference();
        courseOverview();
        prerequisite();



        //Display prerequisite(s)

    }

    private void courseOverview() {
        TextView courseCodeHeader = findViewById(R.id.courseCodeHeader);

        final TextView courseName = findViewById(R.id.courseName);
        final TextView overview = findViewById(R.id.overview);
        final TextView handbookLink = findViewById(R.id.handbook);
        handbookLink.setMovementMethod(LinkMovementMethod.getInstance());

        final ImageView circleT1 = (ImageView)findViewById(R.id.circleT1);
        final ImageView circleT2 = (ImageView)findViewById(R.id.circleT2);
        final ImageView circleT3 = (ImageView)findViewById(R.id.circleT3);
        courseCodeHeader.setText(SelectedCourse);
        refOverview = refDatabase.child("Course"); //Snapshot of course table
        Query Overview = refOverview.orderByChild("Code").equalTo(SelectedCourse); //Find course user clicked on

        Overview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Determine term availability
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    course = ds.getValue(CoreCourse.class);
                    if (course.getT1().equals("FALSE")){
                        circleT1.setColorFilter(Color.RED);
                    }
                    if (course.getT2().equals("FALSE")){
                        circleT2.setColorFilter(Color.RED);
                    }
                    if (course.getT3().equals("FALSE")){
                        circleT3.setColorFilter(Color.RED);
                    }
                    courseName.setText(course.getName());
                    overview.setText(course.getOverview());
                    handbookLink.setText(course.getHandbook());
                }
                //Get Overview


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void prerequisite() {

        final TextView tvPrerequisite = (TextView)findViewById(R.id.prerequisite);
        refPrerequisite = refDatabase.child("Prerequisite");
        final Query Prerequisite = refPrerequisite.orderByChild("Code").equalTo(SelectedCourse);
        Prerequisite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot ds2: dataSnapshot2.getChildren()) {
                    prerequisite = ds2.getValue(Prerequisite.class);
                    tvPrerequisite.setText(prerequisite.getP1());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}