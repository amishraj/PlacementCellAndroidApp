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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeAdminPass extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;

    Button adminpassbutton;
    EditText pass1, pass2;
    String newpassword, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_admin_pass);

        progressDialog = new ProgressDialog(this);

        pass1= findViewById(R.id.adminewpass);
        pass2= findViewById(R.id.adminconfirmpass);
        adminpassbutton= findViewById(R.id.new_pass);

        adminpassbutton.setOnClickListener(this);
    }

    private void adminpass(){
        newpassword= pass1.getText().toString().trim();
        confirmpassword=pass2.getText().toString().trim();

        if(TextUtils.isEmpty(newpassword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(newpassword.contentEquals(confirmpassword)){
            progressDialog.setMessage("Changing Password Please Wait...");
            progressDialog.show();

            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(ChangeAdminPass.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(ChangeAdminPass.this, adminupdate.class));

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(ChangeAdminPass.this, "Password Could not be Changed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        }

        else{
            Toast.makeText(ChangeAdminPass.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }


    }

    @Override
    public void onClick(View v) {

        if(v== adminpassbutton){
            adminpass();
        }


    }
}
