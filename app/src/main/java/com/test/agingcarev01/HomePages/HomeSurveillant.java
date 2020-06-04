package com.test.agingcarev01.HomePages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.ConsulterListes.Activities.ConsulterListeResident;
import com.test.agingcarev01.FonctionsProfil.ConsulterPropreProfil;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilEmployeeDialog.ModifierMotDePasseDialog;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class HomeSurveillant extends AppCompatActivity implements View.OnClickListener, ModifierMotDePasseDialog.ModifMotDePasseDialogListner {
    private Button consulterProfSurvBT, deconnecterSurveillantBT, modifierMdpSurvBT, consulterListeResidentBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_surveillant);

        mAuth = FirebaseAuth.getInstance();

        consulterProfSurvBT = findViewById(R.id.consulterProfSurv);
        consulterProfSurvBT.setOnClickListener(this);

        consulterListeResidentBT = findViewById(R.id.consulterListeResident);
        consulterListeResidentBT.setOnClickListener(this);

        deconnecterSurveillantBT = findViewById(R.id.deconnecterSurveillant);
        deconnecterSurveillantBT.setOnClickListener(this);

        modifierMdpSurvBT = findViewById(R.id.modifierMdpSurv);
        modifierMdpSurvBT.setOnClickListener(this);
    }

    private void openModifPass() {
        ModifierMotDePasseDialog modifierMotDePasseDialog = new ModifierMotDePasseDialog();
        modifierMotDePasseDialog.show(getSupportFragmentManager(), "Modifier Mot De Passe");
    }

    @Override
    public void applyNvPass(String nvMotDePasse) {
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(nvMotDePasse);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.consulterProfSurv) {
            startActivity(new Intent(HomeSurveillant.this, ConsulterPropreProfil.class));
        }
        if (view.getId() == R.id.modifierMdpSurv) {
            openModifPass();
        }
        if (view.getId() == R.id.consulterListeResident) {
            startActivity(new Intent(HomeSurveillant.this, ConsulterListeResident.class));
        }
        if (view.getId() == R.id.deconnecterSurveillant) {
            mAuth.signOut();
            startActivity(new Intent(HomeSurveillant.this, MainActivity.class));
            finishAffinity();
        }
    }
}
