package com.mfundoza.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout tilEmail;
    TextInputLayout tilPassword;
    Button btnSignUp;

    String email;
    String password;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void createUser() {

        email = tilEmail.getEditText().getText().toString();
        password = tilPassword.getEditText().getText().toString();

        if (isInputDataValid()) {
            // Create user
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "User creation: Succesful!", Toast.LENGTH_SHORT).show();
                        // FirebaseUser user = firebaseAuth.getCurrentUser();
                    } else {
                        Toast.makeText(SignUpActivity.this, "User creation: Unsuccesful!", Toast.LENGTH_SHORT);
                    }
                }
            });

            //setToastMessage(successMessage);
        }
        else {
            // Alert user of failed validation
            //setToastMessage(errorMessage);
        }
    }

    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() > 5;
    }
}