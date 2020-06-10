package com.test.agingcarev01.FonctionsSurveillant.Poids;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.agingcarev01.Classe.PoidsClasse;
import com.test.agingcarev01.R;

import java.util.Calendar;

public class AjouterPoidsResident extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView poidsRes, notePoidsRes, datePoidsTxt;
    private Button ajouterPoidsBT, retourFrPoidsResBT;

    private String poidsChk = "";
    private String keyResident, date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_poids_resident);


        poidsRes = findViewById(R.id.poidsRes);
        notePoidsRes = findViewById(R.id.notePoidsRes);

        datePoidsTxt = findViewById(R.id.datePoidsTxt);
        datePoidsTxt.setOnClickListener(this);
        ajouterPoidsBT = findViewById(R.id.ajouterPoids);
        ajouterPoidsBT.setOnClickListener(this);
        retourFrPoidsResBT = findViewById(R.id.retourFrPoidsRes);
        retourFrPoidsResBT.setOnClickListener(this);

        //fetch Poids
        keyResident = getIntent().getStringExtra("key");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ajouterPoids) {
            AjouterPoids();
        }
        if (view.getId() == R.id.retourFrPoidsRes) {
            finish();
        }
        if (view.getId() == R.id.datePoidsTxt) {
            showDatePickerDailog();
        }
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
        datePoidsTxt.setText(date);

    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AjouterPoidsResident.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void AjouterPoids() {
        poidsChk = poidsRes.getText().toString();
        String note = notePoidsRes.getText().toString();
        if (note.isEmpty()) {
            note = "(non précisé)";
        }
        if (poidsChk.isEmpty()) {
            Toast.makeText(AjouterPoidsResident.this, "Champ Poids Vide", Toast.LENGTH_SHORT).show();
            poidsRes.requestFocus();
        } else if (date.isEmpty()) {
            Toast.makeText(AjouterPoidsResident.this, "Champ Date Vide", Toast.LENGTH_SHORT).show();
        } else {
            Float poids = Float.valueOf(poidsRes.getText().toString());
            if (poids <= 0) {
                Toast.makeText(AjouterPoidsResident.this, "Poids doit être supérieur à 0", Toast.LENGTH_SHORT).show();
                poidsRes.requestFocus();
            } else if (poids > 0) {
                PoidsClasse poidsClasse = new PoidsClasse(poids, date, note);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poids").push();
                myRef.setValue(poidsClasse);
                finish();
            }
        }
    }


}
