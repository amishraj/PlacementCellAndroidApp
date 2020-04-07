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
import android.widget.TextView;
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

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Button Edit_button, Edit_pass;
    private EditText editemail, editpassword, editcolid, editnm, editcgpa, editphone, editTenscore, editTwelvescore;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    String colid, name, status, dob, phone, tenscore, twelvescore, cgpa, strGender, strGapYear, strCategory, strpwd, branch, email;
    Spinner editspin_gender;
    Spinner editgapyear;
    Spinner editcategory;
    Spinner editpwd;
    Spinner editbranch;

    TextView dispit;

    Button editAge;

    String collegeid, cg, getage, phoneget, nameget, emailget, branchget, tenget, twget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        editpwd= findViewById(R.id.edit_pwd1);
        Edit_button= findViewById(R.id.Edit_button);
        Edit_pass= findViewById(R.id.button_pass);
        editAge= findViewById(R.id.Edit_age);
        editcolid = findViewById(R.id.Edit_collegeid);
        editemail= findViewById(R.id.edit_mail1);
        editnm = findViewById(R.id.Edit_name);
        editcgpa = findViewById(R.id.edit_cgpa);
        editphone = findViewById(R.id.Edit_phone);
        editTenscore = findViewById(R.id.Edit_10thscore);
        editTwelvescore = findViewById(R.id.Edit_12thscore);
        editspin_gender = findViewById(R.id.edit_gender1);
        editgapyear = findViewById(R.id.edit_gapyear1);
        editcategory = findViewById(R.id.edit_category1);
        editbranch= findViewById(R.id.edit_branch1);
        status = "yes"; //yes for student, no for admin

        Edit_pass.setOnClickListener(this);
        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker= new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        Edit_button.setOnClickListener(this);

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
                        if(status.contentEquals("yes")){
                             nameget = document.getString("Name");
                             getage= document.getString("DateOfBirth");
                             cg= document.getString("CGPA");
                             collegeid= document.getString("CollegeID");
                             emailget= document.getString("Email");
                             phoneget= document.getString("Phone");
                             branchget= document.getString("Branch");
                             tenget= document.getString("Tenth Score");
                             twget= document.getString("Twelve Score");
                        }
                        else{
                            Toast.makeText(EditUserProfile.this, "NOT A STUDENT", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(EditUserProfile.this, ProfileActivity.class));
                        }

                    } else {
                        Toast.makeText(EditUserProfile.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    private void edituser(){

        name= editnm.getText().toString().trim();
        phone= editphone.getText().toString().trim();
        cgpa= editcgpa.getText().toString().trim();
        colid= editcolid.getText().toString().trim();
        tenscore = editTenscore.getText().toString().trim();
        twelvescore = editTwelvescore.getText().toString().trim();
        strGender = editspin_gender.getSelectedItem().toString();
        strGapYear = editgapyear.getSelectedItem().toString();
        strCategory = editcategory.getSelectedItem().toString();
        strpwd = editpwd.getSelectedItem().toString();
        branch= editbranch.getSelectedItem().toString();
        email= editemail.getText().toString().trim();

        if(TextUtils.isEmpty(colid)){
        colid= collegeid;
        }

        if(TextUtils.isEmpty(cgpa)){
        cgpa = cg;
        }
        if(TextUtils.isEmpty(dob)){
            dob = getage;
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
        if(TextUtils.isEmpty(branch)){
            branch= branchget;
        }
        if(TextUtils.isEmpty(tenscore)){
            tenscore= tenget;
        }
        if(TextUtils.isEmpty(twelvescore)){
            twelvescore= twget;
        }
        if(TextUtils.isEmpty(strpwd)){
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

        progressDialog.setMessage("Changing Details Please Wait...");
        progressDialog.show();

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
        user.put("Disability", strpwd);
        user.put("Gap Year", strGapYear);
        user.put("Category", strCategory);
        user.put("Status", status);


// Add a new document with a generated ID
        db.collection("Users").document(firebaseAuth.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(EditUserProfile.this,"Edit Profile Success!",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(EditUserProfile.this, ProfileActivity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserProfile.this,"Edit Profile Error!",Toast.LENGTH_LONG).show();
                    }
                });

    }
    @Override
    public void onClick(View view) {

        if(view == Edit_button){
            edituser();
        }
        else if(view == Edit_pass){
            startActivity(new Intent(EditUserProfile.this, ChangePass.class));
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
