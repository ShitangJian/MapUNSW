package com.example.cris.programplaner;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.cris.programplaner.model.CoreCourse;
import com.example.cris.programplaner.model.Prerequisite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {

    ListView search_course;
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference ref,refRe,refPre;
    ArrayList<String> list,listRe,listPre;
    CoreCourse course;
    SearchView searchView;
    int term;
    String UserID;
    FirebaseAuth mAuth;
    Query Prerequisite;
    Prerequisite prerequisite;
    String course11,P1,P2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        term = getIntent().getIntExtra("term", 0);
        search_course = (ListView) findViewById(R.id.courselist);
        prerequisite = new Prerequisite();
        course = new CoreCourse();
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        ref = database.getReference("Course");
        createlist();
        adapter = new ArrayAdapter<String>(this, R.layout.search_format, R.id.coreCourseInfo, list);
        if (term == 1 || term == 4 || term == 7) {

        Query T1 = ref.orderByChild("T1").equalTo("TRUE");
        T1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    course = ds.getValue(CoreCourse.class);


                    list.add(course.getCode());


                }

                search_course.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

        if (term == 2 || term == 5 || term == 8) {

            Query T1 = ref.orderByChild("T2").equalTo("TRUE");
            T1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        course = ds.getValue(CoreCourse.class);


                        list.add(course.getCode());


                    }

                    search_course.setAdapter(adapter);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if (term == 3 || term == 6 || term == 9) {

            Query T1 = ref.orderByChild("T3").equalTo("TRUE");
            T1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        course = ds.getValue(CoreCourse.class);


                        list.add(course.getCode());


                    }

                    search_course.setAdapter(adapter);

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


        search_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               course11 = (String)search_course.getItemAtPosition(position);
               checkRe();

            }
        });


    }

    private void createlist() {
        listRe = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        refRe = FirebaseDatabase.getInstance().getReference("User").child(UserID);

        refRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                for (DataSnapshot ds3: dataSnapshot3.getChildren()) {
                    String course1 = ds3.child("course1").getValue(String.class);
                    String course2 = ds3.child("course2").getValue(String.class);
                    String course3 = ds3.child("course3").getValue(String.class);

                    if (course1 != null) {
                        listRe.add(course1);
                    }
                    if (course2 != null){
                        listRe.add(course2);
                    }
                    if (course3 != null) {
                        listRe.add(course3);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listPre = new ArrayList<>();
        refPre = database.getReference("Prerequisite");


        refPre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                for (DataSnapshot ds2: dataSnapshot2.getChildren()) {
                    String Precourse = ds2.child("Code").getValue(String.class);
                    listPre.add(Precourse);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkPre() {
        if(listPre.contains(course11)){



            refPre = FirebaseDatabase.getInstance().getReference("Prerequisite");
            refPre.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                    if (dataSnapshot.child(course11).child("P2").getValue().toString()!=null){
                        P1 = dataSnapshot.child(course11).child("P1").getValue().toString();
                        P2 = dataSnapshot.child(course11).child("P2").getValue().toString();
                        if (listRe.contains(P1)==false&&listRe.contains(P2)==false){
                            Toast.makeText(Search.this, "You need to plan " + P1 +
                                    " and " +P2+ " before planning "+course11, Toast.LENGTH_LONG).show();

                        }
                        else if(listRe.contains(P1)==false){
                            Toast.makeText(Search.this, "You need to plan " + P1 + " before planning "+course11, Toast.LENGTH_LONG).show();

                        }
                        else if(listRe.contains(P2)==false){
                            Toast.makeText(Search.this, "You need to plan " + P2 + " before planning "+course11, Toast.LENGTH_LONG).show();

                        }
                        else if(listRe.contains(P1)&&listRe.contains(P2)){
                            Intent courseback = new Intent();
                            courseback.putExtra("course1",course11);
                            setResult(RESULT_OK,courseback);
                            finish();
                        }

                    }


                }
                catch(Exception e){
                    if (dataSnapshot.child(course11).child("P1").getValue().toString()!=null){
                        P1 = dataSnapshot.child(course11).child("P1").getValue().toString();
                        if(listRe.contains(P1)==false){
                            Toast.makeText(Search.this, "You need to plan " + P1 + " before planning "+course11, Toast.LENGTH_LONG).show();

                        }
                        else if(listRe.contains(P1)){
                            Intent courseback = new Intent();
                            courseback.putExtra("course1",course11);
                            setResult(RESULT_OK,courseback);
                            finish();
                        }
                    }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });}
            else {

        Intent courseback = new Intent();
        courseback.putExtra("course1",course11);
        setResult(RESULT_OK,courseback);
        finish();}
    }

    private void checkRe() {
        if (listRe.contains(course11)) {
            Toast.makeText(Search.this, "You have planned " + course11 + " already", Toast.LENGTH_LONG).show();

        }  else {
            checkPre();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.courselist);
        searchView = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }


}
