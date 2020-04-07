package com.example.hp.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class AdminProfile1 extends AppCompatActivity implements View.OnClickListener{
//NOT USED

    /*




























     */
    private TextView adminName, adminAge, adminEmail, adminCgpa, adminColid, adminPhone, adminPassword, adminDesignation;
    private Button adminUpdate;

    DatabaseReference dbUsers;


    private DatabaseReference mDatabase;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    //firebase database object
    private FirebaseDatabase firebaseDatabase;
    //view objects
    private TextView textViewUserEmail;
    private Button adminLogout, btnDetailsUser, btnDetailsCg, createNewList;

    private TextView detailsByCg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile1);

        adminName= findViewById(R.id.tvAdminName);
        adminAge= findViewById(R.id.tvAdminAge);
        adminEmail= findViewById(R.id.tvAdminEmail);
        adminDesignation= findViewById(R.id.tvAdminDesignation);
        adminColid= findViewById(R.id.tvAdminColid);
        adminPhone= findViewById(R.id.tvAdminPhone);
        adminUpdate= findViewById(R.id.btnAdminUpdate);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //initializing firebase databse object
        firebaseDatabase= FirebaseDatabase.getInstance();
        //initializing firestore database
        FirebaseFirestore db= FirebaseFirestore.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, AdminLogin.class));
        }


        //getting current user
        FirebaseUser admin = firebaseAuth.getCurrentUser();


        String userId= firebaseAuth.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Name");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name= dataSnapshot.getValue().toString();
                adminName.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //End Set Name

        //Set CGPA

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("CGPA");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String cgpa= dataSnapshot.getValue().toString();
                adminCgpa.setText(cgpa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //End Set CGPA

        //SET AGE
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Age");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String age= dataSnapshot.getValue().toString();
                adminAge.setText(age);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //End Set Age

        // SET COLLEGE ID
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("CollegeID");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String collegeid= dataSnapshot.getValue().toString();
                adminColid.setText(collegeid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //End Set College ID

        // Set Email

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Email");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String email= dataSnapshot.getValue().toString();
                adminEmail.setText(email);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //End Set Email

        //SET PHONE

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Phone");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phone= dataSnapshot.getValue().toString();
                adminPhone.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //End Set Phone

        //initializing views
        adminLogout = (Button) findViewById(R.id.adminLogout);
        //displaying logged in user name

        //adding listener to button
        createNewList.setOnClickListener(this);
        adminLogout.setOnClickListener(this);
        btnDetailsUser.setOnClickListener(this);
        btnDetailsCg.setOnClickListener(this);

        //Select * from Users
        dbUsers= FirebaseDatabase.getInstance().getReference("Users");

        //Select * from Users WHERE ID= '2017ucp1009'

    }

    @Override
    public void onClick(View view) {


        Intent i ;

        switch (view.getId()){
            case R.id.adminLogout :
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                i= new Intent(this,AdminLogin.class ); startActivity(i); break;
            default: break;
        }

    }
}
