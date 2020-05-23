package com.test.agingcarev01.FonctionsProfil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilDialog.ModifEmailDialog;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilDialog.ModifNomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilDialog.ModifPrenomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilDialog.ModifSexeDialog;
import com.test.agingcarev01.R;

public class ConsulterProfil extends AppCompatActivity implements View.OnClickListener,
        ModifEmailDialog.ModifEmailDialogListner,
        ModifNomDialog.ModifNomDialogListner,
        ModifPrenomDialog.ModifPrenomDialogListner,
        ModifSexeDialog.ModifSexeDialogListner {
    private Button retourFrProfBT,updateEmailBT,updateSexeBT,updateNomBT,updatePrenomBT;
    private TextView nom,prenom,role,email,sexe,nomTXT,prenomTXT,sexeTXT,textView11;
    private String userkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);
        Log.e("TAG Erreur : ", "Hello! consuter profil");

        //Text View nom champ
        nomTXT=findViewById(R.id.nomTextView);
        prenomTXT=findViewById(R.id.prenomTextView);
        sexeTXT=findViewById(R.id.sexeTextView);
        //Text View a modifier
        nom=findViewById(R.id.txtNom);
        prenom=findViewById(R.id.txtPrenom);
        role=findViewById(R.id.txtRole);
        email=findViewById(R.id.txtEmail);
        sexe=findViewById(R.id.txtSex);
        textView11=findViewById(R.id.textView11);
        //Buttons
        retourFrProfBT=findViewById(R.id.retourFrProf);
        retourFrProfBT.setOnClickListener(this);
        updateEmailBT=findViewById(R.id.updateEmail);
        updateEmailBT.setOnClickListener(this);
        updateNomBT=findViewById(R.id.updateNom);
        updateNomBT.setOnClickListener(this);
        updatePrenomBT=findViewById(R.id.updatePrenom);
        updatePrenomBT.setOnClickListener(this);
        updateSexeBT=findViewById(R.id.updateSexe);
        updateSexeBT.setOnClickListener(this);

        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        afficherProfil(currentUserEmail);
    }

    private void afficherProfil(String currentUserEmail) {

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);

        //Load interface avec les champs de chaque role
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshott) {
                if (dataSnapshott.exists()) {
                    for (DataSnapshot roleSnapshott : dataSnapshott.getChildren()) {
                        userkey=roleSnapshott.getKey();
                        String currentRole = roleSnapshott.child("role").getValue(String.class);
                        String currentEmail = roleSnapshott.child("email").getValue(String.class);
                        if (currentRole.equals("Admin")) {
                            prenomTXT.setVisibility(View.INVISIBLE);
                            nomTXT.setVisibility(View.INVISIBLE);
                            sexeTXT.setVisibility(View.INVISIBLE);
                            nom.setVisibility(View.INVISIBLE);
                            prenom.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            updateNomBT.setVisibility(View.INVISIBLE);
                            updatePrenomBT.setVisibility(View.INVISIBLE);
                            updateSexeBT.setVisibility(View.INVISIBLE);
                            role.setText(currentRole);
                            email.setText(currentEmail);

                        } else if (currentRole.equals("Directeur")) {
                            sexeTXT.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            updateSexeBT.setVisibility(View.INVISIBLE);
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        } else if (currentRole.equals("Surveillant")) {
                            sexeTXT.setVisibility(View.INVISIBLE);
                            sexe.setVisibility(View.INVISIBLE);
                            updateSexeBT.setVisibility(View.INVISIBLE);
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);

                        } else if (currentRole.equals("Infirmier")) {
                            String currentNom = roleSnapshott.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshott.child("prenom").getValue(String.class);
                            String currentSexe = roleSnapshott.child("sexe").getValue(String.class);
                            role.setText(currentRole);
                            email.setText(currentEmail);
                            nom.setText(currentNom);
                            prenom.setText(currentPrenom);
                            sexe.setText(currentSexe);

                        }else{
                            Toast.makeText(getApplicationContext(), "Erreur Role", Toast.LENGTH_SHORT).show();
                            Log.e("TAG Erreur : ", "Erreur Role");
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Erreur Consulter Profil", Toast.LENGTH_SHORT).show();
                    Log.e("TAG Erreur : ", "Erreur Consulter Profil");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }



    private void openModifEmail() {
        ModifEmailDialog modifEmailDialog = new ModifEmailDialog();
        modifEmailDialog.show(getSupportFragmentManager(),"Modifier Email");
    }

    private void openModifNom() {
        ModifNomDialog modifNomDialog = new ModifNomDialog();
        modifNomDialog.show(getSupportFragmentManager(),"Modifer Nom");
    }

    private void openModifPrenom() {
        ModifPrenomDialog modifPrenomDialog = new ModifPrenomDialog();
        modifPrenomDialog.show(getSupportFragmentManager(),"Modifer Prenom");
    }

    private void openModifSexe() {
        ModifSexeDialog modifSexeDialog = new ModifSexeDialog();
        modifSexeDialog.show(getSupportFragmentManager(),"Modifier Sexe");
    }

    @Override
    public void applyNvEmail(String email) {
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(email);
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("email").setValue(email);
        afficherProfil(email);
    }

    @Override
    public void applyNvNom(String nom) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("nom").setValue(nom);
        afficherProfil(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    public void applyNvPrenom(String prenom) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("prenom").setValue(prenom);
        afficherProfil(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    public void applyNvSexe(String sexe) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("sexe").setValue(sexe);
        afficherProfil(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrProf){
            finish();
        }
        if(view.getId()==R.id.updateEmail){
            openModifEmail();
        }
        if(view.getId()==R.id.updateNom){
            openModifNom();
        }
        if(view.getId()==R.id.updatePrenom){
            openModifPrenom();
        }
        if(view.getId()==R.id.updateSexe){
            openModifSexe();
        }
    }

}
