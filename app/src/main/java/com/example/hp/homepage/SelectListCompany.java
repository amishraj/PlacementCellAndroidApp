package com.example.hp.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class SelectListCompany extends AppCompatActivity implements View.OnClickListener{

    private CardView allbranch, cse, ece, mech, civil, electrical, chemical, archi, meta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list_company);

        allbranch= findViewById(R.id.allbranches_button);
        cse= findViewById(R.id.cse_companies);
        ece= findViewById(R.id.ece_companies);
        mech= findViewById(R.id.mech_companies);
        civil= findViewById(R.id.civil_companies);
        electrical= findViewById(R.id.electrical_companies);
        chemical= findViewById(R.id.chemical_companies);
        archi= findViewById(R.id.archi_companies);
        meta= findViewById(R.id.meta_companies);


        allbranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;
                i= new Intent(SelectListCompany.this,Company_List.class ); startActivity(i);
            }
        });

        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;
                i= new Intent(SelectListCompany.this,EceCompanies.class ); startActivity(i);
            }
        });
        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;
                i= new Intent(SelectListCompany.this,CseCompanies.class ); startActivity(i);
            }
        });
        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;
                i= new Intent(SelectListCompany.this,MechCompanies.class ); startActivity(i);
            }
        });
        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i ;
                i= new Intent(SelectListCompany.this,CivilCompanies.class ); startActivity(i);
            }
        });
        electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(SelectListCompany.this, ElectricalCompanies.class); startActivity(i);
            }
        });
        chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(SelectListCompany.this, ChemicalCompanies.class); startActivity(i);
            }
        });
        archi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(SelectListCompany.this, ArchiCompanies.class); startActivity(i);
            }
        });

        meta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i= new Intent(SelectListCompany.this, MetaCompanies.class); startActivity(i);
            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
