package com.example.hp.homepage;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class adminupdate extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Button admin_button, admin_pass;
    private EditText admincolid, admindesign, adminmail, admin_nm, adminphone;
    Spinner admingender;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    String colid, name, status, dob, phone, strGender, email, designation;

    Button adminage;
    String nameget, getage, design, collegeid, emailget, phoneget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminupdate);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        admin_button= findViewById(R.id.admin_button);
        admin_pass= findViewById(R.id.admin_pass);
        adminage= findViewById(R.id.admin_age);
        admincolid= findViewById(R.id.admin_collegeid);
        admindesign= findViewById(R.id.admin_design);
        adminphone= findViewById(R.id.admin_phone);
        admingender= findViewById(R.id.admin_gender1);
        adminmail= findViewById(R.id.admin_mail1);
        admin_nm= findViewById(R.id.admin_name);
        status= "no";

        admin_button.setOnClickListener(this);
        admin_pass.setOnClickListener(this);
        adminage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker= new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        FirebaseFirestore db= FirebaseFirestore.getInstance();

        String userId= firebaseAuth.getUid();
        DocumentReference docRef = db.collection("Users").document(userId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String status = document.getString("Status");
                        if(status.contentEquals("no")){
                            nameget = document.getString("Name");
                            getage= document.getString("DateOfBirth");
                            design= document.getString("Designation");
                            collegeid= document.getString("CollegeID");
                            emailget= document.getString("Email");
                            phoneget= document.getString("Phone");
                        }
                        else{
                            Toast.makeText(adminupdate.this, "NOT A STUDENT", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(adminupdate.this, AdminProfile3.class));
                        }

                    } else {
                        Toast.makeText(adminupdate.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


    }

    private void updateadmin(){
        name= admin_nm.getText().toString().trim();
        phone= adminphone.getText().toString().trim();
        colid= admincolid.getText().toString().trim();
        designation = admindesign.getText().toString().trim();
        strGender = admingender.getSelectedItem().toString();
        email= adminmail.getText().toString().trim();

        if(TextUtils.isEmpty(colid)){
            colid= collegeid;
        }

        if(TextUtils.isEmpty(designation)){
            designation = design;
        }

        if(TextUtils.isEmpty(dob)){
            dob= getage;
        }

        if(TextUtils.isEmpty(phone)){
            phone= phoneget;
        }

        if(TextUtils.isEmpty(name)){
            name= nameget;
        }

        if(TextUtils.isEmpty(email)){
            email= emailget;
        }

        if(TextUtils.isEmpty(strGender)){
            Toast.makeText(this,"Please enter gender",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Changing Details Please Wait...");
        progressDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Phone", phone);
        user.put("DateOfBirth", dob);
        user.put("Designation", designation);
        user.put("CollegeID", colid);
        user.put("Email", email);
        user.put("Gender", strGender);
        user.put("Status", status);

        // Add a new document with a generated ID
        db.collection("Users").document(firebaseAuth.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(adminupdate.this,"Edit Profile Success!",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(adminupdate.this, AdminProfile3.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(adminupdate.this,"Edit Profile Error!",Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onClick(View v) {

        if(v == admin_button){
            updateadmin();

        }
        else if(v== admin_pass){
            startActivity(new Intent(adminupdate.this, ChangeAdminPass.class));

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
