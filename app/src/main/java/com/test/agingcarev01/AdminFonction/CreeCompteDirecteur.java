package com.test.agingcarev01.AdminFonction;

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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class CreeCompteDirecteur extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    Button registerBT;
    private EditText password,email,confirmPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte_directeur);

        mAuth = FirebaseAuth.getInstance();

        password=findViewById(R.id.passlog);
        confirmPass=findViewById(R.id.confPass);
        email=findViewById(R.id.email1);

        FirebaseApp.initializeApp(CreeCompteDirecteur.this);
        registerBT=findViewById(R.id.register);
        registerBT.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        final String pass = password.getText().toString();
        String confPass = confirmPass.getText().toString();
        final String emails = email.getText().toString();

        if (emails.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT);
            toast.show();
            email.requestFocus();
        }else if (pass.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT);
            toast.show();
            password.requestFocus();
        }else if (pass.length()<6){
            Toast toast = Toast.makeText(getApplicationContext(), "Mot de Passe (Au Moins 6 Caracteres)", Toast.LENGTH_SHORT);
            toast.show();
            password.requestFocus();
        }else if ((confPass.isEmpty())){
            Toast toast = Toast.makeText(getApplicationContext(), "Confirmer Mot de Passe vide", Toast.LENGTH_SHORT);
            toast.show();
            confirmPass.requestFocus();
        }else if (!(pass.equals(confPass))){
            Toast toast = Toast.makeText(getApplicationContext(), "Erreur Confirmer Mot de Passe", Toast.LENGTH_SHORT);
            toast.show();
            confirmPass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(emails, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(CreeCompteDirecteur.this, "Compte Cree.", Toast.LENGTH_SHORT).show();
                                //FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(CreeCompteDirecteur.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(CreeCompteDirecteur.this, "Erreur Authentication, Veuillez Reessayer.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
