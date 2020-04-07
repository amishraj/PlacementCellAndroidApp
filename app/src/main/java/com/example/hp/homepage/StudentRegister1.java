package com.example.hp.homepage;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentRegister1 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "StudentRegister1";


    Button loginButton;
    private EditText editTextEmail, usercolid, usernm, usercgpa, userphone, userBranch, userTenscore, userTwelvescore;
    private EditText editTextPassword, cnfpassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    Spinner spinner_gender;
    Spinner spinner_pwd;
    Spinner spinner_gapyear;
    Spinner spinner_category;
    Spinner spinner_branch;

    Button userdob;

    String name, phone, dob, cgpa, colid, email, password, password2, strGender, branch, tenscore, twelvescore, strPwd, strGapYear, strCategory, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register1);

        firebaseAuth= FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(this);

        loginButton= findViewById(R.id.login_button1);
        editTextEmail= findViewById(R.id.login_mail1);
        editTextPassword= findViewById(R.id.login_password1);
        cnfpassword= findViewById(R.id.confirm_password1);
        usernm=  findViewById(R.id.register_name1);
        usercgpa=  findViewById(R.id.register_cgpa1);
        usercolid= findViewById(R.id.register_collegeid1);
        userphone= findViewById(R.id.register_phone1);
        userdob= findViewById(R.id.register_age1);
        userTenscore= findViewById(R.id.register_10thscore1);
        userTwelvescore= findViewById(R.id.register_12thscore1);

        spinner_branch= findViewById(R.id.register_spinbranch);
        spinner_gender=findViewById(R.id.register_gender1);
        spinner_pwd=findViewById(R.id.register_pwd1);
        spinner_gapyear=findViewById(R.id.register_gapyear1);
        spinner_category=findViewById(R.id.register_category1);

        loginButton.setOnClickListener(this);
        userdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker= new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    private void registerUser(){
        //getting registration details from edit texts
        name= usernm.getText().toString().trim();
        phone= userphone.getText().toString().trim();
        cgpa= usercgpa.getText().toString().trim();
        colid= usercolid.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        password2= cnfpassword.getText().toString().trim();
        branch = spinner_branch.getSelectedItem().toString().trim();
        tenscore = userTenscore.getText().toString().trim();
        twelvescore = userTwelvescore.getText().toString().trim();
        strGender = spinner_gender.getSelectedItem().toString();
        strPwd = spinner_pwd.getSelectedItem().toString();
        strGapYear = spinner_gapyear.getSelectedItem().toString();
        strCategory = spinner_category.getSelectedItem().toString();
        status = "yes"; //yes for student, no for admin

        //checking if email and passwords are empty

        if(TextUtils.isEmpty(colid)){
            Toast.makeText(this,"Please enter college ID", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(cgpa)){
            Toast.makeText(this,"Please enter CGPA", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please enter phone", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter name", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this,"Please confirm password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(strGender)){
            Toast.makeText(this,"Please enter gender",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(branch)){
            Toast.makeText(this,"Please enter branch",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(tenscore)){
            Toast.makeText(this,"Please enter 10th Score",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(twelvescore)){
            Toast.makeText(this,"Please enter 12th Score",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(strPwd)){
            Toast.makeText(this,"Please enter people with disabilities",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(strGapYear)){
            Toast.makeText(this,"Please enter Gap Year",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(strCategory)){
            Toast.makeText(this,"Please enter Category",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        if(password.contentEquals(password2)) {
            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if success
                    if (task.isSuccessful()) {
                        //display some message here
                        sendEmailVerification();
                    } else {
                        //display some message here
                        Toast.makeText(StudentRegister1.this, "Registration Error", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(StudentRegister1.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

    }



    private void sendEmailVerification(){
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(StudentRegister1.this,"Successfully Registered, Verification mail has been sent",Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(StudentRegister1.this, studentlogin.class));
                    }
                    else{
                        Toast.makeText(StudentRegister1.this,"Error sending Verification mail",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Phone", phone);
        user.put("DateOfBirth", dob);
        user.put("CGPA", cgpa);
        user.put("CollegeID", colid);
        user.put("Email", email);
        user.put("Branch", branch);
        user.put("Tenth Score", tenscore);
        user.put("Twelve Score", twelvescore);
        user.put("Gender", strGender);
        user.put("Disability", strPwd);
        user.put("Gap Year", strGapYear);
        user.put("Category", strCategory);
        user.put("Status", status);

// Add a new document with a generated ID
        db.collection("Users").document(firebaseAuth.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentRegister1.this,"Registration Error!",Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        Intent i;

        if(view == loginButton){
            registerUser();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dob= currentDateString;


    }
}
