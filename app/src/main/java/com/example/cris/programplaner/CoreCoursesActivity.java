package com.example.cris.programplaner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.cris.programplaner.model.Course;

import java.util.ArrayList;
import java.util.List;


public class CoreCoursesActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private CourseAdapter courseAdapter;
    private List<Course> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        onSetTitle();
        initTestData();
        initView();
    }

    private void initTestData() {
        datas = new ArrayList<>();
        datas.add(new Course("INFS 1602"));
        datas.add(new Course("INFS 1603"));
        datas.add(new Course("INFS 1604"));
        datas.add(new Course("INFS 1605"));
        datas.add(new Course("INFS 1606"));
        datas.add(new Course("INFS 1607"));
        datas.add(new Course("INFS 1608"));
        datas.add(new Course("INFS 1609"));
        datas.add(new Course("INFS 1610"));
        datas.add(new Course("INFS 1611"));
        datas.add(new Course("INFS 1612"));
    }

    private void initView() {
        recycleView =  findViewById(R.id.recycleview);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CourseAdapter(this,datas);
        recycleView.setAdapter(courseAdapter);
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(CoreCoursesActivity.this,SpecialCoursesActivity.class);
                intent.putExtra("data",datas.get(position));
                startActivity(intent);
            }
        });
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
