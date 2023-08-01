package com.rohit.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    public EditText regmailvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        regmailvar=findViewById(R.id.email);
        Button resetbuttonvar = findViewById(R.id.login);
        Button backtologinvar = findViewById(R.id.backtologin);
        //to handle back to login button
        backtologinvar.setOnClickListener(view -> startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class)));


        //to handle reset button
        resetbuttonvar.setOnClickListener(view -> {
            String mail=regmailvar.getText().toString().trim();
            if(mail.isEmpty())
            {
                Toast.makeText(ForgetPasswordActivity.this, "Enter your registered email", Toast.LENGTH_SHORT).show();
            }
            else
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPasswordActivity.this, "Password resetting link sent on registered email.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ForgetPasswordActivity.this, "Can't reset password, please try after some time.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}