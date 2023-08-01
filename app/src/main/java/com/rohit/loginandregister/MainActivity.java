package com.rohit.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText Email;
    private EditText Password;
    private TextView resetpasvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        resetpasvar=findViewById(R.id.ForgetPassword);
        //  Login=findViewById(R.id.login);
        auth=FirebaseAuth.getInstance();


        resetpasvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgetPasswordActivity.class));
            }
        });
    }

    private void loginUser(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkEmailVarification();
                } else {
                    Toast.makeText(MainActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Login(View view) {
        String text_email=Email.getText().toString();
        String text_password=Password.getText().toString();

        if (TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password)){
            Toast.makeText(MainActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
        }else if (text_password.length()<6){
            Toast.makeText(MainActivity.this, "Password must be of minimum 6 characters", Toast.LENGTH_SHORT).show();
        }else{
            loginUser(text_email,text_password);
        }
    }


    public void checkEmailVarification()
    {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        /*To avoid Null Pointer Exception*/ //even aftere using the assert we may get the assertion error
        assert firebaseUser != null;
        if(firebaseUser.isEmailVerified())
        {
            Toast.makeText(this, "Logged in ", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,DestinationActivity.class));
        }
        else
        {
            Toast.makeText(MainActivity.this, "Please check email and verify your account ", Toast.LENGTH_SHORT).show();
        }
    }
    public void Signup(View view) {
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }
}