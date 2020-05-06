package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsAdmin.CreeCompteAdmin;
import com.test.agingcarev01.FonctionsAdmin.CreerCompteDirecteur;
import com.test.agingcarev01.FonctionsAdmin.TestCompteActive;
import com.test.agingcarev01.FonctionsCommunes.ConsulterProfil;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteInfirmier;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteSurveillant;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {
    private Button creeCompteDirecBT,creeCompteAdminBT,logoutAdminBT,creeCompteInfBT,creeCompteSurvBT,consulterProfAdminBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mAuth = FirebaseAuth.getInstance();

        creeCompteDirecBT=findViewById(R.id.creeCompteDirec);
        creeCompteDirecBT.setOnClickListener(this);

        creeCompteAdminBT=findViewById(R.id.creeCompteAdmin);
        creeCompteAdminBT.setOnClickListener(this);

        consulterProfAdminBT=findViewById(R.id.consulterProfAdmin);
        consulterProfAdminBT.setOnClickListener(this);

        creeCompteInfBT=findViewById(R.id.creeCompteInf);
        creeCompteInfBT.setOnClickListener(this);

        creeCompteSurvBT=findViewById(R.id.creeCompteSurv);
        creeCompteSurvBT.setOnClickListener(this);

        logoutAdminBT=findViewById(R.id.deconnecterAdmin);
        logoutAdminBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.creeCompteDirec){
            startActivity(new Intent(AdminHome.this, CreerCompteDirecteur.class));
        }
        if(view.getId()==R.id.creeCompteAdmin){
            startActivity(new Intent(AdminHome.this, CreeCompteAdmin.class));
        }
        if(view.getId()==R.id.creeCompteInf){
            startActivity(new Intent(AdminHome.this, CreerCompteInfirmier.class));
        }
        if(view.getId()==R.id.creeCompteSurv){
            startActivity(new Intent(AdminHome.this, CreerCompteSurveillant.class));
        }
        if(view.getId()==R.id.consulterProfAdmin){
            startActivity(new Intent(AdminHome.this, ConsulterProfil.class));
        }
        if(view.getId()==R.id.deconnecterAdmin){
            mAuth.signOut();
            startActivity(new Intent(AdminHome.this, MainActivity.class));
            finish();
        }

    }
}
