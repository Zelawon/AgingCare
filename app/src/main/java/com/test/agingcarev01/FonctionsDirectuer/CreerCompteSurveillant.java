package com.test.agingcarev01.FonctionsDirectuer;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.agingcarev01.Classe.SurveillantClasse;
import com.test.agingcarev01.R;

import es.dmoral.toasty.Toasty;

public class CreerCompteSurveillant extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth1, mAuth2;
    private Button creeSurvBT, retourBT;
    private EditText password, email, confirmPass, nomSurv, prenomSurv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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

        try {
            FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e) {
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }

        password = findViewById(R.id.passSurv);
        confirmPass = findViewById(R.id.confPassSurv);
        email = findViewById(R.id.emailSurv);
        nomSurv = findViewById(R.id.nomSurv);
        prenomSurv = findViewById(R.id.prenomSurv);

        creeSurvBT = findViewById(R.id.creeSurv);
        creeSurvBT.setOnClickListener(this);
        retourBT = findViewById(R.id.retourFormSurvBT);
        retourBT.setOnClickListener(this);

        FirebaseApp.initializeApp(CreerCompteSurveillant.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.creeSurv) {

            final String pass = password.getText().toString();
            final String confPass = confirmPass.getText().toString();
            final String e_mail = email.getText().toString().toLowerCase();
            final String nom = nomSurv.getText().toString().toLowerCase();
            final String prenom = prenomSurv.getText().toString().toLowerCase();

            if (nom.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Nom vide", Toast.LENGTH_SHORT).show();
                nomSurv.requestFocus();
            } else if (prenom.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Prenom vide", Toast.LENGTH_SHORT).show();
                prenomSurv.requestFocus();
            } else if (e_mail.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Email vide", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            } else if (pass.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Mot de Passe vide", Toast.LENGTH_SHORT).show();
                password.requestFocus();
            } else if (pass.length() < 6) {
                Toasty.warning(getApplicationContext(), "Mot de Passe (Au Moins 6 Caracteres)", Toast.LENGTH_SHORT).show();
                password.requestFocus();
            } else if ((confPass.isEmpty())) {
                Toasty.warning(getApplicationContext(), "Champ Confirmer Mot de Passe vide", Toast.LENGTH_SHORT).show();
                confirmPass.requestFocus();
            } else if (!(pass.equals(confPass))) {
                Toasty.warning(getApplicationContext(), "Erreur Confirmer Mot de Passe", Toast.LENGTH_SHORT).show();
                confirmPass.requestFocus();
            } else {
                mAuth2.createUserWithEmailAndPassword(e_mail, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Compte Creer cbn
                                    //Sign in success
                                    //Logout nv compte

                                    //Hide virtual keyboard on button press
                                    InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                                    inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                    Toasty.info(CreerCompteSurveillant.this, "Compte Creer.", Toast.LENGTH_SHORT).show();
                                    mAuth2.signOut();

                                    //ajouter a la database
                                    SurveillantClasse nvSuruv = new SurveillantClasse(e_mail, nom, prenom);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("Employee").push();

                                    myRef.setValue(nvSuruv).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toasty.info(CreerCompteSurveillant.this, "Compte ajouter a la base.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toasty.error(CreerCompteSurveillant.this, "Erreur Creation, Veuillez Reessayer.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        if (view.getId() == R.id.retourFormSurvBT) {
            finish();
        }
    }
}
