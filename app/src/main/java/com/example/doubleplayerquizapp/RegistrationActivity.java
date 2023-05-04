package com.example.doubleplayerquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText emailEdit, userNameEdit, passwdEdit, cnfrmPasswdEdit;
    private DatabaseReference usersRef;
    private Button registerButton;
    private ProgressBar progressBar;
    private FirebaseAuth fbAuth;
    private TextView goToLoginTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        emailEdit = findViewById(R.id.idEditEmail);
        userNameEdit = findViewById(R.id.idEditUserName);
        passwdEdit = findViewById(R.id.idEditPassword);
        registerButton = findViewById(R.id.idBtnRegister);
        cnfrmPasswdEdit = findViewById(R.id.idEditConfirmPassword);
        progressBar = findViewById(R.id.idProgressBar);
        fbAuth = FirebaseAuth.getInstance();
        goToLoginTV = findViewById(R.id.idGoToLogin);
        goToLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = emailEdit.getText().toString();
                String username = userNameEdit.getText().toString();
                String password = passwdEdit.getText().toString();
                String confirmPassword = cnfrmPasswdEdit.getText().toString();

                if (password.equals(confirmPassword) == false) {
                    Toast.makeText(RegistrationActivity.this, "Passwords are not the same",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(RegistrationActivity.this, "Enter your credentials",Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference userRef = usersRef.child(username);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(RegistrationActivity.this, "Username already taken",Toast.LENGTH_SHORT).show();
                            } else {
                                UserSchema user = new UserSchema(email);
                                userRef.setValue(user);
                                fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrationActivity.this, "Registration completed",Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrationActivity.this, "Registration failed",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println(databaseError);
                            Toast.makeText(RegistrationActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}