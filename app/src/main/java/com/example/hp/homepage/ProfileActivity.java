package com.example.hp.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView profileName, profileAge, profileEmail, profileCgpa, profileColid, profilePhone;

    private CardView yourCompanies, buttonEditProfile, withdrawApp;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private Button buttonLogout;

    String cgpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        yourCompanies= findViewById(R.id.companies);
        profileName= findViewById(R.id.tvProfileName);
        profileAge= findViewById(R.id.tvProfileAge);
        profileEmail= findViewById(R.id.tvProfileEmail);
        profileCgpa= findViewById(R.id.tvProfileCgpa);
        profileColid= findViewById(R.id.tvProfileColid);
        profilePhone= findViewById(R.id.tvProfilePhone);
      //  withdrawApp= findViewById(R.id.withdrawApp);

         final GlobalClass globalClass= (GlobalClass)  getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, studentlogin.class));
        }

        //getting current user
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
                            String Name= document.getString("Name");
                            profileName.setText(Name);
                            String dob= document.getString("DateOfBirth");
                            profileAge.setText(dob);
                            cgpa= document.getString("CGPA");
                            profileCgpa.setText(cgpa);
                            String colid= document.getString("CollegeID");
                            profileColid.setText(colid);
                            String mail= document.getString("Email");
                            profileEmail.setText(mail);
                            String phone= document.getString("Phone");
                            profilePhone.setText(phone);
                            String branch= document.getString("Branch");
                            globalClass.setBranch(branch);
                        }
                        else{
                            Toast.makeText(ProfileActivity.this, "NOT A STUDENT", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(ProfileActivity.this, AdminProfile3.class));
                        }

                    } else {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                    } else {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        //initializing views
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonEditProfile =  findViewById(R.id.btnProfileUpdate);
        //displaying logged in user name

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonEditProfile.setOnClickListener(this);
        yourCompanies.setOnClickListener(this);
       // withdrawApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        //if EditProfile is pressed
        if(view == buttonEditProfile){
            startActivity(new Intent(this, EditUserProfile.class));
        }

        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, studentlogin.class));
        }

        if(view== yourCompanies){
            globalClass.setCgpa(cgpa);
            startActivity(new Intent(this, YourCompanies.class));
        }
        if(view== withdrawApp){
        }

    }
}
