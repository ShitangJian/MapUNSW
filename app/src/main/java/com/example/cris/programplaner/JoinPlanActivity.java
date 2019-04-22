package com.example.cris.programplaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cris.programplaner.model.CoreCourse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JoinPlanActivity extends AppCompatActivity {
    TextView T1, T2, T3, T4, T5, T6, T7, T8, T9;
    SemsterCourse scourse;
    String UserID, course,course3,course2,course1,condition1,condition2,condition3;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    DatabaseReference reff,refff;
    CoreCourse coursedetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        T1 = (TextView) findViewById(R.id.Y1T1);
        T2 = (TextView) findViewById(R.id.Y1T2);
        T3 = (TextView) findViewById(R.id.Y1T3);
        T4 = (TextView) findViewById(R.id.Y2T1);
        T5 = (TextView) findViewById(R.id.Y2T2);
        T6 = (TextView) findViewById(R.id.Y2T3);
        T7 = (TextView) findViewById(R.id.Y3T1);
        T8 = (TextView) findViewById(R.id.Y3T2);
        T9 = (TextView) findViewById(R.id.Y3T3);
        scourse = new SemsterCourse();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        course = RetrieveCourse.selectedCourse;
        coursedetail= new CoreCourse();
        refff = FirebaseDatabase.getInstance().getReference("Course");
        refff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                condition1 = dataSnapshot.child(course).child("T1").getValue().toString();
                if (condition1.equals("TRUE")){
                    T1.setBackgroundResource(R.drawable.button);
                    T1.isClickable();
                    T4.setBackgroundResource(R.drawable.button);
                    T4.isClickable();
                    T7.setBackgroundResource(R.drawable.button);
                    T7.isClickable();
                }
                condition2 = dataSnapshot.child(course).child("T2").getValue().toString();
                if (condition2.equals("TRUE")){
                    T2.setBackgroundResource(R.drawable.button);
                    T2.isClickable();
                    T5.setBackgroundResource(R.drawable.button);
                    T5.isClickable();
                    T8.setBackgroundResource(R.drawable.button);
                    T8.isClickable();
                }
                condition3 = dataSnapshot.child(course).child("T3").getValue().toString();
                if (condition3.equals("TRUE")){
                    T3.setBackgroundResource(R.drawable.button);
                    T3.isClickable();
                    T6.setBackgroundResource(R.drawable.button);
                    T6.isClickable();
                    T9.setBackgroundResource(R.drawable.button);
                    T9.isClickable();
                }}
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(1);

            }
        });

        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(2);
            }
        });
        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(3);
            }
        });
        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(4);
            }
        });
        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(5);
            }
        });
        T6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(6);
            }
        });
        T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(7);
            }
        });
        T8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(8);
            }
        });
        T9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCourse(9);
            }
        });

    }

    private void checkCourse(final int i) {
        final String c = Integer.toString(i);
        reff = FirebaseDatabase.getInstance().getReference("User").child(UserID);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                try{course1 = dataSnapshot.child("Term"+c).child("course1").getValue().toString();

        }
        catch (Exception e) {
            scourse.setCourse1(course);
            uploadcourse(i);
            startActivity(new Intent(JoinPlanActivity.this,course_overview.class));

        }
        try{course2 = dataSnapshot.child("Term"+c).child("course2").getValue().toString();

        }
        catch (Exception e) {
            scourse.setCourse1(course);
            uploadcourse(i);
            startActivity(new Intent(JoinPlanActivity.this,course_overview.class));

        }
        try{course3 = dataSnapshot.child("Term"+c).child("course3").getValue().toString();
            Toast.makeText(JoinPlanActivity.this,"This semster is full",Toast.LENGTH_LONG).show();

        }
        catch (Exception e) {
            scourse.setCourse1(course);
            uploadcourse(i);
            startActivity(new Intent(JoinPlanActivity.this,course_overview.class));

        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadcourse(int a) {
        mDatabase = FirebaseDatabase.getInstance().getReference("User/");
        final String b = Integer.toString(a);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(UserID).child("Term"+b).setValue(scourse);
                Toast.makeText(JoinPlanActivity.this,"Sucessfully upload",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
