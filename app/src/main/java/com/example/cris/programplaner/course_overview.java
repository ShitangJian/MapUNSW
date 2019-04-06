package com.example.cris.programplaner;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cris.programplaner.model.CoreCourse;
import com.example.cris.programplaner.model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class course_overview extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    CoreCourse course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);

        String SelectedCourse = RetrieveCourse.selectedCourse;
        TextView courseCodeHeader = findViewById(R.id.courseCodeHeader);
        final ImageView circleT1 = (ImageView)findViewById(R.id.circleT1);
        final ImageView circleT2 = (ImageView)findViewById(R.id.circleT2);
        final ImageView circleT3 = (ImageView)findViewById(R.id.circleT3);
        final TextView testT1 = (TextView)findViewById(R.id.testT1);
        //Make sure to re-use SelectedCourse as variable name in ListView of course browser
        courseCodeHeader.setText(SelectedCourse);
        course = new CoreCourse();


        //TO-DO Term availabilities || Course Overview || Pre-requisites
        // 1: Connect to database and look for course code
        // 2: Term availabities (Convert to boolean?)
        // 3: Get content and link to handbook

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Course");
        Query Overview = ref.orderByChild("Code").equalTo(SelectedCourse);

        Overview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
