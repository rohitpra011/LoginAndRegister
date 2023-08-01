package com.rohit.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText email=findViewById(R.id.email);
        EditText password=findViewById(R.id.password);
        Button register=findViewById(R.id.login);
        auth= FirebaseAuth.getInstance();
        //to display back button on top
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        register.setOnClickListener(view -> {
            String textEmail=email.getText().toString();
            String textPassword=password.getText().toString();
            if (TextUtils.isEmpty(textEmail)||TextUtils.isEmpty(textPassword)){
                Toast.makeText(SignupActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
            }else if (textPassword.length()<6){
                Toast.makeText(SignupActivity.this, "Password must be of minimum 6 characters", Toast.LENGTH_SHORT).show();
            }else{
                registerUser(textEmail,textPassword);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void registerUser(String Email, String Password) {
        auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //  Toast.makeText(registerActivity.this, "New user registration successful", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(registerActivity.this, MainActivity.class));
                    // finish();
                    sendEmailVerification();
                }
                //else{
                //   Toast.makeText(registerActivity.this, "Registration Failed Try again later", Toast.LENGTH_SHORT).show();
                // }
            }
        });
    }



    //email verification
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SignupActivity.this, "Verification email sent. Verify and login again ", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }
            });
        }
        else{
            Toast.makeText(SignupActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
        }
    }






}