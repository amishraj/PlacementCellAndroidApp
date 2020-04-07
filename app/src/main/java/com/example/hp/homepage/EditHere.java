package com.example.hp.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditHere extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    EditText  CGPA, description;
    TextView Name;
    private DocumentReference appliedRef; //maybe not accessing the collection
    private AppliedAdapter adapter;

    RadioGroup cseradio, eceradio, mechradio, civilradio, electricalradio, chemradio, archiradio, metaradio;
    RadioButton radioButtonCse, radioButtonEce, radioButtonMech, radioButtonCivil, radioButtonElectrical, radioButtonChem, radioButtonArchi, radioButtonMeta;

    String title, descriptions, minimumcg, maximumcg, cse, ece, mech, civil, electrical, chem, archi, meta;

    private Button listCreate;

    FirebaseAuth firebaseAuth;
    EditText maxcg;

    TextView textView, dispplayid;
    TextView display;
    String cgpa, userId,data, mycg;
    public String compid;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_here);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);

        CGPA= findViewById(R.id.editlist_cgmin);
        description= findViewById(R.id.editinputListDesc);
        Name=  findViewById(R.id.editinputListTitle);

        GlobalClass globalClass= (GlobalClass) getApplicationContext();

        Name.setText(globalClass.getCompanyid());
        CGPA.setText(globalClass.getCompany_cg_criteria());
        description.setText(globalClass.getCompany_description());

        appliedRef= db.collection("Companies").document(globalClass.getCompanyid());

        firebaseAuth= FirebaseAuth.getInstance();

        //1
        cseradio= findViewById(R.id.editcseradio);
        //2
        chemradio= findViewById(R.id.editchemradio);
        //3
        eceradio= findViewById(R.id.editeceradio);
        //4
        civilradio= findViewById(R.id.editcivilradio);
        //5
        electricalradio= findViewById(R.id.editelectricalradio);
        //6
        archiradio= findViewById(R.id.editarchiradio);
        //7
        metaradio= findViewById(R.id.editmetaradio);
        //8
        mechradio= findViewById(R.id.editmechradio);

        maxcg= findViewById(R.id.editlist_cgmax);

        maxcg.setText("10");

        listCreate= (Button) findViewById(R.id.editlistCreate);

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


                title= Name.getText().toString().trim();
                descriptions= description.getText().toString().trim();
                minimumcg= CGPA.getText().toString().trim();
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
                    Toast.makeText(EditHere.this,"Please enter Company Title", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(descriptions)){
                    Toast.makeText(EditHere.this,"Please enter Company Description", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(minimumcg)){
                    Toast.makeText(EditHere.this,"Please enter Minimum CGPA", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(maximumcg)){
                    Toast.makeText(EditHere.this,"Please enter Maximum CGPA", Toast.LENGTH_LONG).show();
                    return;
                }

                float result= Float.valueOf(minimumcg);

                progressDialog.setMessage("Editing Company Please Wait...");
                progressDialog.show();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Create a new user with a first and last name
                Map<String, Object> company = new HashMap<>();
                company.put("title", title);
                company.put("description", descriptions);
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
                                Toast.makeText(EditHere.this,"Company Updated!",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditHere.this,"Error!",Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

    }


    public void createlist(){

        String title= Name.getText().toString();

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
