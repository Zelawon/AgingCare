package com.test.agingcarev01.FonctionsProfil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifNomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifPrenomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifSexeDialog;
import com.test.agingcarev01.R;

import es.dmoral.toasty.Toasty;

public class ConsulterProfilEmployee extends AppCompatActivity implements View.OnClickListener,
        ModifNomDialog.ModifNomDialogListner,
        ModifPrenomDialog.ModifPrenomDialogListner,
        ModifSexeDialog.ModifSexeDialogListner {
    private String emailEmpRecu;
    private ImageView retourFrProfBT;
    private ImageView updateSexeBT, updateNomBT, updatePrenomBT;
    private TextView nom, prenom, role, email, sexe, nomTXT, prenomTXT, sexeTXT;
    private String userkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_employee);

        emailEmpRecu = getIntent().getStringExtra("email");

        //Text View nom champ
        nomTXT = findViewById(R.id.nomEmpTextView);
        prenomTXT = findViewById(R.id.prenomEmpTextView);
        sexeTXT = findViewById(R.id.sexeEmpTextView);
        //Text View a modifier
        nom = findViewById(R.id.txtNomEmp);
        prenom = findViewById(R.id.txtPrenomEmp);
        role = findViewById(R.id.txtRoleEmp);
        email = findViewById(R.id.txtEmailEmp);
        sexe = findViewById(R.id.txtSexEmp);
        //Buttons
        retourFrProfBT = findViewById(R.id.retourFrProfEmp);
        retourFrProfBT.setOnClickListener(this);
        updateNomBT = findViewById(R.id.updateNomEmp);
        updateNomBT.setOnClickListener(this);
        updatePrenomBT = findViewById(R.id.updatePrenomEmp);
        updatePrenomBT.setOnClickListener(this);
        updateSexeBT = findViewById(R.id.updateSexeEmp);
        updateSexeBT.setOnClickListener(this);

        afficherProfil(emailEmpRecu);
    }

    private void afficherProfil(String emailEmpRecu) {
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(emailEmpRecu);
        //Load interface avec les champs de chaque role
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshott) {
                if (dataSnapshott.exists()) {
                    for (DataSnapshot roleSnapshott : dataSnapshott.getChildren()) {
                        userkey = roleSnapshott.getKey();
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

                        } else {
                            Toasty.error(getApplicationContext(), "Erreur Role", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toasty.error(getApplicationContext(), "Erreur Consulter Profil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    private void openModifNom() {
        ModifNomDialog modifNomDialog = new ModifNomDialog();
        modifNomDialog.show(getSupportFragmentManager(), "Modifer Nom");
    }

    private void openModifPrenom() {
        ModifPrenomDialog modifPrenomDialog = new ModifPrenomDialog();
        modifPrenomDialog.show(getSupportFragmentManager(), "Modifer Prenom");
    }

    private void openModifSexe() {
        ModifSexeDialog modifSexeDialog = new ModifSexeDialog();
        modifSexeDialog.show(getSupportFragmentManager(), "Modifier Sexe");
    }


    @Override
    public void applyNvNom(String nom) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("nom").setValue(nom);
        afficherProfil(emailEmpRecu);
    }

    @Override
    public void applyNvPrenom(String prenom) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("prenom").setValue(prenom);
        afficherProfil(emailEmpRecu);
    }

    @Override
    public void applyNvSexe(String sexe) {
        FirebaseDatabase.getInstance().getReference("Employee").child(userkey).child("sexe").setValue(sexe);
        afficherProfil(emailEmpRecu);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrProfEmp) {
            finish();
        }
        if (view.getId() == R.id.updateNomEmp) {
            openModifNom();
        }
        if (view.getId() == R.id.updatePrenomEmp) {
            openModifPrenom();
        }
        if (view.getId() == R.id.updateSexeEmp) {
            openModifSexe();
        }

    }
}
