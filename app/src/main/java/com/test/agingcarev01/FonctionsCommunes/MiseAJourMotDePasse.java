package com.test.agingcarev01.FonctionsCommunes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.R;

public class MiseAJourMotDePasse extends AppCompatActivity implements View.OnClickListener {
    private Button modifMdpOKBT, retourFormModifMdpBT;
    private EditText nvMotPass,confNvMotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mise_a_jour_mot_de_passe);

        //EditTexts
        nvMotPass=findViewById(R.id.nvMotPass);
        confNvMotPass=findViewById(R.id.confNvMotPass);

        //Buttons
        modifMdpOKBT=findViewById(R.id.modifMdpOK);
        modifMdpOKBT.setOnClickListener(this);
        retourFormModifMdpBT=findViewById(R.id.retourFormModifMdp);
        retourFormModifMdpBT.setOnClickListener(this);
    }

    private boolean modifierMotDePasse() {
        Log.e("TAG Erreur : ", "In fct modif pass");
        final String pass = nvMotPass.getText().toString();
        final String confPass = confNvMotPass.getText().toString();

        if (pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Champ Mot de Passe vide", Toast.LENGTH_SHORT).show();
            nvMotPass.requestFocus();
        }else if (pass.length()<6){
            Toast.makeText(getApplicationContext(), "Mot de Passe (Au Moins 6 Caracteres)", Toast.LENGTH_SHORT).show();
            nvMotPass.requestFocus();
        }else if ((confPass.isEmpty())){
            Toast.makeText(getApplicationContext(), "Champ Confirmer Mot de Passe vide", Toast.LENGTH_SHORT).show();
            confNvMotPass.requestFocus();
        }else if (!(pass.equals(confPass))){
            Toast.makeText(getApplicationContext(), "Erreur Confirmer Mot de Passe", Toast.LENGTH_SHORT).show();
            confNvMotPass.requestFocus();
        }else{
            FirebaseAuth.getInstance().getCurrentUser().updatePassword(pass);
            Log.e("TAG Erreur : ", "modif pass done");
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.modifMdpOK){
            if (modifierMotDePasse()) {
                Toast.makeText(getApplicationContext(), "Mot de Passe Mis a Jour", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(view.getId()==R.id.retourFormModifMdp){
            finish();
        }
    }

}
