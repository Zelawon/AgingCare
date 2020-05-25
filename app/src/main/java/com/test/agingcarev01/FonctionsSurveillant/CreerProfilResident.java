package com.test.agingcarev01.FonctionsSurveillant;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.R;
import com.test.agingcarev01.Classe.ResidentClasse;

import java.util.Calendar;

public class CreerProfilResident extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView dateNaisTV;
    private Button creeResBT, retourBT;
    private EditText nomRes,prenomRes;
    private RadioButton hommRadio,femmRadio;
    private String typeSang,date="vide";
    private int idRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_profil_resident);

        Spinner staticSpinner = (Spinner) findViewById(R.id.spinnerTypeSanguin);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.typeSanguinArray,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        staticSpinner.setOnItemSelectedListener(this);

        nomRes=findViewById(R.id.nomRes);
        prenomRes=findViewById(R.id.prenomRes);
        femmRadio=findViewById(R.id.radioFemmeRes);
        hommRadio=findViewById(R.id.radioHommeRes);

        dateNaisTV=findViewById(R.id.TxtDateNais);
        dateNaisTV.setOnClickListener(this);
        creeResBT=findViewById(R.id.creerRes);
        creeResBT.setOnClickListener(this);
        retourBT=findViewById(R.id.retourFrCreerRes);
        retourBT.setOnClickListener(this);

        getIdResident();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                typeSang="A+";break;
            case 1:
                typeSang="A-";break;
            case 2:
                typeSang="B+";break;
            case 3:
                typeSang="B-";break;
            case 4:
                typeSang="AB+";break;
            case 5:
                typeSang="AB-";break;
            case 6:
                typeSang="O+";break;
            case 7:
                typeSang="O-";break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //janvier = month 0
        month++;
        date = day + "/" + month + "/" + year;
        dateNaisTV.setText(date);
    }

    private void showDatePickerDailog(){
        DatePickerDialog datePickerDialog =new DatePickerDialog(
                CreerProfilResident.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    private void creeProfilResident() {
        final String nom = nomRes.getText().toString().toLowerCase();
        final String prenom = prenomRes.getText().toString().toLowerCase();
        String sexeSelect="";

        if (nom.isEmpty()){
            Toast.makeText(getApplicationContext(), "Champ Nom vide", Toast.LENGTH_SHORT).show();
            nomRes.requestFocus();
        }else if (prenom.isEmpty()){
            Toast.makeText(getApplicationContext(), "Champ Prenom vide", Toast.LENGTH_SHORT).show();
            prenomRes.requestFocus();
        }else if ((!(femmRadio.isChecked()))&&(!(hommRadio.isChecked()))){
            Toast.makeText(getApplicationContext(), "Champ sexe vide", Toast.LENGTH_SHORT).show();
        }else if (date.equals("vide")){
            Toast.makeText(getApplicationContext(), "Champ date de naissance vide", Toast.LENGTH_SHORT).show();
        }else{
            if (femmRadio.isChecked()){
                sexeSelect="femme";
            }else if (hommRadio.isChecked()){
                sexeSelect="homme";
            }
            ResidentClasse nvInf = new ResidentClasse(idRes,nom,prenom,sexeSelect,typeSang,date);

             FirebaseDatabase database = FirebaseDatabase.getInstance();
             DatabaseReference myRef = database.getReference("Resident").push();

            myRef.setValue(nvInf).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(CreerProfilResident.this, "Profil ajouter a la base.", Toast.LENGTH_SHORT).show();
                    updateIdResident();
                    finish();
                }
            });
        }
    }

    private void getIdResident() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ID").child("IDResident");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idRes=dataSnapshot.getValue(Integer.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Error", databaseError.toException());
            }
        });
    }

    private  void updateIdResident(){
        //get l'id
        getIdResident();
        //ajout 1 a l'id
        idRes++;
        //update l'id
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idUpdateRef = database.getReference();
        idUpdateRef.child("ID").child("IDResident").setValue(idRes);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.TxtDateNais){
            showDatePickerDailog();
        }
        if(view.getId()==R.id.creerRes){
            creeProfilResident();
        }
        if(view.getId()==R.id.retourFrCreerRes){
            finish();
        }
    }

}
