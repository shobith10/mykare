package com.example.mykare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Initialize variable
    Button ulogin;
    EditText uEmail,uPassword;
    TextView uRegister;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uRegister = findViewById(R.id.register);
        ulogin = findViewById(R.id.login);
        uEmail = findViewById(R.id.email);
        uPassword = findViewById(R.id.password);

        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString().trim();

                if(email.isEmpty()){
                    uEmail.setError("Email is required");
                    uEmail.requestFocus();
                    return;
                }
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    return;
                } else{
                    uEmail.setError("Please provide vaild email!");
                    uEmail.requestFocus();
                }

                if(password.isEmpty()){
                    uPassword.setError("Password is required");
                    uPassword.requestFocus();
                    return;
                }
                if(password.length() < 6){
                    uPassword.setError("Min password length should be 6 characters");
                    uPassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }else{
                            Toast.makeText(MainActivity.this,"Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        uRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


    }
}