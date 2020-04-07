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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView DispView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth1;
    private Button tpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        firebaseAuth1= FirebaseAuth.getInstance();


        //if getCurrentUser does not returns null
        if(firebaseAuth1.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), AdminProfile3.class));
        }


        progressDialog= new ProgressDialog(this);

        loginButton= findViewById(R.id.admin_button);
        editTextEmail= findViewById(R.id.admin_name);
        tpp= findViewById(R.id.adminpolicy);
        editTextPassword= findViewById(R.id.admin_pass);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loginButton.setOnClickListener(this);
        tpp.setOnClickListener(this);
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


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth1.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            checkstatus();

                        }
                        else{
                            Toast.makeText(AdminLogin.this, "Please Enter Correct Email/Password",
                                    Toast.LENGTH_LONG).show();
                            return;

                        }

                    }
                });

    }

    private void checkstatus(){
        firebaseAuth1 = FirebaseAuth.getInstance();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        String userId= firebaseAuth1.getUid();
        DocumentReference docRef = db.collection("Users").document(userId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String status = document.getString("Status");
                        if(status.contentEquals("no")){
                            finish();
                            startActivity(new Intent(AdminLogin.this, AdminProfile3.class));
                        }
                        else{
                            Toast.makeText(AdminLogin.this,"Invalid Administrator Credentials",Toast.LENGTH_LONG).show();
                            firebaseAuth1.signOut();
                            finish();
                        }

                    } else {
                        Toast.makeText(AdminLogin.this, "Error Retrieving User Data", Toast.LENGTH_LONG).show();
                    }

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
        if(view==tpp){
            i= new Intent(this, Placement_Policy.class); startActivity(i);
        }

    }
}
