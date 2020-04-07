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

public class AdminProfile2 extends AppCompatActivity implements View.OnClickListener{
//NOT USED


    /*























 */
    private TextView adminName, adminAge, adminEmail, adminCgpa, adminColid, adminPhone, adminPassword;
    private Button adminUpdate;


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

        adminColid= findViewById(R.id.tvAdminColid);
        adminPhone= findViewById(R.id.tvAdminPhone);
        adminUpdate= findViewById(R.id.btnAdminUpdate);

        firebaseAuth = FirebaseAuth.getInstance();

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
                        String Name= document.getString("Name");
                        adminName.setText("mc");
                        String age= document.getString("Age");
                        adminAge.setText(age);
                        String cgpa= document.getString("CGPA");
                        adminCgpa.setText(cgpa);
                        String colid= document.getString("CollegeID");
                        adminColid.setText(colid);
                        String mail= document.getString("Email");
                        adminEmail.setText(mail);
                        String phone= document.getString("Phone");
                        adminPhone.setText(phone);

                    } else {
                        Toast.makeText(AdminProfile2.this, "Error", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        //initializing views
        adminLogout = (Button) findViewById(R.id.adminLogout);

        //displaying logged in user name

        //adding listener to button
        adminLogout.setOnClickListener(this);
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
            startActivity(new Intent(this, studentlogin.class));
        }

    }
}


