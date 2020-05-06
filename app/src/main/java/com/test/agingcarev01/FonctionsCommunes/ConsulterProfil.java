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
    private Button modifierProfBT, retourFrProfBT,modifierProfPassBT;
    private TextView nom,prenom,role,email,sexe,nomTXT,prenomTXT,sexeTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);

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

        modifierProfBT=findViewById(R.id.modifierProf);
        modifierProfBT.setOnClickListener(this);
        retourFrProfBT=findViewById(R.id.retourFrProf);
        retourFrProfBT.setOnClickListener(this);
        modifierProfPassBT=findViewById(R.id.modifierProfPass);
        modifierProfPassBT.setOnClickListener(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        emailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot roleSnapshot : dataSnapshot.getChildren()) {
                        String currentRole = roleSnapshot.child("role").getValue(String.class);
                        String currentEmail = roleSnapshot.child("email").getValue(String.class);
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
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        }else if (currentRole.equals("Surveillant")) {
                            sexeTXT.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        }else if (currentRole.equals("Infirmier")) {
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            String currentSexe = roleSnapshot.child("sexe").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);
                            sexe.setText(currentSexe);

                        }
                    }
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
        if(view.getId()==R.id.modifierProf){
            startActivity(new Intent(ConsulterProfil.this, ModifierProfil.class));
        }
        if(view.getId()==R.id.retourFrProf){
            finish();
        }
        if(view.getId()==R.id.modifierProfPass){
            finish();
        }

    }
}
