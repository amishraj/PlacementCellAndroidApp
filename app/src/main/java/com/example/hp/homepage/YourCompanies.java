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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class YourCompanies extends AppCompatActivity implements companyAdapter.OnItemClickListener {

    Dialog myDialog;
    Button refreshButton;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference companyRef = db.collection("Companies");
    private companyAdapter adapter;

    TextView textView, dispplayid, dispthebranch;
    TextView display;
    FirebaseAuth firebaseAuth;
    String cgpa, userId,data, mycg, studbranch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_companies);
        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        display= findViewById(R.id.disp);

        myDialog= new Dialog(this);
        dispplayid= findViewById(R.id.dispme);
        dispthebranch=findViewById(R.id.dispbranch);

        dispplayid.setText(globalClass.getCgpa());
        data= dispplayid.getText().toString().trim();

        dispthebranch.setText(globalClass.getBranch());
        studbranch= dispthebranch.getText().toString().trim();

        Float result= Float.valueOf(data);
        String resultbranch= String.valueOf(studbranch);

        if((result>0 && result <=10)&&(resultbranch.equals("Computer Science and Engineering"))){
            setUpRecyclerView();
        }

        if((result>0 && result <=10)&&(resultbranch.equals("Electronics and Communication Engineering"))){
            setUpRecyclerView2();
        }

        if((result>0 && result <=10)&&(resultbranch.equals("Mechanical Engineering"))){
            setUpRecyclerView3();
        }
        if((result>0 && result <=10)&&(resultbranch.equals("Civil Engineering"))){
            setUpRecyclerView4();
        }
        if((result>0 && result <=10)&&(resultbranch.equals("Electrical Engineering"))){
            setUpRecyclerView5();
        }
        if((result>0 && result <=10)&&(resultbranch.equals("Chemical Engineering"))){
            setUpRecyclerView6();
        }
        if((result>0 && result <=10)&&(resultbranch.equals("Architecture and Planning"))){
            setUpRecyclerView7();
        }
        if((result>0 && result <=10)&&(resultbranch.equals("Metallurgical and Material Engineering"))){
            setUpRecyclerView8();
        }


    }
/*
    public void ShowPopup(View v) {

    } */

    public void setUpRecyclerView(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Computer Science and Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }

    public void setUpRecyclerView2(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Electronics and Communication Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView3(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Mechanical Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView4(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Civil Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView5(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Electrical Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView6(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Chemical Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView7(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Architecture and Planning","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void setUpRecyclerView8(){
    /* displayid= findViewById(R.id.disp);
    mycg = displayid.getText().toString(); */

        Float result= Float.valueOf(data);
        String resultbranch = String.valueOf(studbranch);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result).whereEqualTo("Metallurgical and Material Engineering","Yes");


        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<Company>()
                .setQuery(query, Company.class)
                .build();

        adapter = new companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
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
        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        Button btnapply;
        myDialog.setContentView(R.layout.custompupup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        PopupName= (TextView) myDialog.findViewById(R.id.popupnm);
        cgdisp= (TextView) myDialog.findViewById(R.id.popupcg);
        description= (TextView) myDialog.findViewById(R.id.popupdesc);

        Company company= documentSnapshot.toObject(Company.class);
        String nm= documentSnapshot.getId();
        String descpop= documentSnapshot.getString("description");

        double cg= documentSnapshot.getDouble("mincg");
        String dispcg= String.valueOf(cg);

        PopupName.setText(nm);
        cgdisp.setText(dispcg);
        description.setText(descpop);

        txtclose.setText("X");

        btnapply = (Button) myDialog.findViewById(R.id.btnapply);

        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(YourCompanies.this, "Applying, Please Wait...", Toast.LENGTH_SHORT).show();
                firebaseAuth= FirebaseAuth.getInstance();
                if(firebaseAuth.getCurrentUser() == null){
                    //closing this activity
                    finish();
                    //starting login activity
                    startActivity(new Intent(YourCompanies.this, studentlogin.class));
                }

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
                                    String dob= document.getString("DateOfBirth");
                                    String ccgpa= document.getString("CGPA");
                                    String colid= document.getString("CollegeID");
                                    String mail= document.getString("Email");
                                    String phone= document.getString("Phone");

                                    FirebaseFirestore datab = FirebaseFirestore.getInstance();

                                    Map<String, Object> applied = new HashMap<>();
                                    applied.put("Name", Name);
                                    applied.put("DateOfBirth", dob);
                                    applied.put("CGPA", ccgpa);
                                    applied.put("CollegeID", colid);
                                    applied.put("Email", mail);
                                    applied.put("Phone", phone);
                                    applied.put("Application Status", "Applied");

                                    datab.collection("Companies").document(documentSnapshot.getId()).collection(documentSnapshot.getId() + "Applied Users").document(colid)
                                            .set(applied)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(YourCompanies.this,"Successfully Applied!",Toast.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(YourCompanies.this,"Error!",Toast.LENGTH_LONG).show();
                                                }
                                            });


                                }
                                else{
                                    Toast.makeText(YourCompanies.this, "NOT A STUDENT", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(YourCompanies.this, studentlogin.class));
                                }

                            } else {
                                Toast.makeText(YourCompanies.this, "Error", Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });

            }
        });

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
