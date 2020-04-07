package com.example.hp.homepage;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DetailsCg extends AppCompatActivity implements View.OnClickListener {

    private TextView detailsCg;
    private EditText inputDetailsCg, inputMaxCg;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    //firebase database object
    private FirebaseDatabase firebaseDatabase;
    private Button userCgQuery, userMaxCgQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_cg);

        //Select * from Users WHERE ID= '2017ucp1009'
        userMaxCgQuery= (Button) findViewById(R.id.userMaxCgQuery);
        userCgQuery= (Button) findViewById(R.id.userCgQuery);
        detailsCg= (TextView) findViewById(R.id.detailsCg);

        inputMaxCg= (EditText) findViewById(R.id.inputMaxCg);
        inputDetailsCg= (EditText) findViewById(R.id.inputDetailsCg);

        userMaxCgQuery.setOnClickListener(this);
        userCgQuery.setOnClickListener(this);

    }

    public void mincgQuery(){

        String name  =  inputDetailsCg.getText().toString();
        Query query1= FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("CGPA")
                .startAt(name);
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter CGPA", Toast.LENGTH_LONG).show();
            return;
        }

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String details= dataSnapshot.getValue().toString();

                if(TextUtils.isEmpty(details)){
                    Toast.makeText(DetailsCg.this,"Please enter Correct ID", Toast.LENGTH_LONG).show();
                    return;
                }

                detailsCg.setText(details);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(DetailsCg.this, "Please Enter Correct ID", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    public void maxcgQuery(){

        String name  =  inputMaxCg.getText().toString();
        Query query= FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("CGPA")
                .endAt(name);
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter CGPA", Toast.LENGTH_LONG).show();
            return;
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String details= dataSnapshot.getValue().toString();

                if(TextUtils.isEmpty(details)){
                    Toast.makeText(DetailsCg.this,"Please enter Correct ID", Toast.LENGTH_LONG).show();
                    return;
                }

                detailsCg.setText(details);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(DetailsCg.this, "Please Enter Correct ID", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == userCgQuery){
            mincgQuery();
        }

        if(view == userMaxCgQuery){
            maxcgQuery();
        }

    }
}
