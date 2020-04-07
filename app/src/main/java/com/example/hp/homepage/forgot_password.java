package com.example.hp.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    private EditText ForgottenEmail;
    private Button ForgotButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ForgottenEmail= findViewById(R.id.forgotten_mail);
        ForgotButton= findViewById(R.id.forgot_button);
        firebaseAuth= FirebaseAuth.getInstance();

        ForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail= ForgottenEmail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(forgot_password.this,"Please enter email",Toast.LENGTH_LONG).show();
                }
                else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(forgot_password.this,"An email has been sent to your Email ID",Toast.LENGTH_LONG).show();
                               finish();
                               startActivity(new Intent(forgot_password.this, studentlogin.class));
                           }
                           else{
                               Toast.makeText(forgot_password.this,"The Email entered is incorrect",Toast.LENGTH_LONG).show();
                           }
                        }
                    });
                }
            }
        });
    }
}
