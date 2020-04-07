package com.example.hp.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditCompany extends AppCompatActivity implements View.OnClickListener {
    private CardView allbranch, cse, ece, mech, civil, electrical, chemical, archi, meta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);

        allbranch= findViewById(R.id.editallbranches_button);
        cse= findViewById(R.id.editcse_companies);
        ece= findViewById(R.id.editece_companies);
        mech= findViewById(R.id.editmech_companies);
        civil= findViewById(R.id.editcivil_companies);
        electrical= findViewById(R.id.editelectrical_companies);
        chemical= findViewById(R.id.editchemical_companies);
        archi= findViewById(R.id.editarchi_companies);
        meta= findViewById(R.id.editmeta_companies);

        allbranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditAll.class ); startActivity(i);
            }
        });
        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditCse.class ); startActivity(i);

            }
        });
        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditEce.class ); startActivity(i);
            }
        });
        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditMech.class ); startActivity(i);
            }
        });
        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditCivil.class ); startActivity(i);
            }
        });
        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditElectrical.class ); startActivity(i);
            }
        });
        chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditChemical.class ); startActivity(i);
            }
        });
        archi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditArchi.class ); startActivity(i);
            }
        });
        meta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(EditCompany.this,EditMeta.class ); startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
