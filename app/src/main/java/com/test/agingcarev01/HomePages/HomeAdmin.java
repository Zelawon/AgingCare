package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsAdmin.CreeCompteAdmin;
import com.test.agingcarev01.FonctionsAdmin.CreerCompteDirecteur;
import com.test.agingcarev01.FonctionsCommunes.ConsulterProfil;
import com.test.agingcarev01.FonctionsCommunes.ModifierProfilDialog.ModifierMotDePasseDialog;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteInfirmier;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteSurveillant;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeAdmin extends AppCompatActivity implements View.OnClickListener, ModifierMotDePasseDialog.ModifMotDePasseDialogListner {
    private Button creeCompteDirecBT,creeCompteAdminBT,logoutAdminBT,creeCompteInfBT,creeCompteSurvBT,consulterProfAdminBT,modifierMdpAdminBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

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

        modifierMdpAdminBT=findViewById(R.id.modifierMdpAdmin);
        modifierMdpAdminBT.setOnClickListener(this);
    }

    private void openModifPass() {
        ModifierMotDePasseDialog modifierMotDePasseDialog =new ModifierMotDePasseDialog();
        modifierMotDePasseDialog.show(getSupportFragmentManager(),"Modifier Mot De Passe");
    }

    @Override
    public void applyNvPass(String nvMotDePasse) {
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(nvMotDePasse);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.creeCompteDirec){
            startActivity(new Intent(HomeAdmin.this, CreerCompteDirecteur.class));
        }
        if(view.getId()==R.id.creeCompteAdmin){
            startActivity(new Intent(HomeAdmin.this, CreeCompteAdmin.class));
        }
        if(view.getId()==R.id.creeCompteInf){
            startActivity(new Intent(HomeAdmin.this, CreerCompteInfirmier.class));
        }
        if(view.getId()==R.id.creeCompteSurv){
            startActivity(new Intent(HomeAdmin.this, CreerCompteSurveillant.class));
        }
        if(view.getId()==R.id.consulterProfAdmin){
            startActivity(new Intent(HomeAdmin.this, ConsulterProfil.class));
        }
        if(view.getId()==R.id.modifierMdpAdmin){
            openModifPass();
        }
        if(view.getId()==R.id.deconnecterAdmin){
            mAuth.signOut();
            startActivity(new Intent(HomeAdmin.this, MainActivity.class));
            finishAffinity ();
        }

    }
}
