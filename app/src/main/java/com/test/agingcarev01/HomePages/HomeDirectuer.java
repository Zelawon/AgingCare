package com.test.agingcarev01.HomePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeInfirmier;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeSurveillant;
import com.test.agingcarev01.FonctionsProfil.ConsulterPropreProfil;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilEmployeeDialog.ModifierMotDePasseDialog;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeDirectuer extends AppCompatActivity implements View.OnClickListener, ModifierMotDePasseDialog.ModifMotDePasseDialogListner {
    private Button logoutDirecBT,consulterProfDirecBT,modifierMdpDirecBT,
            consulterListeSurveillantBT,consulterListeInfirmierBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_directuer);

        mAuth = FirebaseAuth.getInstance();


        modifierMdpDirecBT=findViewById(R.id.modifierMdpDirec);
        modifierMdpDirecBT.setOnClickListener(this);


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

        if(view.getId()==R.id.consulterProfDirec){
            startActivity(new Intent(HomeDirectuer.this, ConsulterPropreProfil.class));
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
