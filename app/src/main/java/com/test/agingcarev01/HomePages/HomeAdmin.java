package com.test.agingcarev01.HomePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeDirecteur;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeInfirmier;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeSurveillant;
import com.test.agingcarev01.FonctionsAdmin.CreeCompteAdmin;
import com.test.agingcarev01.FonctionsProfil.ConsulterPropreProfil;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeAdmin extends AppCompatActivity implements View.OnClickListener {
    private Button creeCompteAdminBT, logoutAdminBT, consulterProfAdminBT,
            consulterListeSurveillantADBT, consulterListeInfirmierADBT, consulterListeDirecteurADBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        mAuth = FirebaseAuth.getInstance();

        consulterListeDirecteurADBT = findViewById(R.id.consulterListeDirecteurAD);
        consulterListeDirecteurADBT.setOnClickListener(this);

        consulterListeInfirmierADBT = findViewById(R.id.consulterListeInfirmierAD);
        consulterListeInfirmierADBT.setOnClickListener(this);

        consulterListeSurveillantADBT = findViewById(R.id.consulterListeSurveillantAD);
        consulterListeSurveillantADBT.setOnClickListener(this);


        creeCompteAdminBT = findViewById(R.id.creeCompteAdmin);
        creeCompteAdminBT.setOnClickListener(this);

        consulterProfAdminBT = findViewById(R.id.consulterProfAdmin);
        consulterProfAdminBT.setOnClickListener(this);


        logoutAdminBT = findViewById(R.id.deconnecterAdmin);
        logoutAdminBT.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.consulterListeInfirmierAD) {
            startActivity(new Intent(HomeAdmin.this, ConsulterListeInfirmier.class));
        }
        if (view.getId() == R.id.consulterListeSurveillantAD) {
            startActivity(new Intent(HomeAdmin.this, ConsulterListeSurveillant.class));
        }
        if (view.getId() == R.id.consulterListeDirecteurAD) {
            startActivity(new Intent(HomeAdmin.this, ConsulterListeDirecteur.class));
        }
        if (view.getId() == R.id.creeCompteAdmin) {
            startActivity(new Intent(HomeAdmin.this, CreeCompteAdmin.class));
        }
        if (view.getId() == R.id.consulterProfAdmin) {
            startActivity(new Intent(HomeAdmin.this, ConsulterPropreProfil.class));
        }
        if (view.getId() == R.id.deconnecterAdmin) {
            mAuth.signOut();
            startActivity(new Intent(HomeAdmin.this, MainActivity.class));
            finishAffinity();
        }

    }
}
