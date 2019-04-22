package com.example.cris.programplaner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Year1_term1 extends AppCompatActivity {

    private Button add1_1, add1_2, add1_3;
    private TextView course1_1, course1_2, course1_3, comfirmcourse, Semster;
    private DatabaseReference mDatabase;
    String UserID;
    FirebaseAuth mAuth;
    SemsterCourse scourse;
    int term;
    ImageView back;
    String Term, course3, course2, course1;
    DatabaseReference reff;
    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    PlannedCourse plannedCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_course);

        back = (ImageView) findViewById(R.id.imageView8);
        add1_1 = (Button) findViewById(R.id.add1_1);
        add1_2 = (Button) findViewById(R.id.add1_2);
        add1_3 = (Button) findViewById(R.id.add1_3);
        comfirmcourse = (TextView) findViewById(R.id.comfirmcourse);
        Semster = (TextView) findViewById(R.id.Semster);

        course1_1 = (TextView) findViewById(R.id.course1_1);
        course1_2 = (TextView) findViewById(R.id.course1_2);
        course1_3 = (TextView) findViewById(R.id.course1_3);

        scourse = new SemsterCourse();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        term = getIntent().getIntExtra("term", 0);
        if (term == 3 || term == 6 || term == 9) {
            add1_3.setVisibility(View.INVISIBLE);
            course1_3.setVisibility(View.INVISIBLE);
        } else {
            add1_3.setVisibility(View.VISIBLE);
            course1_3.setVisibility(View.VISIBLE);
        }
        Term = Integer.toString(term);
        changeTerm(term);
        showCourse1();
        showCourse2();
        showCourse3();




        add1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search1();
            }
        });

        add1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search2();
            }
        });

        add1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search3();
            }
        });
        adapter = new ArrayAdapter<String>(this,R.layout.degree_overview_format,R.id.degreeOverviewInfo, list);
        ref = FirebaseDatabase.getInstance().getReference("User").child(UserID);


        list = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String course1 = ds.child("course1").getValue(String.class);
                    String course2 = ds.child("course2").getValue(String.class);
                    String course3 = ds.child("course3").getValue(String.class);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        comfirmcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getValue();


                if (list.contains(scourse.getCourse1())) {
                    Toast.makeText(Year1_term1.this, "You have planned " + scourse.getCourse1() + " already", Toast.LENGTH_LONG).show();

                } else if (list.contains(scourse.getCourse2())) {
                    Toast.makeText(Year1_term1.this, "You have planned " + scourse.getCourse2() + " already", Toast.LENGTH_LONG).show();

                } else if (list.contains(scourse.getCourse3())) {
                    Toast.makeText(Year1_term1.this, "You have planned " + scourse.getCourse3() + " already", Toast.LENGTH_LONG).show();

                } else {
                    uploadcourse();
                    Toast.makeText(Year1_term1.this, "Successfully Upload", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Year1_term1.this, ProgressionPlanner.class));
                }
            }



    });
        back.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        startActivity(new Intent(Year1_term1.this, ProgressionPlanner.class));
    }
    });

}


    private void showCourse3() {
        if (term != 3 || term != 6 || term != 9) {
            try {
                reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            course3 = dataSnapshot.child("Term" + Term).child("course3").getValue().toString();
                            course1_3.setText(course3);
                        } catch (Exception e) {
                            course1_3.setText("course 3");

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } catch (Exception e) {
                course1_3.setText("course 3");


            }
        }
    }

    private void showCourse2() {
        try {
            reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        course2 = dataSnapshot.child("Term" + Term).child("course2").getValue().toString();
                        course1_2.setText(course2);
                    } catch (Exception e) {
                        course1_2.setText("course 2");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } catch (Exception e) {
            course1_2.setText("course 2");
        }
    }

    private void showCourse1() {
        try {
            reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        course1 = dataSnapshot.child("Term" + Term).child("course1").getValue().toString();
                        course1_1.setText(course1);
                    } catch (Exception e) {
                        course1_1.setText("course 1");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } catch (Exception e) {
            course1_1.setText("course 1");
        }
    }

    private void changeTerm(int i) {
        switch (term) {
            case 1:
                Semster.setText("Year1 term1");
                break;
            case 2:
                Semster.setText("Year1 term2");
                break;
            case 3:
                Semster.setText("Year1 term3");
                break;
            case 4:
                Semster.setText("Year2 term1");
                break;
            case 5:
                Semster.setText("Year2 term2");
                break;
            case 6:
                Semster.setText("Year2 term3");
                break;
            case 7:
                Semster.setText("Year3 term1");
                break;
            case 8:
                Semster.setText("Year3 term2");
                break;
            case 9:
                Semster.setText("Year3 term3");
                break;

        }

    }

    private void uploadcourse() {
        mDatabase = FirebaseDatabase.getInstance().getReference("User/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(UserID).child("Term" + Term).setValue(scourse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getValue() {
        scourse.setCourse1(course1_1.getText().toString());
        scourse.setCourse2(course1_2.getText().toString());
        if (term != 3 || term != 6 || term != 9) {


            scourse.setCourse3(course1_3.getText().toString());
        }
    }

    private void Search1() {
        Intent intent = new Intent(Year1_term1.this, Search.class);
        intent.putExtra("term", term);
        startActivityForResult(intent, 1);

    }

    private void Search2() {
        Intent intent = new Intent(Year1_term1.this, Search.class);
        intent.putExtra("term", term);
        startActivityForResult(intent, 2);

    }

    private void Search3() {
        Intent intent = new Intent(Year1_term1.this, Search.class);
        intent.putExtra("term", term);
        startActivityForResult(intent, 3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String course1 = data.getStringExtra("course1");
                course1_1.setText(course1);
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String course1 = data.getStringExtra("course1");
                course1_2.setText(course1);
            }
        }
        if (term != 3 || term != 6 || term != 9) {


            if (requestCode == 3) {
                if (resultCode == RESULT_OK) {
                    String course1 = data.getStringExtra("course1");
                    course1_3.setText(course1);
                }
            }
        }
    }


}
