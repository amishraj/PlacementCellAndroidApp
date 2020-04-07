package com.example.hp.homepage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class studentlogin extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup, DispView, textViewForgotPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button tpp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
        FirebaseApp.initializeApp(studentlogin.this);

        firebaseAuth= FirebaseAuth.getInstance();


        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog= new ProgressDialog(this);

        loginButton= findViewById(R.id.login_button);
        tpp= findViewById(R.id.placementpolicy);
        editTextEmail= findViewById(R.id.login_mail);
        editTextPassword= findViewById(R.id.login_password);

        textViewSignup= findViewById(R.id.register);
        textViewForgotPassword= findViewById(R.id.forgot_password);

        tpp.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);//checking if it is getting clicked or not
        textViewForgotPassword.setOnClickListener(this);//checking if it is getting clicked or not

    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and passwod are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            checkEmailVerification();
                        }
                        else{

                            Toast.makeText(studentlogin.this, "Please Enter Correct Email/Password",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                });

    }
    @Override
    public void onClick(View view) {
        Intent i;

        if(view == loginButton){
            userLogin();
        }
        if(view == textViewSignup){
          i= new Intent(this, StudentRegister1.class); startActivity(i);
        }
        if(view == textViewForgotPassword){
            i= new Intent(this, forgot_password.class); startActivity(i);
        }
        if(view == tpp){
            i= new Intent(this, Placement_Policy.class); startActivity(i);
        }

    }

    private void checkstatus(){
        firebaseAuth = FirebaseAuth.getInstance();
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
                            finish();
                            startActivity(new Intent(studentlogin.this, ProfileActivity.class));
                        }
                        else{
                            Toast.makeText(studentlogin.this,"Invalid Student Credentials",Toast.LENGTH_LONG).show();
                            firebaseAuth.signOut();
                            finish();
                        }

                    } else {
                        Toast.makeText(studentlogin.this, "Error Retrieving User Data", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser= firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag= firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            checkstatus();
        }
        else{
            Toast.makeText(this,"You have not verified your Email ID",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
    }
}
