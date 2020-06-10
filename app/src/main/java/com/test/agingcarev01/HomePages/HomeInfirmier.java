package com.test.agingcarev01.HomePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsInfirmier.RendezVous.ConsulterRendezVousAffecter;
import com.test.agingcarev01.FonctionsInfirmier.ResidentAffecter.ConsulterListeResidentAffecter;
import com.test.agingcarev01.FonctionsProfil.ConsulterPropreProfil;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeInfirmier extends AppCompatActivity implements View.OnClickListener {
    private ImageButton consulterProfInfBT, consulterListeResident, consulterListeRDV;
    private Button deconnecterInfirmierBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_infirmier);

        mAuth = FirebaseAuth.getInstance();

        consulterListeRDV = findViewById(R.id.consulterListeRDV);
        consulterListeRDV.setOnClickListener(this);

        consulterListeResident = findViewById(R.id.consulterListeResident);
        consulterListeResident.setOnClickListener(this);

        consulterProfInfBT = findViewById(R.id.consulterProfInf);
        consulterProfInfBT.setOnClickListener(this);

        deconnecterInfirmierBT = findViewById(R.id.deconnecterInfirmier);
        deconnecterInfirmierBT.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.consulterProfInf) {
            startActivity(new Intent(HomeInfirmier.this, ConsulterPropreProfil.class));
        }
        if (view.getId() == R.id.deconnecterInfirmier) {
            mAuth.signOut();
            startActivity(new Intent(HomeInfirmier.this, MainActivity.class));
            finishAffinity();
        }
        if (view.getId() == R.id.consulterListeResident) {
            startActivity(new Intent(HomeInfirmier.this, ConsulterListeResidentAffecter.class));
        }
        if (view.getId() == R.id.consulterListeRDV) {
            startActivity(new Intent(HomeInfirmier.this, ConsulterRendezVousAffecter.class));
        }
    }

}
