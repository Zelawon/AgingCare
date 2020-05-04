package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteSurveillant;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class DirectuerHome extends AppCompatActivity implements View.OnClickListener {
    private Button creeInfBT,creeSurBT,consultInfBT,consultSurBT,logoutDirecBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directuer_home);

        mAuth = FirebaseAuth.getInstance();

        creeInfBT=findViewById(R.id.creerCompteInfirmier);
        creeInfBT.setOnClickListener(this);

        creeSurBT=findViewById(R.id.creerCompteSurveillant);
        creeSurBT.setOnClickListener(this);

        consultInfBT=findViewById(R.id.consulterListeInfirmier);
        consultInfBT.setOnClickListener(this);

        consultSurBT=findViewById(R.id.consulterListeSurveillant);
        consultSurBT.setOnClickListener(this);

        logoutDirecBT=findViewById(R.id.deconnecterDierecteur);
        logoutDirecBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.creerCompteSurveillant){
            startActivity(new Intent(DirectuerHome.this, CreerCompteSurveillant.class));
        }
        if(view.getId()==R.id.deconnecterDierecteur){
            mAuth.signOut();
            startActivity(new Intent(DirectuerHome.this, MainActivity.class));
            finish();
        }

    }
}
