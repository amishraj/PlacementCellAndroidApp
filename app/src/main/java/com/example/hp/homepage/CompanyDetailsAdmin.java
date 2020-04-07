package com.example.hp.homepage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class CompanyDetailsAdmin extends AppCompatActivity implements AppliedAdapter.OnItemClickListener{
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    TextView Name, CGPA, description;
    private CollectionReference appliedRef; //maybe not accessing the collection
    private AppliedAdapter adapter;

    Dialog myDialog;

    FirebaseAuth firebaseAuth;

    TextView textView, dispplayid;
    TextView display;
    String cgpa, userId,data, mycg;
    public String compid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details_admin);

        myDialog= new Dialog(this);


        CGPA= findViewById(R.id.companydescripcg);
        description= findViewById(R.id.companydescription);
        Name= findViewById(R.id.companynm);
        GlobalClass globalClass= (GlobalClass) getApplicationContext();

        Name.setText(globalClass.getCompanyid());
        CGPA.setText(globalClass.getCompany_cg_criteria());
        description.setText(globalClass.getCompany_description());

        appliedRef= db.collection("Companies").document(globalClass.getCompanyid()).collection(globalClass.getCompanyid() + "Applied Users");

        setuprecyclerview();

    }

    private void setuprecyclerview(){

        Query query = appliedRef.whereEqualTo("Application Status", "Applied");

        FirestoreRecyclerOptions<applied> options = new FirestoreRecyclerOptions.Builder<applied>()
                .setQuery(query, applied.class)
                .build();

        adapter = new AppliedAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.appliedstudents_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected  void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void OnItemCLick(final DocumentSnapshot documentSnapshot, int position) {
        TextView txtclose;
        TextView PopupName;
        TextView cgdisp;
        TextView colidpopup;
        TextView branchview;
        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        Button btnapplied, btntest, btnaccept, btnreject;
        myDialog.setContentView(R.layout.studpopup);

        txtclose =(TextView) myDialog.findViewById(R.id.stxtclose);
        PopupName= (TextView) myDialog.findViewById(R.id.spopupnm);
        cgdisp= (TextView) myDialog.findViewById(R.id.spopupcg);
        branchview= (TextView) myDialog.findViewById(R.id.spopupbranch);
        colidpopup= (TextView) myDialog.findViewById(R.id.spopupcolid);

        applied Applied= documentSnapshot.toObject(applied.class);
        String nm= documentSnapshot.getString("Name");
        String colid= documentSnapshot.getString("CollegeID");
        String cgpa= documentSnapshot.getString("CGPA");
        String branch= documentSnapshot.getString("Branch");

        PopupName.setText(nm);
        cgdisp.setText(cgpa);
        branchview.setText(branch);
        colidpopup.setText(colid);

        txtclose.setText("X");

        btnaccept= findViewById(R.id.sbtnaccepted);
        btnapplied= findViewById(R.id.sbtnapplied);
        btnreject= findViewById(R.id.sbtnrejected);
        btntest= findViewById(R.id.sbtntest);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
