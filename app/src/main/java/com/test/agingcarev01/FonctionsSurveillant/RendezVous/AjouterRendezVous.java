package com.test.agingcarev01.FonctionsSurveillant.RendezVous;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.RendezVousClasse;
import com.test.agingcarev01.R;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AjouterRendezVous extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private EditText nomRDV, lieuRDV, detailRDV, numTelRDV;
    private TextView dateRDV, tempRDV;
    private Button ajouterRDV, retourFrRDV;
    private String keyResident, date = "", temp = "";
    private int idRDV, idRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_rendez_vous);

        nomRDV = findViewById(R.id.nomRDV);
        lieuRDV = findViewById(R.id.lieuRDV);
        detailRDV = findViewById(R.id.detailRDV);
        numTelRDV = findViewById(R.id.numTelRDV);

        dateRDV = findViewById(R.id.dateRDV);
        dateRDV.setOnClickListener(this);
        tempRDV = findViewById(R.id.tempRDV);
        tempRDV.setOnClickListener(this);
        ajouterRDV = findViewById(R.id.ajouterRDV);
        ajouterRDV.setOnClickListener(this);
        retourFrRDV = findViewById(R.id.retourFrRDV);
        retourFrRDV.setOnClickListener(this);

        //fetch
        keyResident = getIntent().getStringExtra("key");

        getIdRendezVous();
        getIdResident();

    }

    private void getIdResident() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Resident").child(keyResident).child("id");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idRes = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Error", databaseError.toException());
            }
        });
    }

    private void getIdRendezVous() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ID").child("IDRendezVous");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idRDV = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Error", databaseError.toException());
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //janvier = month 0
        month++;
        String monthString = "" + month;
        String dayString = "" + day;

        if (month < 10) {
            monthString = "0" + month;
        }
        if (day < 10) {
            dayString = "0" + day;
        }
        date = year + "/" + monthString + "/" + dayString;
        dateRDV.setText(date);

    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AjouterRendezVous.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        if (minute < 10) {
            temp = hourOfDay + ":0" + minute;
            tempRDV.setText(temp);
        } else {
            temp = hourOfDay + ":" + minute;
            tempRDV.setText(temp);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ajouterRDV) {
            AjouterRDV();
        }
        if (view.getId() == R.id.retourFrRDV) {
            finish();
        }
        if (view.getId() == R.id.dateRDV) {
            showDatePickerDailog();
        }
        if (view.getId() == R.id.tempRDV) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "Time Picker");
        }

    }

    private void AjouterRDV() {
        String lieu = lieuRDV.getText().toString();
        if (lieu.isEmpty()) {
            lieu = "(non précisé)";
        }

        String num = numTelRDV.getText().toString();
        if (num.isEmpty()) {
            num = "(non précisé)";
        }
        String detail = detailRDV.getText().toString();
        if (detail.isEmpty()) {
            detail = "(non précisé)";
        }
        String nom = nomRDV.getText().toString();
        if (nom.isEmpty()) {
            nom = "(non précisé)";
        }

        if (nom.isEmpty()) {
            Toasty.warning(AjouterRendezVous.this, "Champ Nom Vide", Toast.LENGTH_SHORT).show();
            nomRDV.requestFocus();
        } else if (date.isEmpty()) {
            Toasty.warning(AjouterRendezVous.this, "Champ Date Vide", Toast.LENGTH_SHORT).show();
        } else if (temp.isEmpty()) {
            Toasty.warning(AjouterRendezVous.this, "Champ Temp Vide", Toast.LENGTH_SHORT).show();
        } else {
            //Hide virtual keyboard on button press
            InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            updateIdRDV();
            RendezVousClasse rendezVousClasse = new RendezVousClasse(idRDV, date, temp, lieu, nom, detail, num, idRes);
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("rendezVous").push();
            myRef.setValue(rendezVousClasse);
            DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("RendezVous").push();
            myRef2.setValue(rendezVousClasse);
            finish();
        }
    }

    private void updateIdRDV() {
        //ajout 1 a l'id
        idRDV++;
        //update l'id
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idUpdateRef = database.getReference();
        idUpdateRef.child("ID").child("IDRendezVous").setValue(idRDV);
    }


}