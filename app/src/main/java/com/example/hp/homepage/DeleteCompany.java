package com.example.hp.homepage;

import android.app.Dialog;
import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DeleteCompany extends AppCompatActivity implements companyAdapter.OnItemClickListener{

    Dialog myDialog;
    Button refreshButton;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference companyRef = db.collection("Companies");
    private companyAdapter adapter;

    FirebaseAuth firebaseAuth;
    String cgpa, userId, mycg, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_company);
        GlobalClass globalClass= (GlobalClass) getApplicationContext();
        myDialog= new Dialog(this);

        setUpRecyclerView();
    }

    public void setUpRecyclerView(){

        data="10";

        Float result= Float.valueOf(data);
        Query query = companyRef.whereLessThanOrEqualTo("mincg", result);
        FirestoreRecyclerOptions<Company> options = new FirestoreRecyclerOptions.Builder<com.example.hp.homepage.Company>()
                .setQuery(query, com.example.hp.homepage.Company.class)
                .build();

        adapter = new com.example.hp.homepage.companyAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.delete_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
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
    public void OnItemCLick(DocumentSnapshot documentSnapshot, int position) {

        }

}
