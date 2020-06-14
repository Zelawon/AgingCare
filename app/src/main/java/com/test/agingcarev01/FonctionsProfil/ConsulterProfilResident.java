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
import com.test.agingcarev01.FonctionsSurveillant.AffecterInfirmier.AffecteeInfirmiereResident;
import com.test.agingcarev01.FonctionsSurveillant.ConsulterStatistiqueResident;
import com.test.agingcarev01.FonctionsSurveillant.Maladie.ConsulterListeMaladie;
import com.test.agingcarev01.FonctionsSurveillant.ModifierInfoResident;
import com.test.agingcarev01.FonctionsSurveillant.Poids.ConsulterListePoids;
import com.test.agingcarev01.FonctionsSurveillant.RendezVous.ConsulterListeRendezVous;
import com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie.ConsulterListeTauxGlycemique;
import com.test.agingcarev01.FonctionsSurveillant.TensionArterielle.ConsulterListeTensionArterielle;
import com.test.agingcarev01.FonctionsSurveillant.Traitement.ConsulterListeTraitement;
import com.test.agingcarev01.R;

public class ConsulterProfilResident extends AppCompatActivity implements View.OnClickListener {
    private TextView nom, prenom, sexe, dateNaissance, typeSang;
    private DatabaseReference databaseReference;
    private Button retourFrProfilResBT, modifierResProfilBT;
    private ImageButton maladieResProfil,statestiqueResProfilBT,poidsResProfilBT,
            tauxGlycemiqueResProfil, tensionArterielleResProfil, rendezVousResProfil,
            traitementResProfil,smartWatchResProfil,infirmeirResProfilBT;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_resident);

        nom = findViewById(R.id.nomResProfil);
        prenom = findViewById(R.id.prenomResProfil);
        sexe = findViewById(R.id.sexeResProfil);
        dateNaissance = findViewById(R.id.dateNasiResProfil);
        typeSang = findViewById(R.id.typeSangResProfil);

        rendezVousResProfil = findViewById(R.id.rendezVousResProfil);
        rendezVousResProfil.setOnClickListener(this);

        retourFrProfilResBT = findViewById(R.id.retourFrProfilRes);
        retourFrProfilResBT.setOnClickListener(this);

        statestiqueResProfilBT = findViewById(R.id.statestiqueResProfil);
        statestiqueResProfilBT.setOnClickListener(this);

        poidsResProfilBT = findViewById(R.id.poidsResProfil);
        poidsResProfilBT.setOnClickListener(this);

        infirmeirResProfilBT = findViewById(R.id.infirmeirResProfil);
        infirmeirResProfilBT.setOnClickListener(this);

        maladieResProfil = findViewById(R.id.maladieResProfil);
        maladieResProfil.setOnClickListener(this);

        modifierResProfilBT = findViewById(R.id.modifierResProfil);
        modifierResProfilBT.setOnClickListener(this);

        traitementResProfil=findViewById(R.id.traitementResProfil);
        traitementResProfil.setOnClickListener(this);

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

    private void startModifProfilActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ModifierInfoResident.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
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
        if (view.getId() == R.id.modifierResProfil) {
            startModifProfilActivity(key);
        }
        if (view.getId() == R.id.statestiqueResProfil) {
            startStatistiqueActivity(key);
        }
        if (view.getId() == R.id.maladieResProfil) {
            startMaladieActivity(key);
        }
        if (view.getId() == R.id.infirmeirResProfil) {
            startInfirmierActivity(key);
        }
        if (view.getId() == R.id.poidsResProfil) {
            startPoidsActivity(key);
        }
        if (view.getId() == R.id.rendezVousResProfil) {
            startRDVActivity(key);
        }
        if (view.getId()==R.id.traitementResProfil){
            startTraitementActivity();
        }
    }

    private void startTraitementActivity() {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListeTraitement.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startRDVActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListeRendezVous.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startTensionArterielleProfilActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListeTensionArterielle.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startTauxGlycemiqueActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListeTauxGlycemique.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startStatistiqueActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterStatistiqueResident.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startInfirmierActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, AffecteeInfirmiereResident.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startMaladieActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListeMaladie.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }

    private void startPoidsActivity(String key) {
        Intent intent = new Intent(ConsulterProfilResident.this, ConsulterListePoids.class);
        intent.putExtra("key", this.key);
        startActivity(intent);
    }
}
