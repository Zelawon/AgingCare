package com.test.agingcarev01.FonctionsProfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.FonctionsSurveillant.Poids.ConsulterListePoids;
import com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie.ConsulterListeTauxGlycemique;
import com.test.agingcarev01.FonctionsSurveillant.TensionArterielle.ConsulterListeTensionArterielle;
import com.test.agingcarev01.R;

public class ConsulterProfilResidentPourInfirmier extends AppCompatActivity implements View.OnClickListener {
    private TextView nom, prenom, sexe, dateNaissance, typeSang;
    private DatabaseReference databaseReference;
    private Button retourFrProfilResBT;
    private ImageButton poidsResProfilBT, tauxGlycemiqueResProfil, tensionArterielleResProfil;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_resident_infirmier);

        nom = findViewById(R.id.nomResProfil);
        prenom = findViewById(R.id.prenomResProfil);
        sexe = findViewById(R.id.sexeResProfil);
        dateNaissance = findViewById(R.id.dateNasiResProfil);
        typeSang = findViewById(R.id.typeSangResProfil);

        retourFrProfilResBT = findViewById(R.id.retourFrProfilRes);
        retourFrProfilResBT.setOnClickListener(this);

        poidsResProfilBT = findViewById(R.id.poidsResProfil);
        poidsResProfilBT.setOnClickListener(this);

        tauxGlycemiqueResProfil = findViewById(R.id.tauxGlycemiqueResProfil);
        tauxGlycemiqueResProfil.setOnClickListener(this);

        tensionArterielleResProfil = findViewById(R.id.tensionArterielleResProfil);
        tensionArterielleResProfil.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident");
        key = getIntent().getStringExtra("id");
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrProfilRes) {
            finish();
        }
        if (view.getId() == R.id.tensionArterielleResProfil) {
            startTensionArterielleProfilActivity(key);
        }
        if (view.getId() == R.id.tauxGlycemiqueResProfil) {
            startTauxGlycemiqueActivity(key);
        }
        if (view.getId() == R.id.poidsResProfil) {
            startPoidsActivity(key);
        }
    }

    private void startTensionArterielleProfilActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResidentPourInfirmier.this, ConsulterListeTensionArterielle.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startTauxGlycemiqueActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResidentPourInfirmier.this, ConsulterListeTauxGlycemique.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startPoidsActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResidentPourInfirmier.this, ConsulterListePoids.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }
}
