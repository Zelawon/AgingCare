package com.test.agingcarev01.FonctionsSurveillant;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifNomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifPrenomDialog;
import com.test.agingcarev01.FonctionsProfil.ModifProfilCommunDialog.ModifSexeDialog;
import com.test.agingcarev01.FonctionsProfil.ModifierProfilResidentDialog.ModiftypeSanguinDialog;
import com.test.agingcarev01.R;

import java.util.Calendar;

public class ModifierInfoResident extends AppCompatActivity implements View.OnClickListener,
        ModifNomDialog.ModifNomDialogListner,
        ModifPrenomDialog.ModifPrenomDialogListner,
        ModifSexeDialog.ModifSexeDialogListner,
        ModiftypeSanguinDialog.ModiftypeSanguinDialogListner,
        DatePickerDialog.OnDateSetListener{
    String key,date="";
    TextView nom,prenom,sexe,dateNaissance,typeSang;
    ImageView modifNomResBT,modifPrenomResBT,modifSexeResBT,modifDateNaisResBT,modifTypeSangResBT;
    Button retourFrModifProfilResBT;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_info_resident);

        nom=findViewById(R.id.modifNomResProfil);
        prenom=findViewById(R.id.modifPrenomResProfil);
        sexe=findViewById(R.id.modifSexeResProfil);
        dateNaissance=findViewById(R.id.modifDateNasiResProfil);
        typeSang=findViewById(R.id.modifTypeSangResProfil);

        retourFrModifProfilResBT=findViewById(R.id.retourFrModifProfilRes);
        retourFrModifProfilResBT.setOnClickListener(this);
        modifNomResBT=findViewById(R.id.modifNomRes);
        modifNomResBT.setOnClickListener(this);
        modifPrenomResBT=findViewById(R.id.modifPrenomRes);
        modifPrenomResBT.setOnClickListener(this);
        modifSexeResBT=findViewById(R.id.modifSexeRes);
        modifSexeResBT.setOnClickListener(this);
        modifDateNaisResBT=findViewById(R.id.modifDateNaisRes);
        modifDateNaisResBT.setOnClickListener(this);
        modifTypeSangResBT=findViewById(R.id.modifTypeSangRes);
        modifTypeSangResBT.setOnClickListener(this);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident");
        key=getIntent().getStringExtra("key");

        fillProfileInfo(key);
    }

    private void fillProfileInfo(String id) {
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nomRes = dataSnapshot.child("nom").getValue().toString();
                String prenomRes = dataSnapshot.child("prenom").getValue().toString();
                String sexeRes = dataSnapshot.child("sexeRes").getValue().toString();
                String dateNaisRes = dataSnapshot.child("dateNaissanceRes").getValue().toString();
                String typeSangRes = dataSnapshot.child("typeSanguin").getValue().toString();
                nom.setText(nomRes);
                prenom.setText(prenomRes);
                sexe.setText(sexeRes);
                dateNaissance.setText(dateNaisRes);
                typeSang.setText(typeSangRes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrModifProfilRes) {
            finish();
        }
        if (view.getId() == R.id.modifNomRes) {
            openModifNom();
        }
        if (view.getId() == R.id.modifPrenomRes) {
            openModifPrenom();
        }
        if (view.getId() == R.id.modifSexeRes) {
            openModifSexe();
        }
        if (view.getId() == R.id.modifDateNaisRes) {
            openModifDateNaissance();
        }
        if (view.getId() == R.id.modifTypeSangRes) {
            openModifTypeSang();
        }
    }

    private void openModifDateNaissance() {
        DatePickerDialog datePickerDialog =new DatePickerDialog(
                ModifierInfoResident.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //janvier = month 0
        month++;
        date = day + "/" + month + "/" + year;
        FirebaseDatabase.getInstance().getReference("Resident").child(key).child("dateNaissanceRes").setValue(date);
        fillProfileInfo(key);
    }

    private void openModifTypeSang() {
        ModiftypeSanguinDialog modiftypeSanguinDialog = new ModiftypeSanguinDialog();
        modiftypeSanguinDialog.show(getSupportFragmentManager(),"Modifier TypeSanguin");
    }

    private void openModifSexe() {
        ModifSexeDialog modifSexeDialog = new ModifSexeDialog();
        modifSexeDialog.show(getSupportFragmentManager(),"Modifier Sexe");
    }

    private void openModifPrenom() {
        ModifPrenomDialog modifPrenomDialog = new ModifPrenomDialog();
        modifPrenomDialog.show(getSupportFragmentManager(),"Modifer Prenom");
    }

    private void openModifNom() {
        ModifNomDialog modifNomDialog = new ModifNomDialog();
        modifNomDialog.show(getSupportFragmentManager(),"Modifer Nom");
    }

    @Override
    public void applyNvNom(String nom) {
        FirebaseDatabase.getInstance().getReference("Resident").child(key).child("test").setValue(nom);
        fillProfileInfo(key);
    }

    @Override
    public void applyNvPrenom(String prenom) {
        FirebaseDatabase.getInstance().getReference("Resident").child(key).child("prenom").setValue(prenom);
        fillProfileInfo(key);
    }

    @Override
    public void applyNvSexe(String sexe) {
        FirebaseDatabase.getInstance().getReference("Resident").child(key).child("sexeRes").setValue(sexe);
        fillProfileInfo(key);
    }

    @Override
    public void applyNvType(String type) {
        FirebaseDatabase.getInstance().getReference("Resident").child(key).child("typeSanguin").setValue(type);
        fillProfileInfo(key);
    }
}
