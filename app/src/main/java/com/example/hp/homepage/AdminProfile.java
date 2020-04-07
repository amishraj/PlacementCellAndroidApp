package com.example.hp.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity implements OnClickListener {

    private TextView profileName, profileAge, profileEmail, profileCgpa, profileColid, profilePhone, profilePassword;
    private Button profileUpdate, adminUpdate;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    //firebase database object
    private FirebaseDatabase firebaseDatabase;
    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName= findViewById(R.id.tvProfileName);
        profileAge= findViewById(R.id.tvProfileAge);
        profileEmail= findViewById(R.id.tvProfileEmail);
        profileCgpa= findViewById(R.id.tvProfileCgpa);
        profileColid= findViewById(R.id.tvProfileColid);
        profilePhone= findViewById(R.id.tvProfilePhone);
        profileUpdate= findViewById(R.id.btnProfileUpdate);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //initializing firebase databse object
        firebaseDatabase= FirebaseDatabase.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, AdminLogin.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        adminUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, AdminLogin.class));
        }

        else if(view== adminUpdate){

            startActivity(new Intent(this, adminupdate.class));

        }

    }
}
