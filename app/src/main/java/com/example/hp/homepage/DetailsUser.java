package com.example.hp.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.w3c.dom.Text;


public class DetailsUser extends AppCompatActivity implements View.OnClickListener{

    private TextView detailsUser;
    private EditText inputDetailsUser;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    //firebase database object
    private FirebaseDatabase firebaseDatabase;
    private Button userIdQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_user);

        //Select * from Users WHERE ID= '2017ucp1009'

        userIdQuery= (Button) findViewById(R.id.userIdQuery);
        detailsUser= (TextView) findViewById(R.id.detailsUser);
        inputDetailsUser= (EditText) findViewById(R.id.inputDetailsUser);


        userIdQuery.setOnClickListener(this);

    }


    public void userQuery(){

        String name  =  inputDetailsUser.getText().toString();
        Query query= FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("CollegeID")
                .equalTo(name);

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter Institute ID", Toast.LENGTH_LONG).show();
            return;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String details= dataSnapshot.getValue().toString();

                if(TextUtils.isEmpty(details)){
                    Toast.makeText(DetailsUser.this,"Please enter Correct ID", Toast.LENGTH_LONG).show();
                    return;
                }

                detailsUser.setText(details);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(DetailsUser.this, "Please Enter Correct ID", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view == userIdQuery){
            userQuery();
        }

    };
}
