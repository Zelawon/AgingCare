package com.test.agingcarev01.DirectuerFonctions;

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
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.AdminFonction.CreeCompteDirecteur;
import com.test.agingcarev01.AdminFonction.testApresLogin;
import com.test.agingcarev01.DirectuerHome;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class CreerCompteSurveillant extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth1;
    private FirebaseAuth mAuth2;
    Button creeSurvBT, retourBT;
    private EditText password,email,confirmPass,nomSurv,prenomSurv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte_surveillant);

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

        password=findViewById(R.id.passSurv);
        confirmPass=findViewById(R.id.confPassSurv);
        email=findViewById(R.id.emailSurv);
        nomSurv=findViewById(R.id.nomSurv);
        prenomSurv=findViewById(R.id.prenomSurv);

        creeSurvBT=findViewById(R.id.creeSurv);
        creeSurvBT.setOnClickListener(this);
        retourBT=findViewById(R.id.retourMenuDirec);
        retourBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.creeSurv){

            final String pass = password.getText().toString();
            final String confPass = confirmPass.getText().toString();
            final String e_mail = email.getText().toString();
            final String nom = nomSurv.getText().toString();
            final String prenom = prenomSurv.getText().toString();

            if (nom.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "Nom vide", Toast.LENGTH_SHORT);
                toast.show();
                nomSurv.requestFocus();
            }else if (prenom.isEmpty()){
                Toast toast = Toast.makeText(getApplicationContext(), "Prenom vide", Toast.LENGTH_SHORT);
                toast.show();
                prenomSurv.requestFocus();
            }else if (e_mail.isEmpty()){
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
            }else {
                mAuth2.createUserWithEmailAndPassword(e_mail, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(CreerCompteSurveillant.this, "Compte Creer.", Toast.LENGTH_SHORT).show();
                                    mAuth2.signOut();
                                    startActivity(new Intent(CreerCompteSurveillant.this, testApresLogin.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreerCompteSurveillant.this, "Erreur Creation, Veuillez Reessayer.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        if(view.getId()==R.id.retourMenuDirec){
            startActivity(new Intent(CreerCompteSurveillant.this, DirectuerHome.class));
            finish();
        }
    }
}
