package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsAdmin.TestCompteActive;
import com.test.agingcarev01.FonctionsCommunes.ConsulterProfil;
import com.test.agingcarev01.FonctionsCommunes.MiseAJourMotDePasse;
import com.test.agingcarev01.FonctionsCommunes.MiseAJourProfil;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class InfirmierHome extends AppCompatActivity implements View.OnClickListener {
    private Button consulterProfInfBT,deconnecterInfirmierBT,buttontest,modifierProfInfBT,modifierMdpInfBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infirmier_home);

        mAuth = FirebaseAuth.getInstance();

        consulterProfInfBT=findViewById(R.id.consulterProfInf);
        consulterProfInfBT.setOnClickListener(this);

        deconnecterInfirmierBT=findViewById(R.id.deconnecterInfirmier);
        deconnecterInfirmierBT.setOnClickListener(this);

        modifierProfInfBT=findViewById(R.id.modifierProfInf);
        modifierProfInfBT.setOnClickListener(this);

        modifierMdpInfBT=findViewById(R.id.modifierMdpInf);
        modifierMdpInfBT.setOnClickListener(this);

        buttontest=findViewById(R.id.buttontest);
        buttontest.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG Erreur : ", "Hello! resume Infirmier Home");
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.consulterProfInf){
            startActivity(new Intent(InfirmierHome.this, ConsulterProfil.class));
        }
        if(view.getId()==R.id.modifierProfInf){
            startActivity(new Intent(InfirmierHome.this, MiseAJourProfil.class));
        }
        if(view.getId()==R.id.modifierMdpInf){
            startActivity(new Intent(InfirmierHome.this, MiseAJourMotDePasse.class));
        }
        if(view.getId()==R.id.deconnecterInfirmier){
            mAuth.signOut();
            startActivity(new Intent(InfirmierHome.this, MainActivity.class));
            finishAffinity ();
        }
        if(view.getId()==R.id.buttontest){
            startActivity(new Intent(InfirmierHome.this, TestCompteActive.class));
        }
    }
}
