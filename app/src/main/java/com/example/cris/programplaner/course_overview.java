package com.example.cris.programplaner;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class course_overview extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference refOverview;
    DatabaseReference refPrerequisite;
    CoreCourse course;
    Prerequisite prerequisite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);

        String SelectedCourse = RetrieveCourse.selectedCourse;
        TextView courseCodeHeader = findViewById(R.id.courseCodeHeader);
        final ImageView circleT1 = (ImageView)findViewById(R.id.circleT1);
        final ImageView circleT2 = (ImageView)findViewById(R.id.circleT2);
        final ImageView circleT3 = (ImageView)findViewById(R.id.circleT3);
        final TextView tvPrerequisite = (TextView)findViewById(R.id.prerequisite);

        //Make sure to re-use SelectedCourse as variable name in ListView of course browser
        courseCodeHeader.setText(SelectedCourse);
        course = new CoreCourse();
        prerequisite = new Prerequisite();


        //TO-DO Term availabilities || Course Overview || Pre-requisites
        // 1: Connect to database and look for course code
        // 2: Term availabities (Convert to boolean?)
        // 3: Get content and link to handbook
        // 4: Determine Pre-requisites

        database = FirebaseDatabase.getInstance();
        refOverview = database.getReference("Course"); //Snapshot of course table
        Query Overview = refOverview.orderByChild("Code").equalTo(SelectedCourse); //Find course user clicked on

        refPrerequisite = database.getReference("Prerequisite"); //Snapshot of prerequisite table
        Query Prerequisite = refPrerequisite.equalTo(SelectedCourse);

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
                }
                //Get Overview


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Display prerequisite(s)
        Prerequisite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot ds2: dataSnapshot2.getChildren()) {
                    prerequisite = ds2.getValue(Prerequisite.class);
                    tvPrerequisite.setText(prerequisite.getPrerequisite1());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
