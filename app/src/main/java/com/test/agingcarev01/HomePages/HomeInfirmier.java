package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilEmployee;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilDialog.ModifierMotDePasseDialog;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeInfirmier extends AppCompatActivity implements View.OnClickListener, ModifierMotDePasseDialog.ModifMotDePasseDialogListner {
    private Button consulterProfInfBT,deconnecterInfirmierBT,modifierMdpInfBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_infirmier);

        mAuth = FirebaseAuth.getInstance();

        consulterProfInfBT=findViewById(R.id.consulterProfInf);
        consulterProfInfBT.setOnClickListener(this);

        deconnecterInfirmierBT=findViewById(R.id.deconnecterInfirmier);
        deconnecterInfirmierBT.setOnClickListener(this);

        modifierMdpInfBT=findViewById(R.id.modifierMdpInf);
        modifierMdpInfBT.setOnClickListener(this);

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
        if(view.getId()==R.id.consulterProfInf){
            startActivity(new Intent(HomeInfirmier.this, ConsulterProfilEmployee.class));
        }

        if(view.getId()==R.id.modifierMdpInf){
            openModifPass();
        }
        if(view.getId()==R.id.deconnecterInfirmier){
            mAuth.signOut();
            startActivity(new Intent(HomeInfirmier.this, MainActivity.class));
            finishAffinity ();
        }
    }

}
