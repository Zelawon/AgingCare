package com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.agingcarev01.Classe.TauxGlycemiqueClasse;
import com.test.agingcarev01.R;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AjouterTauxGlycemie extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private TextView tauxGlyceRes, noteTauxGlyce, dateTauxGlyce;
    private Button ajouterTauxGlyce, retourFrTauxGlycemique;
    private String keyResident, date = "", tauxChk = "";
    private String mesure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_taux_glycemie);

        tauxGlyceRes = findViewById(R.id.tauxGlyceRes);
        noteTauxGlyce = findViewById(R.id.noteTauxGlyce);

        dateTauxGlyce = findViewById(R.id.dateTauxGlyce);
        dateTauxGlyce.setOnClickListener(this);
        ajouterTauxGlyce = findViewById(R.id.ajouterTauxGlyce);
        ajouterTauxGlyce.setOnClickListener(this);
        retourFrTauxGlycemique = findViewById(R.id.retourFrTauxGlycemique);
        retourFrTauxGlycemique.setOnClickListener(this);

        //fetch
        keyResident = getIntent().getStringExtra("key");

        Spinner spinnerSelectGrephe = (Spinner) findViewById(R.id.spinnerMesureTauxGlyce);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.mesureTauxGlycemiqueArray,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerSelectGrephe.setAdapter(staticAdapter);
        spinnerSelectGrephe.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ajouterTauxGlyce) {
            AjouterTauxGlycemique();
        }
        if (view.getId() == R.id.retourFrTauxGlycemique) {
            finish();
        }
        if (view.getId() == R.id.dateTauxGlyce) {
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
        dateTauxGlyce.setText(date);

    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AjouterTauxGlycemie.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void AjouterTauxGlycemique() {
        tauxChk = tauxGlyceRes.getText().toString();
        if (tauxChk.isEmpty()) {
            Toasty.warning(AjouterTauxGlycemie.this, "Champ Taux Glycemique Vide", Toast.LENGTH_SHORT).show();
            tauxGlyceRes.requestFocus();
        } else if (date.isEmpty()) {
            Toasty.warning(AjouterTauxGlycemie.this, "Champ Date Vide", Toast.LENGTH_SHORT).show();
        } else {
            Float taux = Float.valueOf(tauxGlyceRes.getText().toString());
            if (taux <= 0) {
                Toasty.warning(AjouterTauxGlycemie.this, "Taux Glycemique doit être supérieur à 0", Toast.LENGTH_SHORT).show();
                tauxGlyceRes.requestFocus();
            } else if (taux > 0) {
                if (noteTauxGlyce.getText().toString().isEmpty()) {
                    TauxGlycemiqueClasse tauxGlycemiqueClasse = new TauxGlycemiqueClasse(taux, date, "(non précisé)", mesure);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemique").push();
                    myRef.setValue(tauxGlycemiqueClasse);
                } else {
                    //Hide virtual keyboard on button press
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    TauxGlycemiqueClasse tauxGlycemiqueClasse = new TauxGlycemiqueClasse(taux, date, noteTauxGlyce.getText().toString(), mesure);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemique").push();
                    myRef.setValue(tauxGlycemiqueClasse);
                }
                finish();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                mesure = "Avant le petit déjeuner";
                break;
            case 1:
                mesure = "Après le petit déjeuner";
                break;
            case 2:
                mesure = "Avant le déjeuner";
                break;
            case 3:
                mesure = "Après le déjeuner";
                break;
            case 4:
                mesure = "Avant le dîner";
                break;
            case 5:
                mesure = "Après le dîner";
                break;
            case 6:
                mesure = "Avant de dormir";
                break;
            case 7:
                mesure = "Après le sommeil";
                break;
            case 8:
                mesure = "Jeûne";
                break;
            case 9:
                mesure = "Autre";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
