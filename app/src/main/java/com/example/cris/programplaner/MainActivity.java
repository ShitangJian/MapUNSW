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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cris.programplaner.model.ChildData;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ExpandableListView listview;
    private ExpandListViewAdapter adapter;
    private List<ExpandData> datalist = new ArrayList<>();
    ImageView icon;
    TextView name;
    String UserID;
    FirebaseAuth mAuth;
    File localFile;
    DatabaseReference reff;
    public static String courseType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();

        icon = (ImageView)findViewById(R.id.usericon);
        name = (TextView)findViewById(R.id.username);

        StorageReference ref = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://infs3605team5database.appspot.com")
                .child("profile").child(UserID+".jpg");

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String a = dataSnapshot.child("name").getValue().toString();
                name.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView planner = (TextView) findViewById(R.id.goto_planner);
        planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,JoinPlanActivity.class));
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
