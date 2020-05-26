package com.test.agingcarev01.FonctionsProfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeMaladie;
import com.test.agingcarev01.FonctionsSurveillant.ModifierInfoResident;
import com.test.agingcarev01.R;

public class ConsulterProfilResident extends AppCompatActivity implements View.OnClickListener {
    TextView nom,prenom,sexe,dateNaissance,typeSang;
    DatabaseReference databaseReference;
    Button retourFrProfilResBT,modifierResProfilBT,maladieResProfilBT;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_resident);

        nom=findViewById(R.id.nomResProfil);
        prenom=findViewById(R.id.prenomResProfil);
        sexe=findViewById(R.id.sexeResProfil);
        dateNaissance=findViewById(R.id.dateNasiResProfil);
        typeSang=findViewById(R.id.typeSangResProfil);

        retourFrProfilResBT=findViewById(R.id.retourFrProfilRes);
        retourFrProfilResBT.setOnClickListener(this);

        maladieResProfilBT=findViewById(R.id.maladieResProfil);
        maladieResProfilBT.setOnClickListener(this);

        modifierResProfilBT=findViewById(R.id.modifierResProfil);
        modifierResProfilBT.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident");
        key=getIntent().getStringExtra("id");
        fillProfileInfo(key);
    }

    private void fillProfileInfo(String id) {

        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nomRes = dataSnapshot.child("nom").getValue().toString();
                String prenomRes = dataSnapshot.child("prenom").getValue().toString();
                String sexeRes = dataSnapshot.child("sexeRes").getValue().toString();
                String dateNaisRes = dataSnapshot.child("dateNaissanceRes").getValue().toString();
                String typeSangRes = dataSnapshot.child("typeSanguin").getValue().toString();
                nom.setText(nomRes);
                prenom.setText(prenomRes);
                sexe.setText(sexeRes);
                dateNaissance.setText(dateNaisRes);
                typeSang.setText(typeSangRes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startModifProfilActivity(String key) {
        Intent intent=new Intent(ConsulterProfilResident.this, ModifierInfoResident.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrProfilRes) {
            finish();
        }
        if (view.getId() == R.id.modifierResProfil) {
            startModifProfilActivity(key);
        }
        if (view.getId() == R.id.maladieResProfil) {
            startMaladieActivity(key);
        }
    }

    private void startMaladieActivity(String key) {
        Intent intent=new Intent(ConsulterProfilResident.this, ConsulterListeMaladie.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }
}
