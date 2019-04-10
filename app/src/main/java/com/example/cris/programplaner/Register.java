package com.example.cris.programplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Register extends AppCompatActivity implements View.OnClickListener {
    String[] degree = {"Information System"};
    ArrayAdapter<String> arrayAdapter;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword,editName;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private ImageButton imageButton;
    private StorageReference storageReference;
    DatabaseReference ref;
    String imageUri;
    Uri selectdImage;
    User user;
    Spinner programSelect;
    String UserID;

    private static final int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarUtil.immersive(this);
        programSelect = (Spinner) findViewById(R.id.ProgramSelect);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, degree);
        programSelect.setAdapter(arrayAdapter);

        mAuth = FirebaseAuth.getInstance();

        //create user
        user = new User();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editName = (EditText)findViewById((R.id.editName));

        buttonSignup = (Button) findViewById(R.id.tv_register);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uploadImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImage, RESULT_LOAD_IMAGE);
            }
        });



/*
        imagePath = getIntent().getStringExtra("imagePath");
        ImagePath = Uri.parse(imagePath);
        imageButton.setImageURI(Uri.parse(imagePath));



        byteArray = getIntent().getByteArrayExtra("Bitmap");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageButton.setImageBitmap(bmp);
*/

    }

    private void registerUser() {

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            uploadimage();
                            uploadDetail();

                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            //display some message here
                            Toast.makeText(Register.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void uploadDetail() {
        ref = FirebaseDatabase.getInstance().getReference("User");
        getValue();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ref.child(UserID).setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getValue() {
        user.setName(editName.getText().toString());
        user.setEmail(editTextEmail.getText().toString());
        user.setProgram(programSelect.getSelectedItem().toString());
    }

    private void uploadimage() {
        UserID = mAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("profile/" + UserID + ".jpg");

        if (selectdImage != null) {
            storageReference.putFile(selectdImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           imageUri = taskSnapshot.getUploadSessionUri().toString();
                        }
                    });
        }



    }


    @Override
    public void onClick(View view) {
        registerUser();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            selectdImage = data.getData();
            imageButton.setImageURI(selectdImage);

        }
    }
}