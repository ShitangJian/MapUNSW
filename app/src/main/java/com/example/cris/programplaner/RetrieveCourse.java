package com.example.cris.programplaner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cris.programplaner.model.CoreCourse;
import com.example.cris.programplaner.model.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class RetrieveCourse extends MainActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    CoreCourse course;
    public static String courseType;
    public static String selectedCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_course);

        course = new CoreCourse();
        listView = (ListView) findViewById(R.id.listView);

        title();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Course");
        //if statement to capture core, free elective, prescribed elective or general education
        Query CoreQuery = ref.orderByChild("CourseType").equalTo(courseType);

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.search_format,R.id.coreCourseInfo, list);
        CoreQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    course = ds.getValue(CoreCourse.class);
                    list.add(course.getCode());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //OnClickListener when clicking on a list item. Should direct to course overview page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(RetrieveCourse.this, course_overview.class);
                selectedCourse = (listView.getItemAtPosition(i).toString());
                intent.putExtra(EXTRA_MESSAGE, selectedCourse);
                startActivity(intent);
                Toast.makeText(RetrieveCourse.this, "You click on: " + selectedCourse, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void title() {
        TextView courseBrowserHeader = (TextView)findViewById(R.id.courseBrowserHeader);
        String title = RetrieveCourse.courseType;
        if (title == "Core") {
            courseBrowserHeader.setText("Core Courses");
        }
        else if (title == "PrescribedElective") {
            courseBrowserHeader.setText("Prescribed Electives");
        }
        else if (title == "FreeElective") {
            courseBrowserHeader.setText("Free Electives");
        }
        else if (title == "GeneralEducation") {
            courseBrowserHeader.setText("General Education");
        }
    }
}
