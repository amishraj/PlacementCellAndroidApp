package com.example.hp.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminProfile3 extends AppCompatActivity implements View.OnClickListener{

    private TextView adminName, adminAge, adminEmail, adminCgpa, adminColid, adminPhone, adminPassword, adminDesignation;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button adminLogout, btnDetailsUser, btnDetailsCg;
    private CardView addCompany,listcomp, adminUpdate, editCompany, delCompany;

    private TextView detailsByCg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile1);

        addCompany= findViewById(R.id.addNewCompany);
        adminName= findViewById(R.id.tvAdminName);
        adminAge= findViewById(R.id.tvAdminAge);
        adminEmail= findViewById(R.id.tvAdminEmail);
        adminDesignation= findViewById(R.id.tvAdminDesignation);
        adminColid= findViewById(R.id.tvAdminColid);
        adminPhone= findViewById(R.id.tvAdminPhone);
        editCompany= findViewById(R.id.editcompany);
        delCompany= findViewById(R.id.delete_company);
        adminUpdate= findViewById(R.id.btnAdminUpdate);
        listcomp= findViewById(R.id.listcompany);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this,AdminLogin.class));
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
                        if(status.contentEquals("no")){
                            String Name= document.getString("Name");
                            adminName.setText(Name);
                            String dob= document.getString("DateOfBirth");
                            adminAge.setText(dob);
                            String cgpa= document.getString("Designation");
                            adminDesignation.setText(cgpa);
                            String colid= document.getString("CollegeID");
                            adminColid.setText(colid);
                            String mail= document.getString("Email");
                            adminEmail.setText(mail);
                            String phone= document.getString("Phone");
                            adminPhone.setText(phone);
                        }
                        else{
                            Toast.makeText(AdminProfile3.this, "NOT A STUDENT", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(AdminProfile3.this, ProfileActivity.class));
                        }
                    }
                    else {
                        Toast.makeText(AdminProfile3.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


        //initializing views
        adminLogout = (Button) findViewById(R.id.adminLogout);
        adminUpdate.setOnClickListener(this);

        //displaying logged in user name

        //adding listener to button
        listcomp.setOnClickListener(this);
        addCompany.setOnClickListener(this);
        editCompany.setOnClickListener(this);
        adminLogout.setOnClickListener(this);
        delCompany.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        //if logout is pressed
        if(view == adminLogout){
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
        else if(view== addCompany){
            startActivity(new Intent(this, CreateList.class));

        }
        if(view== listcomp){
            startActivity(new Intent(this, SelectListCompany.class));
        }

        if(view== editCompany){
            startActivity(new Intent(this, EditCompany.class));

        }
        if(view== delCompany){
            startActivity(new Intent(this, DeleteCompany.class));

        }


    }
}
