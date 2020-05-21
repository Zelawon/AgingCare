package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsCommunes.ConsulterListeX.ConsulterListeInfirmier;
import com.test.agingcarev01.FonctionsCommunes.ConsulterListeX.ConsulterListeSurveillant;
import com.test.agingcarev01.FonctionsCommunes.ConsulterProfil;
import com.test.agingcarev01.FonctionsCommunes.ModifierProfilDialog.ModifierMotDePasseDialog;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteInfirmier;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteSurveillant;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeDirectuer extends AppCompatActivity implements View.OnClickListener, ModifierMotDePasseDialog.ModifMotDePasseDialogListner {
    private Button creeInfBT,creeSurBT, logoutDirecBT,consulterProfDirecBT,
            modifierMdpDirecBT,consulterListeSurveillantBT,consulterListeInfirmierBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_directuer);

        mAuth = FirebaseAuth.getInstance();

        creeInfBT=findViewById(R.id.creerCompteInfirmier);
        creeInfBT.setOnClickListener(this);

        modifierMdpDirecBT=findViewById(R.id.modifierMdpDirec);
        modifierMdpDirecBT.setOnClickListener(this);

        creeSurBT=findViewById(R.id.creerCompteSurveillant);
        creeSurBT.setOnClickListener(this);

        consulterListeInfirmierBT=findViewById(R.id.consulterListeInfirmier);
        consulterListeInfirmierBT.setOnClickListener(this);

        consulterProfDirecBT=findViewById(R.id.consulterProfDirec);
        consulterProfDirecBT.setOnClickListener(this);

        consulterListeSurveillantBT=findViewById(R.id.consulterListeSurveillant);
        consulterListeSurveillantBT.setOnClickListener(this);

        logoutDirecBT=findViewById(R.id.deconnecterDierecteur);
        logoutDirecBT.setOnClickListener(this);
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

        if(view.getId()==R.id.creerCompteSurveillant){
            startActivity(new Intent(HomeDirectuer.this, CreerCompteSurveillant.class));
        }
        if(view.getId()==R.id.creerCompteInfirmier){
            startActivity(new Intent(HomeDirectuer.this, CreerCompteInfirmier.class));
        }
        if(view.getId()==R.id.consulterProfDirec){
            startActivity(new Intent(HomeDirectuer.this, ConsulterProfil.class));
        }
        if(view.getId()==R.id.modifierMdpDirec){
            openModifPass();
        }
        if(view.getId()==R.id.consulterListeSurveillant){
            startActivity(new Intent(HomeDirectuer.this, ConsulterListeSurveillant.class));
        }
        if(view.getId()==R.id.consulterListeInfirmier){
            startActivity(new Intent(HomeDirectuer.this, ConsulterListeInfirmier.class));
        }
        if(view.getId()==R.id.deconnecterDierecteur){
            mAuth.signOut();
            startActivity(new Intent(HomeDirectuer.this, MainActivity.class));
            finishAffinity ();
        }

    }
}
