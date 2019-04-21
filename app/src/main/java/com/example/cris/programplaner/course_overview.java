package com.example.cris.programplaner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
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
    DatabaseReference refOverview;
    DatabaseReference refPrerequisite;
    CoreCourse course;
    Prerequisite prerequisite;
    String SelectedCourse = RetrieveCourse.selectedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);



        course = new CoreCourse();
        prerequisite = new Prerequisite();

        //TO DO
        // 1: Determine Pre-requisites

        database = FirebaseDatabase.getInstance();
        courseOverview();
        prerequisite();
        TextView goToOverview = (TextView) findViewById(R.id.tv_join);
        goToOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(course_overview.this, JoinPlanActivity.class));
            }
        });
        Button degreeOverview = findViewById(R.id.goToDegreeOverview1);
        degreeOverview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(course_overview.this,CourseOverview.class));
            }
        });


        //Display prerequisite(s)

    }

    private void courseOverview() {
        final TextView courseCodeHeader = findViewById(R.id.courseCodeHeader);

        final TextView courseName = findViewById(R.id.courseName);
        final TextView overview = findViewById(R.id.overview);
        final TextView handbookLink = findViewById(R.id.handbook);
        handbookLink.setMovementMethod(LinkMovementMethod.getInstance());

        final TextView circleT1 = (TextView)findViewById(R.id.circleT1);
        final TextView circleT2 = (TextView)findViewById(R.id.circleT2);
        final TextView circleT3 = (TextView)findViewById(R.id.circleT3);
        refOverview = database.getReference("Course"); //Snapshot of course table
        Query Overview = refOverview.orderByChild("Code").equalTo(SelectedCourse); //Find course user clicked on

        Overview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Determine term availability
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    course = ds.getValue(CoreCourse.class);
                    if (course.getT1().equals("FALSE")){
                        circleT1.setBackgroundResource(R.drawable.not_pass);
                    }
                    if (course.getT2().equals("FALSE")){
                        circleT2.setBackgroundResource(R.drawable.not_pass);
                    }
                    if (course.getT3().equals("FALSE")){
                        circleT3.setBackgroundResource(R.drawable.not_pass);
                    }
                    courseName.setText(course.getCode());
                    courseCodeHeader.setText(course.getName());
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
        refPrerequisite = database.getReference("Prerequisite");
        Query Prerequisite = refPrerequisite.orderByChild("Code").equalTo(SelectedCourse);
        Prerequisite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot ds2: dataSnapshot2.getChildren()) {
                    prerequisite = ds2.getValue(Prerequisite.class);
                    if (prerequisite.getP2() != null){
                        tvPrerequisite.setText(prerequisite.getP1() + "  |  " + prerequisite.getP2());}
                        else if (prerequisite.getP2() == null&&prerequisite.getP1()!=null){
                        tvPrerequisite.setText(prerequisite.getP1());}
                    else {
                        tvPrerequisite.setText("No prerequisites");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}