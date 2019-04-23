package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProgressionPlanner extends AppCompatActivity {

    String UserID;
    FirebaseAuth mAuth;
    File localFile;
    CircleImageView icon,icon2;
    DatabaseReference reff;
    TextView name;
    int UoC;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ExpandableListView listview;
    private ExpandListViewAdapter adapter;
    private List<ExpandData> datalist = new ArrayList<>();
    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> listUoC;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_term);
        icon2 = (CircleImageView) findViewById(R.id.profile_image_icon1);
        name= (TextView)findViewById(R.id.name1);
        Button T1 = (Button) findViewById(R.id.term1);
        Button T2 = (Button) findViewById(R.id.term2);
        Button T3 = (Button) findViewById(R.id.term3);
        Button T4 = (Button) findViewById(R.id.term4);
        Button T5 = (Button) findViewById(R.id.term5);
        Button T6 = (Button) findViewById(R.id.term6);
        Button T7 = (Button) findViewById(R.id.term7);
        Button T8 = (Button) findViewById(R.id.term8);
        Button T9 = (Button) findViewById(R.id.term9);
        final TextView unitsOfCredit = findViewById(R.id.tv_credit);
        loadPhoto();
        loadname();
        implementNavi();

        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 1);
                startActivity(term1);
            }
        });

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 2);
                startActivity(term1);
            }
        });
        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 3);
                startActivity(term1);
            }
        });
        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 4);
                startActivity(term1);
            }
        });
        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 5);
                startActivity(term1);
            }
        });
        T6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 6);
                startActivity(term1);
            }
        });
        T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 7);
                startActivity(term1);
            }
        });
        T8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 8);
                startActivity(term1);
            }
        });
        T9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent term1 = new Intent(ProgressionPlanner.this, Year1_term1.class);
                term1.putExtra("term", 9);
                startActivity(term1);
            }
        });
        listUoC = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                for (DataSnapshot ds3: dataSnapshot3.getChildren()) {
                    String course1 = ds3.child("course1").getValue(String.class);
                    String course2 = ds3.child("course2").getValue(String.class);
                    String course3 = ds3.child("course3").getValue(String.class);

                    if (course1 != null) {
                        listUoC.add(course1);
                    }
                    if (course2 != null){
                        listUoC.add(course2);
                    }
                    if (course3 != null) {
                        listUoC.add(course3);
                    }
                }
                UoC = listUoC.size() * 6;
                String units = Integer.toString(UoC);
                unitsOfCredit.setText(units);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadname() {
        reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameee = dataSnapshot.child("name").getValue().toString();
                name.setText(nameee);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPhoto() {
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
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
                            icon2.setImageBitmap(bitmap);
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
    }
    public void implementNavi(){
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();

        icon = (CircleImageView) findViewById(R.id.profile_image2);
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
                            icon2.setImageBitmap(bitmap);
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
                Toast.makeText(ProgressionPlanner.this,"You are looking at Progression Page",Toast.LENGTH_LONG).show();

            }
        });
        TextView goto_courseoverview = (TextView)findViewById(R.id.goto_courseoverview);
        goto_courseoverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProgressionPlanner.this,MainActivity.class));            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout1);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.menu);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(ProgressionPlanner.this,RetrieveCourse.class));
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
    }
