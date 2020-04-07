package com.example.hp.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateList extends AppCompatActivity implements View.OnClickListener {

    EditText inputListTitle, inputListDesc, mincg, maxcg;
    String title, description, minimumcg, maximumcg, cse, ece, mech, civil, electrical, chem, archi, meta;

    RadioGroup cseradio, eceradio, mechradio, civilradio, electricalradio, chemradio, archiradio, metaradio;
    RadioButton radioButtonCse, radioButtonEce, radioButtonMech, radioButtonCivil, radioButtonElectrical, radioButtonChem, radioButtonArchi, radioButtonMeta;

    private Button listCreate;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();

        cseradio= findViewById(R.id.cseradio);
        chemradio= findViewById(R.id.chemradio);
        eceradio= findViewById(R.id.eceradio);
        civilradio= findViewById(R.id.civilradio);
        electricalradio= findViewById(R.id.electricalradio);
        archiradio= findViewById(R.id.archiradio);
        metaradio= findViewById(R.id.metaradio);
        mechradio= findViewById(R.id.mechradio);
        inputListTitle= (EditText) findViewById(R.id.inputListTitle);
        inputListDesc= findViewById(R.id.inputListDesc);
        mincg= findViewById(R.id.list_cgmin);
        maxcg= findViewById(R.id.list_cgmax);

        listCreate= (Button) findViewById(R.id.listCreate);

        listCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cseRadioId= cseradio.getCheckedRadioButtonId();
                int eceRadioId= eceradio.getCheckedRadioButtonId();
                int mechRadioId= mechradio.getCheckedRadioButtonId();
                int civilRadioId= civilradio.getCheckedRadioButtonId();
                int electricalRadioId= electricalradio.getCheckedRadioButtonId();
                int chemRadioId= chemradio.getCheckedRadioButtonId();
                int archiRadioId= archiradio.getCheckedRadioButtonId();
                int metaRadioId= metaradio.getCheckedRadioButtonId();

                radioButtonCse= findViewById(cseRadioId);
                radioButtonEce= findViewById(eceRadioId);
                radioButtonMech= findViewById(mechRadioId);
                radioButtonCivil= findViewById(civilRadioId);
                radioButtonElectrical= findViewById(electricalRadioId);
                radioButtonChem= findViewById(chemRadioId);
                radioButtonArchi= findViewById(archiRadioId);
                radioButtonMeta= findViewById(metaRadioId);

                // setitout.setText(radioButtonCse.getText().toString());

                title= inputListTitle.getText().toString().trim();
                description= inputListDesc.getText().toString().trim();
                minimumcg= mincg.getText().toString().trim();
                maximumcg= maxcg.getText().toString().trim();
                cse= radioButtonCse.getText().toString().trim();
                ece= radioButtonEce.getText().toString().trim();
                civil= radioButtonCivil.getText().toString().trim();
                mech= radioButtonMech.getText().toString().trim();
                electrical= radioButtonElectrical.getText().toString().trim();
                chem= radioButtonChem.getText().toString().trim();
                archi= radioButtonArchi.getText().toString().trim();
                meta= radioButtonMeta.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    Toast.makeText(CreateList.this,"Please enter Company Title", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    Toast.makeText(CreateList.this,"Please enter Company Description", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(minimumcg)){
                    Toast.makeText(CreateList.this,"Please enter Minimum CGPA", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(maximumcg)){
                    Toast.makeText(CreateList.this,"Please enter Maximum CGPA", Toast.LENGTH_LONG).show();
                    return;
                }

                float result= Float.valueOf(minimumcg);

                progressDialog.setMessage("Adding Company Please Wait...");
                progressDialog.show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Create a new company
                Map<String, Object> company = new HashMap<>();
                company.put("title", title);
                company.put("description", description);
                company.put("mincg", result);
                company.put("maxcg", maximumcg);
                company.put("Computer Science and Engineering", cse);
                company.put("Electronics and Communication Engineering", ece);
                company.put("Mechanical Engineering",mech);
                company.put("Civil Engineering", civil);
                company.put("Electrical Engineering", electrical);
                company.put("Chemical Engineering", chem);
                company.put("Architecture and Planning", archi);
                company.put("Metallurgical and Material Engineering", meta);

// Add a new document with a generated ID
                db.collection("Companies").document(title)
                        .set(company)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(CreateList.this,"Company Added!",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(CreateList.this, AdminProfile3.class));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateList.this,"Error!",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }

    public void createlist(){

        String title= inputListTitle.getText().toString();

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this,"Please enter Title of List", Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void checkButton(View v){
        int cseRadioId= cseradio.getCheckedRadioButtonId();
        int eceRadioId= eceradio.getCheckedRadioButtonId();
        int mechRadioId= mechradio.getCheckedRadioButtonId();
        int civilRadioId= civilradio.getCheckedRadioButtonId();
        int electricalRadioId= electricalradio.getCheckedRadioButtonId();
        int chemRadioId= chemradio.getCheckedRadioButtonId();
        int archiRadioId= archiradio.getCheckedRadioButtonId();
        int metaRadioId= metaradio.getCheckedRadioButtonId();

        radioButtonCse= findViewById(cseRadioId);
        radioButtonEce= findViewById(eceRadioId);
        radioButtonMech= findViewById(mechRadioId);
        radioButtonCivil= findViewById(civilRadioId);
        radioButtonElectrical= findViewById(electricalRadioId);
        radioButtonChem= findViewById(chemRadioId);
        radioButtonArchi= findViewById(archiRadioId);
        radioButtonMeta= findViewById(metaRadioId);

    }

    @Override
    public void onClick(View view) {

        if(view == listCreate){
            int cseRadioId= cseradio.getCheckedRadioButtonId();
            int eceRadioId= eceradio.getCheckedRadioButtonId();
            int mechRadioId= mechradio.getCheckedRadioButtonId();
            int civilRadioId= civilradio.getCheckedRadioButtonId();
            int electricalRadioId= electricalradio.getCheckedRadioButtonId();
            int chemRadioId= chemradio.getCheckedRadioButtonId();
            int archiRadioId= archiradio.getCheckedRadioButtonId();
            int metaRadioId= metaradio.getCheckedRadioButtonId();

            cseradio= findViewById(cseRadioId);
            eceradio= findViewById(eceRadioId);
            mechradio= findViewById(mechRadioId);
            civilradio= findViewById(civilRadioId);
            electricalradio= findViewById(electricalRadioId);
            chemradio= findViewById(chemRadioId);
            archiradio= findViewById(archiRadioId);
            metaradio= findViewById(metaRadioId);

            createlist();
        }

    }

}
