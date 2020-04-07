package com.example.hp.homepage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class EditCse extends AppCompatActivity implements companyAdapter.OnItemClickListener{

    Dialog myDialog;
    Button refreshButton;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference companyRef = db.collection("Companies");
    private companyAdapter adapter;

    Spinner companylist_branches;
    String branch_search;

    TextView textView, dispplayid;
    TextView display;
    FirebaseAuth firebaseAuth;
    String cgpa, userId,data, mycg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cse);


        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        myDialog= new Dialog(this);

        setUpRecyclerView();
    }

    public void setUpRecyclerView(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        data="10";
        Float result= Float.valueOf(data);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Computer Science and Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.editcse_recycler);
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
        TextView description;
        Button btnapply;
        myDialog.setContentView(R.layout.custompupup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        PopupName= (TextView) myDialog.findViewById(R.id.popupnm);
        cgdisp= (TextView) myDialog.findViewById(R.id.popupcg);
        description= (TextView) myDialog.findViewById(R.id.popupdesc);

        Company company= documentSnapshot.toObject(Company.class);
        String nm= documentSnapshot.getId();
        final String descpop= documentSnapshot.getString("description");


        double cg= documentSnapshot.getDouble("mincg");
        final String dispcg= String.valueOf(cg);

        PopupName.setText(nm);
        cgdisp.setText(dispcg);
        description.setText(descpop);

        btnapply = (Button) myDialog.findViewById(R.id.btnapply);

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditCse.this, "Showing Edit Company Page...", Toast.LENGTH_SHORT).show();
                GlobalClass globalClass= (GlobalClass) getApplicationContext();
                globalClass.setCompanyid(documentSnapshot.getId());
                globalClass.setCompany_cg_criteria(dispcg);
                globalClass.setCompany_description(descpop);

                Intent i;
                i= new Intent(EditCse.this, EditHere.class); startActivity(i);

            }
        });


        //
        txtclose.setText("X");
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
