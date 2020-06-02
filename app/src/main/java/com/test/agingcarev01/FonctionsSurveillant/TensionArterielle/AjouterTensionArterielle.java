package com.test.agingcarev01.FonctionsSurveillant.TensionArterielle;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
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
import com.test.agingcarev01.Classe.TensionArterielleClasse;
import com.test.agingcarev01.R;

import java.util.Calendar;

public class AjouterTensionArterielle extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener {

    private TextView pressSystolique,pressDiastolique,dateTensionArterielle,noteTensionArterielle;
    private Button ajouterTensionArterielle,retourFrTensionArterielle;
    private String keyResident,date="", systoliqueChk ="",diastoliqueChk ="",bras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_tension_arterielle);

        pressSystolique = findViewById(R.id.pressSystolique);
        pressDiastolique = findViewById(R.id.pressDiastolique);
        dateTensionArterielle = findViewById(R.id.dateTensionArterielle);
        noteTensionArterielle = findViewById(R.id.noteTensionArterielle);

        ajouterTensionArterielle = findViewById(R.id.ajouterTensionArterielle);
        ajouterTensionArterielle.setOnClickListener(this);
        retourFrTensionArterielle=findViewById(R.id.retourFrTensionArterielle);
        retourFrTensionArterielle.setOnClickListener(this);
        dateTensionArterielle = findViewById(R.id.dateTensionArterielle);
        dateTensionArterielle.setOnClickListener(this);

        //fetch
        keyResident = getIntent().getStringExtra("key");

        Spinner spinnerSelectGrephe = (Spinner) findViewById(R.id.spinnerBrasMesure);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.BrasMesure,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerSelectGrephe.setAdapter(staticAdapter);
        spinnerSelectGrephe.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ajouterTensionArterielle){
            AjouterTension();
        }
        if(view.getId()==R.id.retourFrTensionArterielle){
            finish();
        }
        if (view.getId()==R.id.dateTensionArterielle){
            showDatePickerDailog();
        }
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //janvier = month 0
        month++;
        String monthString = ""+month;
        String dayString = ""+day;

        if (month<10){
            monthString = "0"+month;
        }
        if (day<10){
            dayString = "0"+day;
        }
        date = year + "/" + monthString + "/" + dayString;
        dateTensionArterielle.setText(date);

    }

    private void showDatePickerDailog(){
        DatePickerDialog datePickerDialog =new DatePickerDialog(
                AjouterTensionArterielle.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void AjouterTension() {
        systoliqueChk = pressSystolique.getText().toString();
        diastoliqueChk = pressDiastolique.getText().toString();
        if (systoliqueChk.isEmpty()) {
            Toast.makeText(AjouterTensionArterielle.this, "Champ Pression Systolique Vide", Toast.LENGTH_SHORT).show();
            pressSystolique.requestFocus();
        }else if (date.isEmpty()) {
            Toast.makeText(AjouterTensionArterielle.this, "Champ Date Vide", Toast.LENGTH_SHORT).show();
        }else if (diastoliqueChk.isEmpty()) {
            Toast.makeText(AjouterTensionArterielle.this, "Champ Pression Diastolique Vide", Toast.LENGTH_SHORT).show();
            pressDiastolique.requestFocus();
        } else{
            Float pressSysto=Float.valueOf(pressSystolique.getText().toString());
            Float pressDiasto=Float.valueOf(pressDiastolique.getText().toString());
            if ( pressSysto<=0) {
                Toast.makeText(AjouterTensionArterielle.this, "Taux  Pression Systolique doit être supérieur à 0", Toast.LENGTH_SHORT).show();
                pressSystolique.requestFocus();
            }else if ( pressDiasto<=0) {
                Toast.makeText(AjouterTensionArterielle.this, "Taux  Pression Diastolique doit être supérieur à 0", Toast.LENGTH_SHORT).show();
                pressDiastolique.requestFocus();
            } else if ( (pressSysto>0)&&(pressDiasto>0) ) {
                if (noteTensionArterielle.getText().toString().isEmpty()){
                    TensionArterielleClasse tensionArterielleClasse = new TensionArterielleClasse(pressSysto,pressDiasto,bras, date, "(aucune notes)");
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tensionArterielle").push();
                    myRef.setValue(tensionArterielleClasse);
                }else {
                    TensionArterielleClasse tensionArterielleClasse = new TensionArterielleClasse(pressSysto,pressDiasto,bras, date, noteTensionArterielle.getText().toString());
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tensionArterielle").push();
                    myRef.setValue(tensionArterielleClasse);
                }
                finish();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                bras="Droite";break;
            case 1:
                bras="Gauche";break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
