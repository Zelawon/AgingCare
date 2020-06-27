package com.test.agingcarev01.FonctionsDirectuer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.R;

import es.dmoral.toasty.Toasty;

public class CreerCompteInfirmier extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth1, mAuth2;
    private Button creeInfBT, retourBT;
    private EditText password, email, confirmPass, nomInf, prenomInf;
    private RadioButton hommRadio, femmRadio;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int idEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte_infirmier);

        mAuth1 = FirebaseAuth.getInstance();
        //auth 2 pour ne pas deconnecter le auth 1
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

        password = findViewById(R.id.passInf);
        confirmPass = findViewById(R.id.confPassInf);
        email = findViewById(R.id.emailInf);
        nomInf = findViewById(R.id.nomInf);
        prenomInf = findViewById(R.id.prenomInf);
        femmRadio = findViewById(R.id.radioFemmeInf);
        hommRadio = findViewById(R.id.radioHommeInf);

        creeInfBT = findViewById(R.id.creeInf);
        creeInfBT.setOnClickListener(this);
        retourBT = findViewById(R.id.retourFormInfBT);
        retourBT.setOnClickListener(this);

        FirebaseApp.initializeApp(CreerCompteInfirmier.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        getIdEemployee();

    }

    private void getIdEemployee() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ID").child("IDEmployee");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idEmp = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Error", databaseError.toException());
            }
        });
    }

    private void updateIdEmployee() {
        //get l'id
        getIdEemployee();
        //ajout 1 a l'id
        idEmp++;
        //update l'id
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idUpdateRef = database.getReference();
        idUpdateRef.child("ID").child("IDEmployee").setValue(idEmp);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.creeInf) {

            final String pass = password.getText().toString();
            final String confPass = confirmPass.getText().toString();
            final String e_mail = email.getText().toString().toLowerCase();
            final String nom = nomInf.getText().toString().toLowerCase();
            final String prenom = prenomInf.getText().toString().toLowerCase();

            if (nom.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Nom vide", Toast.LENGTH_SHORT).show();
                nomInf.requestFocus();
            } else if (prenom.isEmpty()) {
                Toasty.warning(getApplicationContext(), "Champ Prenom vide", Toast.LENGTH_SHORT).show();
                prenomInf.requestFocus();
            } else if ((!(femmRadio.isChecked())) && (!(hommRadio.isChecked()))) {
                Toasty.warning(getApplicationContext(), "Champ sexe vide", Toast.LENGTH_SHORT).show();
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

                                    Toasty.info(CreerCompteInfirmier.this, "Compte Creer.", Toast.LENGTH_SHORT).show();
                                    mAuth2.signOut();

                                    //ajouter a la database
                                    if (femmRadio.isChecked()) {
                                        InfirmierClasse nvInf = new InfirmierClasse(idEmp, e_mail, nom, prenom, "femme");
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Employee").push();

                                        myRef.setValue(nvInf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toasty.info(CreerCompteInfirmier.this, "Compte ajouter a la base.", Toast.LENGTH_SHORT).show();
                                                updateIdEmployee();
                                                finish();
                                            }
                                        });
                                    } else if (hommRadio.isChecked()) {
                                        InfirmierClasse nvInf = new InfirmierClasse(idEmp, e_mail, nom, prenom, "homme");
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Employee").push();

                                        myRef.setValue(nvInf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toasty.info(CreerCompteInfirmier.this, "Compte ajouter a la base.", Toast.LENGTH_SHORT).show();
                                                updateIdEmployee();
                                                finish();
                                            }
                                        });
                                    } else {
                                        Toasty.error(CreerCompteInfirmier.this, "ERREUR CHAMP SEXE", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toasty.error(CreerCompteInfirmier.this, "Erreur Creation, Veuillez Reessayer.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        if (view.getId() == R.id.retourFormInfBT) {
            finish();
        }

    }
}
