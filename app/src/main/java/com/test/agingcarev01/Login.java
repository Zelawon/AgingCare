package com.test.agingcarev01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.AdminFonction.CreeCompteDirecteur;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button logInBT, retourBT;
    private EditText email,mdp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInBT=findViewById(R.id.SignIn);
        retourBT =findViewById(R.id.retourLogin);
        email=findViewById(R.id.emaillog);
        mdp=findViewById(R.id.passlog);

        mAuth = FirebaseAuth.getInstance();

        logInBT.setOnClickListener(this);
        retourBT.setOnClickListener(this);
    }
    private void singIn(final String mail, final String password) {
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
                else{
                    if (mail.isEmpty()){
                        Toast toast = Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT);
                        toast.show();
                    }else if (password.isEmpty()){
                        Toast toast = Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void updateUI() {
        //if email existe dans database affiche le home correspendent.
        startActivity(new Intent(Login.this, DirectuerHome.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        String password = mdp.getText().toString();
        String emails = email.getText().toString();
        if(view.getId()==R.id.SignIn){
            if (emails.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT);
                toast.show();
                email.requestFocus();
            }else if (password.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT);
                toast.show();
                mdp.requestFocus();
            }else {
                singIn(emails,password);
            }
        }
        if (view.getId()==R.id.retourLogin){
            finish();
        }
    }
}
