package com.test.agingcarev01.FonctionsCommunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.HomePages.AdminHome;
import com.test.agingcarev01.HomePages.DirectuerHome;
import com.test.agingcarev01.HomePages.InfirmierHome;
import com.test.agingcarev01.HomePages.SurveillantHome;
import com.test.agingcarev01.R;

public class ModifierProfil extends AppCompatActivity implements View.OnClickListener {
    private Button confModifBT, retourFormModifBT;
    private String  roleGeneral,userkey;
    private EditText modifEmail,modifNom,modifPrenom;
    private TextView modifSexeTextView,modifNomTextView,modifPrenomTextView;
    private RadioGroup modifsexeGroup;
    private RadioButton modifRadioHommeInf,modifRadioFemmeInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil);

        Log.e("TAG Erreur : ", "Hello! Modifier profil");

        //EditTexts
        modifEmail=findViewById(R.id.modifEmail);
        modifNom=findViewById(R.id.modifNom);
        modifPrenom=findViewById(R.id.modifPrenom);

        //Radio
        modifRadioHommeInf=findViewById(R.id.modifRadioHommeInf);
        modifRadioFemmeInf=findViewById(R.id.modifRadioFemmeInf);
        modifsexeGroup=findViewById(R.id.modifsexeGroup);

        //TextViews
        modifSexeTextView=findViewById(R.id.modifSexeTextView);
        modifNomTextView=findViewById(R.id.modifNomTextView);
        modifPrenomTextView=findViewById(R.id.modifPrenomTextView);

        //Buttons
        confModifBT=findViewById(R.id.confModif);
        confModifBT.setOnClickListener(this);
        retourFormModifBT=findViewById(R.id.retourFormModif);
        retourFormModifBT.setOnClickListener(this);

        //Remplir l'interface avec les donnees du profil
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        emailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot roleSnapshot : dataSnapshot.getChildren()) {
                        userkey=roleSnapshot.getKey();
                        ////Get Data de la BD
                        String currentRole = roleSnapshot.child("role").getValue(String.class);
                        roleGeneral=currentRole;
                        String currentEmail = roleSnapshot.child("email").getValue(String.class);
                        //pour Chaque role
                        if (currentRole.equals("Admin")) {
                            //Invisble from View selon Donnee Role
                            modifNomTextView.setVisibility(View.INVISIBLE);
                            modifNom.setVisibility(View.INVISIBLE);
                            modifPrenomTextView.setVisibility(View.INVISIBLE);
                            modifPrenom.setVisibility(View.INVISIBLE);
                            modifSexeTextView.setVisibility(View.INVISIBLE);
                            modifsexeGroup.setVisibility(View.INVISIBLE);
                            //Set les Champ avec les donnes du profil
                            modifEmail.setText(currentEmail);

                        }
                        else if (currentRole.equals("Directeur")) {
                            //Invisble from View selon Donnee Role
                            modifSexeTextView.setVisibility(View.INVISIBLE);
                            modifsexeGroup.setVisibility(View.INVISIBLE);
                            //Get Data de la BD
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            //Set les Champ avec les donnes du profil
                            modifEmail.setText(currentEmail);
                            modifNom.setText(currentNom);
                            modifPrenom.setText(currentPrenom);

                        }else if (currentRole.equals("Surveillant")) {
                            //Invisble from View selon Donnee Role
                            modifSexeTextView.setVisibility(View.INVISIBLE);
                            modifsexeGroup.setVisibility(View.INVISIBLE);
                            //Get Data de la BD
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            //Set les Champ avec les donnes du profil
                            modifEmail.setText(currentEmail);
                            modifNom.setText(currentNom);
                            modifPrenom.setText(currentPrenom);

                        }else if (currentRole.equals("Infirmier")) {
                            //Get Data de la BD
                            String currentNom = roleSnapshot.child("nom").getValue(String.class);
                            String currentPrenom = roleSnapshot.child("prenom").getValue(String.class);
                            String currentSexe = roleSnapshot.child("sexe").getValue(String.class);
                            //Set les Champ avec les donnes du profil
                            modifEmail.setText(currentEmail);
                            modifNom.setText(currentNom);
                            modifPrenom.setText(currentPrenom);;
                            //Set
                            if (currentSexe.equals("homme")){
                                modifRadioHommeInf.setChecked(true);
                                modifRadioFemmeInf.setChecked(false);
                            }else if (currentSexe.equals("femme")){
                                modifRadioHommeInf.setChecked(false);
                                modifRadioFemmeInf.setChecked(true);
                            }
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Erreur Modifier Profil", Toast.LENGTH_SHORT).show();
                    Log.e("TAG Erreur : ", "Erreur Modifier Profil");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    public void updateEmail(){
        FirebaseAuth.getInstance().getCurrentUser().updateEmail(modifEmail.getText().toString());
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("email").setValue(modifEmail.getText().toString());
    }

    public void modifierProfile(){
        final DatabaseReference employeeModifRef = FirebaseDatabase.getInstance().getReference("Employee");

        if (roleGeneral.equals("Admin")){
            updateEmail();
        }else if (roleGeneral.equals("Directeur")){
            updateEmail();
            //employeeModifRef.child(userkey).child("email").setValue(modifEmail.getText().toString());
            employeeModifRef.child(userkey).child("nom").setValue(modifNom.getText().toString());
            employeeModifRef.child(userkey).child("prenom").setValue(modifPrenom.getText().toString());

        }else if (roleGeneral.equals("Surveillant")){
            updateEmail();
            employeeModifRef.child(userkey).child("nom").setValue(modifNom.getText().toString());
            employeeModifRef.child(userkey).child("prenom").setValue(modifPrenom.getText().toString());

        }else if (roleGeneral.equals("Infirmier")){
            updateEmail();
            employeeModifRef.child(userkey).child("nom").setValue(modifNom.getText().toString());
            employeeModifRef.child(userkey).child("prenom").setValue(modifPrenom.getText().toString());

            if (modifRadioHommeInf.isChecked()){
                employeeModifRef.child(userkey).child("sexe").setValue("homme");
            }else if (modifRadioFemmeInf.isChecked()){
                employeeModifRef.child(userkey).child("sexe").setValue("femme");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.confModif){
            modifierProfile();
            //Toast.makeText(getApplicationContext(), "Profil Mis a Jour", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(view.getId()==R.id.retourFormModif){
            finish();
        }

    }
}
