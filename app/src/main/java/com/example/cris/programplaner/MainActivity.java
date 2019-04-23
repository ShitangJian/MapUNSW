package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cris.programplaner.model.ChildData;
import com.example.cris.programplaner.model.PlannedCourse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ExpandableListView listview;
    private ExpandListViewAdapter adapter;
    private List<ExpandData> datalist = new ArrayList<>();
    CircleImageView icon;
    TextView name;
    String UserID;
    FirebaseAuth mAuth;
    File localFile;
    DatabaseReference reff;

    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter1;
    PlannedCourse plannedCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();

        icon = (CircleImageView) findViewById(R.id.profile_image2);
        name = (TextView)findViewById(R.id.username);

        StorageReference ref = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://infs3605team5database.appspot.com")
                .child("profile").child(UserID+".jpg");
        implementCourseoverview();

        localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");

            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            icon.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        } catch (IOException e) {

        }

        reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot4) {
                String a = dataSnapshot4.child("name").getValue().toString();
                name.setText("Hi "+a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView planner = (TextView) findViewById(R.id.goto_planner);
        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProgressionPlanner.class));
            }
        });
        TextView goto_courseoverview = (TextView)findViewById(R.id.goto_courseoverview);
        goto_courseoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"You are looking at Courses Overview Page",Toast.LENGTH_LONG).show();
            }
        });


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
                    RetrieveCourse.courseType = "Core";
                }
                else if (data.equals("Prescribed Electives")) {
                    RetrieveCourse.courseType = "PrescribedElective";
                }
                else if (data.equals("Free Electives")) {
                    RetrieveCourse.courseType = "FreeElective";
                }
                //else if (data.equals("GE")) {
                  //  courseType = "GeneralEducation";
                //}
                startActivity(new Intent(MainActivity.this,RetrieveCourse.class));
                return false;
            }
        });
    }

    private void testData(){


        ExpandData expandData2 = new ExpandData();
        expandData2.setName("Course Outline");
        ArrayList<ChildData> arr2 = new ArrayList<>();
        arr2.add(new ChildData("Core Courses"));
        arr2.add(new ChildData("Prescribed Electives"));
        arr2.add(new ChildData("Free Electives"));
        // arr2.add(new ChildData("GE"));
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
    public void implementCourseoverview(){
        plannedCourse = new PlannedCourse();

        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        listView = findViewById(R.id.listViewOverview);

        list = new ArrayList<>();
        adapter1 = new ArrayAdapter<String>(this,R.layout.degree_overview_format,R.id.degreeOverviewInfo, list);
try{
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String course1 = ds.child("course1").getValue(String.class);
                    String course2 = ds.child("course2").getValue(String.class);
                    String course3 = ds.child("course3").getValue(String.class);
                    Log.d("TAG", course1 + " | " + course2 + " | " + course3);

                    if (course1 != null) {
                        list.add(course1);
                    }
                    if (course2 != null){
                        list.add(course2);
                    }
                    if (course3 != null) {
                        list.add(course3);
                    }
                }
                listView.setAdapter(adapter1);
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);}
        catch (Exception e){

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(), course_overview.class);
                course_overview.selectedCourse = (listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });
    }
}
