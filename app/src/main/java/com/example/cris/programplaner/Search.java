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

import com.example.cris.programplaner.model.CoreCourse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends AppCompatActivity {

    ListView search_course;
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list;
    CoreCourse course;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_course = (ListView) findViewById(R.id.courselist);
        course = new CoreCourse();
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        ref = database.getReference("Course");
        adapter = new ArrayAdapter<String>(this, R.layout.search_format, R.id.coreCourseInfo, list);
        ref.addValueEventListener(new ValueEventListener() {
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



        search_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String course = (String)search_course.getItemAtPosition(position);
               Intent courseback = new Intent();
               courseback.putExtra("course1",course);
               setResult(RESULT_OK,courseback);
               finish();
            }
        });


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
