package com.example.cris.programplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class course_overview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_overview);
        String SelectedCourse = RetrieveCourse.selectedCourse;
        TextView CourseCode = findViewById(R.id.CourseCode);
        CourseCode.setText(SelectedCourse);
    }
}
