package com.test.agingcarev01.FonctionsCommunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.R;

public class ConsulterProfil extends AppCompatActivity implements View.OnClickListener {
    private Button retourFrProfBT;
    private TextView nom,prenom,role,email,sexe,nomTXT,prenomTXT,sexeTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);
        Log.e("TAG Erreur : ", "Hello! consuter profil");

        //Buttons
        retourFrProfBT=findViewById(R.id.retourFrProf);
        retourFrProfBT.setOnClickListener(this);

        //Database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        Log.e("TAG Erreur : ", "1");

        //Text View nom champ
        nomTXT=findViewById(R.id.nomTextView);
        prenomTXT=findViewById(R.id.prenomTextView);
        sexeTXT=findViewById(R.id.sexeTextView);
        //Text View a modifier
        nom=findViewById(R.id.txtNom);
        prenom=findViewById(R.id.txtPrenom);
        role=findViewById(R.id.txtRole);
        email=findViewById(R.id.txtEmail);
        sexe=findViewById(R.id.txtSex);

        Log.e("TAG Erreur : ", "1.1");
        //Load interface avec les champs de chaque role
        emailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshott) {
                Log.e("TAG Erreur : ", "1.2");
                Log.e("TAG Erreur : ", dataSnapshott.getKey());
                if (dataSnapshott.exists()) {
                    Log.e("TAG Erreur : ", "1.3");
                    for (DataSnapshot roleSnapshott : dataSnapshott.getChildren()) {
                        Log.e("TAG Erreur : ", "1.4");
                        Log.e("TAG Erreur : ", roleSnapshott.child("role").getValue(String.class));
                        String currentRole = roleSnapshott.child("role").getValue(String.class);
                        String currentEmail = roleSnapshott.child("email").getValue(String.class);
                        if (currentRole.equals("Admin")) {
                            prenomTXT.setVisibility(View.INVISIBLE);
                            nomTXT.setVisibility(View.INVISIBLE);
                            sexeTXT.setVisibility(View.INVISIBLE);
                            nom.setVisibility(View.INVISIBLE);
                            prenom.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            role.setText(currentRole);
                            email.setText(currentEmail);

                        } else if (currentRole.equals("Directeur")) {
                            sexeTXT.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        } else if (currentRole.equals("Surveillant")) {
                            sexeTXT.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        } else if (currentRole.equals("Infirmier")) {
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            String currentSexe = roleSnapshott.child("sexe").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);
                            sexe.setText(currentSexe);

                        }else{
                            Toast.makeText(getApplicationContext(), "Erreur Role", Toast.LENGTH_SHORT).show();
                            Log.e("TAG Erreur : ", "Erreur Role");
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Erreur Consulter Profil", Toast.LENGTH_SHORT).show();
                    Log.e("TAG Erreur : ", "Erreur Consulter Profil");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrProf){
            finish();
        }
    }
}
