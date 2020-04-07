package com.example.hp.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentRegister extends AppCompatActivity implements View.OnClickListener {
//NOT USED










    /*









 */
    Button loginButton;
    private EditText editTextEmail, userAge, usercolid, usernm, usercgpa, userphone, userBranch, userTenscore, userTwelvescore;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference mDatabase;
    private DatabaseReference mMessageReference;

    Spinner spinner_gender;
    Spinner spinner_pwd;
    Spinner spinner_gapyear;
    Spinner spinner_category;

    String name, phone, age, cgpa, colid, email, password, strGender, branch, tenscore, twelvescore, strPwd, strGapYear, strCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        firebaseAuth= FirebaseAuth.getInstance();

        progressDialog= new ProgressDialog(this);

        loginButton= findViewById(R.id.login_button1);
        editTextEmail= findViewById(R.id.login_mail1);
        editTextPassword= findViewById(R.id.login_password1);
        userAge= findViewById(R.id.register_age1);
        usernm=  findViewById(R.id.register_name1);
        usercgpa=  findViewById(R.id.register_cgpa1);
        usercolid= findViewById(R.id.register_collegeid1);
        userphone= findViewById(R.id.register_phone1);
        userBranch= findViewById(R.id.register_branch1);
        userTenscore= findViewById(R.id.register_10thscore1);
        userTwelvescore= findViewById(R.id.register_12thscore1);
        spinner_gender=findViewById(R.id.register_gender1);
        spinner_pwd=findViewById(R.id.register_pwd1);
        spinner_gapyear=findViewById(R.id.register_gapyear1);
        spinner_category=findViewById(R.id.register_category1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMessageReference = FirebaseDatabase.getInstance().getReference("Users");


        loginButton.setOnClickListener(this);

    }

    private void registerUser(){

        //getting registration details from edit texts
        name= usernm.getText().toString().trim();
        phone= userphone.getText().toString().trim();
        age= userAge.getText().toString().trim();
        cgpa= usercgpa.getText().toString().trim();
        colid= usercolid.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        branch = userBranch.getText().toString().trim();
        tenscore = userTenscore.getText().toString().trim();
        twelvescore = userTwelvescore.getText().toString().trim();
        strGender = spinner_gender.getSelectedItem().toString();
        strPwd = spinner_pwd.getSelectedItem().toString();
        strGapYear = spinner_gapyear.getSelectedItem().toString();
        strCategory = spinner_category.getSelectedItem().toString();


        //checking if email and passwords are empty

        if(TextUtils.isEmpty(colid)){
            Toast.makeText(this,"Please enter college ID", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(cgpa)){
            Toast.makeText(this,"Please enter CGPA", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this,"Please enter age", Toast.LENGTH_LONG).show();
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
        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            sendEmailVerification();
                        }else{
                            //display some message here
                            Toast.makeText(StudentRegister.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
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

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());

        UserProfile userProfile= new UserProfile(age, colid, name, cgpa, phone, email, strGender, branch, tenscore, twelvescore, strPwd, strGapYear, strCategory);
        myref.setValue(userProfile);


    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(StudentRegister.this,"Successfully Registered, Verification mail has been sent",Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(StudentRegister.this, studentlogin.class));
                    }
                    else{
                        Toast.makeText(StudentRegister.this,"Error sending Verification mail",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
