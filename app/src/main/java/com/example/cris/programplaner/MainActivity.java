package com.example.cris.programplaner;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.cris.programplaner.model.ChildData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ExpandableListView listview;
    private ExpandListViewAdapter adapter;
    private List<ExpandData> datalist = new ArrayList<>();
    public static String courseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        testData();

        listview = findViewById(R.id.contacts_expandlistview);
        adapter = new ExpandListViewAdapter(this, listview, datalist);
        listview.setAdapter(adapter);
        listview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String data = datalist.get(i).getMenus().get(i1).getName();
                if (data.equals("Core Courses")) {
                    courseType = "Core";
                }
                else if (data.equals("Prescribed Electives")) {
                    courseType = "PrescribedElective";
                }
                else if (data.equals("Free Electives")) {
                    courseType = "FreeElective";
                }
                else if (data.equals("GE")) {
                    courseType = "GeneralEducation";
                }
                startActivity(new Intent(MainActivity.this,RetrieveCourse.class));
                return false;
            }
        });
    }

    private void testData(){


        ExpandData expandData2 = new ExpandData();
        expandData2.setName("Course OutLines");
        ArrayList<ChildData> arr2 = new ArrayList<>();
        arr2.add(new ChildData("Core Courses"));
        arr2.add(new ChildData("Prescribed Electives"));
        arr2.add(new ChildData("Free Electives"));
        arr2.add(new ChildData("GE"));
        expandData2.setMenus(arr2);
        datalist.add(expandData2);

    }
    //按返回键 收起 DrawerLayout
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}
