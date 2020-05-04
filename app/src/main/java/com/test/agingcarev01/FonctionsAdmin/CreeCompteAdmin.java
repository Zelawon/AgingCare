package com.test.agingcarev01.FonctionsAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.agingcarev01.Classe.AdminClasse;
import com.test.agingcarev01.HomePages.AdminHome;
import com.test.agingcarev01.R;

public class CreeCompteAdmin extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth1,mAuth2;
    private Button registerBT,retourBT;
    private EditText password,email,confirmPass;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte_admin);

        mAuth1 = FirebaseAuth.getInstance();
        //auth 2 poun ne pas deconnecter le auth 1
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("[https://agingcare-6f82d.firebaseio.com/]")
                .setApiKey("AIzaSyDed0dGzgms4U1AdwN7qorSluc2A3B1aHQ")
                .setApplicationId("agingcare-6f82d").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        password=findViewById(R.id.passAdmin);
        confirmPass=findViewById(R.id.confPassAdmin);
        email=findViewById(R.id.emailAdmin);

        FirebaseApp.initializeApp(CreeCompteAdmin.this);
        registerBT=findViewById(R.id.creeAdmin);
        registerBT.setOnClickListener(this);
        retourBT=findViewById(R.id.retourFormAdminBT);
        retourBT.setOnClickListener(this);

        FirebaseApp.initializeApp(CreeCompteAdmin.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.creeAdmin){
            final String pass = password.getText().toString();
            final String confPass = confirmPass.getText().toString();
            final String e_mail = email.getText().toString();

            if (e_mail.isEmpty()){
                Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }else if (pass.isEmpty()){
                Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT).show();
                password.requestFocus();
            }else if (pass.length()<6){
                Toast.makeText(getApplicationContext(), "Mot de Passe (Au Moins 6 Caracteres)", Toast.LENGTH_SHORT).show();
                password.requestFocus();
            }else if ((confPass.isEmpty())){
                Toast.makeText(getApplicationContext(), "Confirmer Mot de Passe vide", Toast.LENGTH_SHORT).show();
                confirmPass.requestFocus();
            }else if (!(pass.equals(confPass))){
                Toast.makeText(getApplicationContext(), "Erreur Confirmer Mot de Passe", Toast.LENGTH_SHORT).show();
                confirmPass.requestFocus();
            }else{
                mAuth2.createUserWithEmailAndPassword(e_mail, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Compte Creer cbn
                                    //Sign in success
                                    //Logout nv compte
                                    Toast.makeText(CreeCompteAdmin.this, "Compte Creer.", Toast.LENGTH_SHORT).show();
                                    mAuth2.signOut();

                                    //ajouter a la database.
                                    AdminClasse nvAdmin = new AdminClasse(e_mail);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Employee").push();

                                    myRef.setValue(nvAdmin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(CreeCompteAdmin.this, "Compte ajouter a la base.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CreeCompteAdmin.this, AdminHome.class));
                                            finish();
                                        }
                                    });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreeCompteAdmin.this, "Erreur Creation, Veuillez Reessayer.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        if(view.getId()==R.id.retourFormAdminBT){
            finish();
        }
    }
}
