package com.test.agingcarev01.FonctionsSurveillant.Traitement;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.TraitementClasse;
import com.test.agingcarev01.R;
import com.touchboarder.weekdaysbuttons.WeekdaysDataItem;
import com.touchboarder.weekdaysbuttons.WeekdaysDataSource;
import com.touchboarder.weekdaysbuttons.WeekdaysDrawableProvider;

import java.util.ArrayList;
import java.util.Calendar;

public class AjouterTraitement extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, WeekdaysDataSource.Callback, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "AjouterTraitement";

    private EditText nomTraitement, doseTraitement;
    private TextView textTempTaitement1, textTempTaitement2, textTempTaitement3,
            textTempTaitement4, textTempTaitement5, textTempTaitement6,
            tempTaitement1, tempTaitement2, tempTaitement3, tempTaitement4,
            tempTaitement5, tempTaitement6, dateDebutTraitement, dateFinTraitement,
            textViewRepetitionTrait, uniteTraitement;
    private Button retourFrAjoutTraitement, ajouterTraitementResident;
    private RecyclerView weekdays_sample_2;

    private WeekdaysDataSource weekdaysDataSource2;
    private int nbrTemp, d1, d2, idTrait, idRes;
    private String days = "", typeTraitement, repetitionTraitement, temp1 = "", temp2 = "", unite="",
            temp3 = "", temp4 = "", temp5 = "", temp6 = "", repetitionMode, dateDebut, dateFin, keyResident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_traitement);

        setupWeekdaysButtons();

        //Type Traitement Spinner
        Spinner spinnerTypeTraitement = (Spinner) findViewById(R.id.spinnerTypeTraitement);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.TypeTraitement, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeTraitement.setAdapter(typeAdapter);
        spinnerTypeTraitement.setOnItemSelectedListener(this);

        //repetition Traitement Spinner
        Spinner spinnerRepetitionTraitement = (Spinner) findViewById(R.id.spinnerRepetitionTraitement);
        ArrayAdapter<CharSequence> repetitionAdapter = ArrayAdapter.createFromResource(this, R.array.RepetitionTraitement, android.R.layout.simple_spinner_item);
        repetitionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRepetitionTraitement.setAdapter(repetitionAdapter);
        spinnerRepetitionTraitement.setOnItemSelectedListener(this);

        //temp Traitement Spinner
        Spinner spinnerTempTraitement = (Spinner) findViewById(R.id.spinnerRepetitionTemp);
        ArrayAdapter<CharSequence> tempAdapter = ArrayAdapter.createFromResource(this, R.array.NbrRepetitionJour, android.R.layout.simple_spinner_item);
        tempAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempTraitement.setAdapter(tempAdapter);
        spinnerTempTraitement.setOnItemSelectedListener(this);

        ajouterTraitementResident = findViewById(R.id.ajouterTraitementResident);
        ajouterTraitementResident.setOnClickListener(this);
        retourFrAjoutTraitement = findViewById(R.id.retourFrAjoutTraitement);
        retourFrAjoutTraitement.setOnClickListener(this);

        nomTraitement = findViewById(R.id.nomTraitement);
        doseTraitement = findViewById(R.id.doseTraitement);
        weekdays_sample_2 = findViewById(R.id.weekdays_sample_2);
        textViewRepetitionTrait = findViewById(R.id.textViewRepetitionTrait);
        uniteTraitement=findViewById(R.id.uniteTraitement);

        textTempTaitement1 = findViewById(R.id.textTempTaitement1);
        textTempTaitement2 = findViewById(R.id.textTempTaitement2);
        textTempTaitement3 = findViewById(R.id.textTempTaitement3);
        textTempTaitement4 = findViewById(R.id.textTempTaitement4);
        textTempTaitement5 = findViewById(R.id.textTempTaitement5);
        textTempTaitement6 = findViewById(R.id.textTempTaitement6);

        tempTaitement1 = findViewById(R.id.tempTaitement1);
        tempTaitement2 = findViewById(R.id.tempTaitement2);
        tempTaitement3 = findViewById(R.id.tempTaitement3);
        tempTaitement4 = findViewById(R.id.tempTaitement4);
        tempTaitement5 = findViewById(R.id.tempTaitement5);
        tempTaitement6 = findViewById(R.id.tempTaitement6);
        tempTaitement1.setOnClickListener(this);
        tempTaitement2.setOnClickListener(this);
        tempTaitement3.setOnClickListener(this);
        tempTaitement4.setOnClickListener(this);
        tempTaitement5.setOnClickListener(this);
        tempTaitement6.setOnClickListener(this);

        dateDebutTraitement = findViewById(R.id.dateDebutTraitement);
        dateDebutTraitement.setOnClickListener(this);
        dateFinTraitement = findViewById(R.id.dateFinTraitement);
        dateFinTraitement.setOnClickListener(this);

        keyResident = getIntent().getStringExtra("key");

        getIdResident();
        getIdTraitement();
        HideTime();
        HideRepetition();

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

    private void getIdTraitement() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("ID").child("IDTraitement");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                idTrait = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "Error", databaseError.toException());
            }
        });
    }

    private void HideRepetition() {
        weekdays_sample_2.setVisibility(View.GONE);
        textViewRepetitionTrait.setVisibility(View.GONE);
    }

    private void HideTime() {
        textTempTaitement2.setVisibility(View.GONE);
        textTempTaitement3.setVisibility(View.GONE);
        textTempTaitement4.setVisibility(View.GONE);
        textTempTaitement5.setVisibility(View.GONE);
        textTempTaitement6.setVisibility(View.GONE);

        tempTaitement2.setVisibility(View.GONE);
        tempTaitement3.setVisibility(View.GONE);
        tempTaitement4.setVisibility(View.GONE);
        tempTaitement5.setVisibility(View.GONE);
        tempTaitement6.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.spinnerTypeTraitement:
                switch (position) {
                    case 0:
                        typeTraitement = "Comprimé";
                        uniteTraitement.setText(unite);
                        break;
                    case 1:
                        typeTraitement = "Ampoules";
                        uniteTraitement.setText(unite);
                        break;
                    case 2:
                        typeTraitement = "Capsule";
                        uniteTraitement.setText(unite);
                        break;
                    case 3:
                        typeTraitement = "Poudre";
                        unite = "g";
                        uniteTraitement.setText(unite);
                        break;
                    case 4:
                        typeTraitement = "Sirop";
                        unite = "mL";
                        uniteTraitement.setText(unite);
                        break;
                    case 5:
                        typeTraitement = "Pommade";
                        uniteTraitement.setText(unite);
                        break;
                    case 6:
                        typeTraitement = "Seringue";
                        unite = "mL";
                        uniteTraitement.setText(unite);
                        break;
                    case 7:
                        typeTraitement = "Sérum";
                        unite = "mL";
                        break;
                    case 8:
                        typeTraitement = "Suppositoire";
                        uniteTraitement.setText(unite);
                        break;
                    case 9:
                        typeTraitement = "Autre";
                        uniteTraitement.setText(unite);
                        break;
                }
                break;
            case R.id.spinnerRepetitionTraitement:

                switch (position) {
                    case 0:
                        repetitionTraitement = "Tous les jours";
                        repetitionMode = "tousJour";
                        days = "Tous Les Jour";
                        HideRepetition();
                        break;
                    case 1:
                        repetitionTraitement = "Customiser jours";
                        repetitionMode = "custom";
                        weekdays_sample_2.setVisibility(View.VISIBLE);
                        textViewRepetitionTrait.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case R.id.spinnerRepetitionTemp:
                switch (position) {
                    case 0:
                        nbrTemp = 1;
                        HideTime();
                        break;
                    case 1:
                        nbrTemp = 2;
                        HideTime();
                        textTempTaitement1.setVisibility(View.VISIBLE);
                        tempTaitement1.setVisibility(View.VISIBLE);
                        textTempTaitement2.setVisibility(View.VISIBLE);
                        tempTaitement2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        nbrTemp = 3;
                        HideTime();
                        textTempTaitement1.setVisibility(View.VISIBLE);
                        tempTaitement1.setVisibility(View.VISIBLE);
                        textTempTaitement2.setVisibility(View.VISIBLE);
                        tempTaitement2.setVisibility(View.VISIBLE);
                        textTempTaitement3.setVisibility(View.VISIBLE);
                        tempTaitement3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        nbrTemp = 4;
                        HideTime();
                        textTempTaitement1.setVisibility(View.VISIBLE);
                        tempTaitement1.setVisibility(View.VISIBLE);
                        textTempTaitement2.setVisibility(View.VISIBLE);
                        tempTaitement2.setVisibility(View.VISIBLE);
                        textTempTaitement3.setVisibility(View.VISIBLE);
                        tempTaitement3.setVisibility(View.VISIBLE);
                        textTempTaitement4.setVisibility(View.VISIBLE);
                        tempTaitement4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        nbrTemp = 5;
                        HideTime();
                        textTempTaitement1.setVisibility(View.VISIBLE);
                        tempTaitement1.setVisibility(View.VISIBLE);
                        textTempTaitement2.setVisibility(View.VISIBLE);
                        tempTaitement2.setVisibility(View.VISIBLE);
                        textTempTaitement3.setVisibility(View.VISIBLE);
                        tempTaitement3.setVisibility(View.VISIBLE);
                        textTempTaitement4.setVisibility(View.VISIBLE);
                        tempTaitement4.setVisibility(View.VISIBLE);
                        textTempTaitement5.setVisibility(View.VISIBLE);
                        tempTaitement5.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        nbrTemp = 6;
                        HideTime();
                        textTempTaitement1.setVisibility(View.VISIBLE);
                        tempTaitement1.setVisibility(View.VISIBLE);
                        textTempTaitement2.setVisibility(View.VISIBLE);
                        tempTaitement2.setVisibility(View.VISIBLE);
                        textTempTaitement3.setVisibility(View.VISIBLE);
                        tempTaitement3.setVisibility(View.VISIBLE);
                        textTempTaitement4.setVisibility(View.VISIBLE);
                        tempTaitement4.setVisibility(View.VISIBLE);
                        textTempTaitement5.setVisibility(View.VISIBLE);
                        tempTaitement5.setVisibility(View.VISIBLE);
                        textTempTaitement6.setVisibility(View.VISIBLE);
                        tempTaitement6.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void setupWeekdaysButtons() {
        weekdaysDataSource2 = new WeekdaysDataSource(this, R.id.weekdays_sample_2)
                .setDrawableType(WeekdaysDrawableProvider.MW_ROUND_RECT)
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setFontBaseSize(16)
                .setFontTypeFace(Typeface.MONOSPACE)
                .setUnselectedColorRes(R.color.colorPrimaryLight)
                .setTextColorUnselectedRes(R.color.colorSecondaryText)
                .setNumberOfLetters(3)
                .setFillWidth(true)
                .start(this);
    }

    @Override
    public void onWeekdaysItemClicked(int attachId, WeekdaysDataItem item) {
    }

    @Override
    public void onWeekdaysSelected(int attachId, ArrayList<WeekdaysDataItem> items) {
        String selectedDays = getSelectedDaysFromWeekdaysData(items);
        days = selectedDays;
    }


    private String getSelectedDaysFromWeekdaysData(ArrayList<WeekdaysDataItem> items) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean selected = false;
        for (WeekdaysDataItem dataItem : items
        ) {
            if (dataItem.isSelected()) {
                selected = true;
                stringBuilder.append(dataItem.getLabel());
                stringBuilder.append(",");
            }
        }
        if (selected) {
            String result = stringBuilder.toString();
            return result.substring(0, result.lastIndexOf(","));
        } else return "No days selected";
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ajouterTraitementResident) {
            AjouterNvTraitement();
        }
        if (view.getId() == R.id.retourFrAjoutTraitement) {
            finish();
        }
        if (view.getId() == R.id.tempTaitement1) {
            TimePicker timePicker = new TimePicker(tempTaitement1, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.tempTaitement2) {
            TimePicker timePicker = new TimePicker(tempTaitement2, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.tempTaitement3) {
            TimePicker timePicker = new TimePicker(tempTaitement3, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.tempTaitement4) {
            TimePicker timePicker = new TimePicker(tempTaitement4, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.tempTaitement5) {
            TimePicker timePicker = new TimePicker(tempTaitement5, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.tempTaitement6) {
            TimePicker timePicker = new TimePicker(tempTaitement6, AjouterTraitement.this);
            timePicker.show(AjouterTraitement.this.getSupportFragmentManager(), "Start Time");
        }
        if (view.getId() == R.id.dateDebutTraitement) {
            d1 = 1;
            d2 = 2;
            showDatePickerDailog();
        }
        if (view.getId() == R.id.dateFinTraitement) {
            d1 = 2;
            d2 = 1;
            showDatePickerDailog();
        }
    }

    private void AjouterNvTraitement() {
        getTimeAndDate();
        String nom = nomTraitement.getText().toString();

        String doseChk = doseTraitement.getText().toString();

        if (nom.isEmpty()) {
            Toast.makeText(AjouterTraitement.this, "Champ Nom Vide", Toast.LENGTH_SHORT).show();
            nomTraitement.requestFocus();
        } else if (doseChk.isEmpty()) {
            Toast.makeText(AjouterTraitement.this, "Champ Dose Vide", Toast.LENGTH_SHORT).show();
            doseTraitement.requestFocus();
        } else {
            Float dose = Float.valueOf(doseTraitement.getText().toString());
            if (dose <= 0) {
                Toast.makeText(AjouterTraitement.this, "Dose doit être supérieur à 0", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 1 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 2 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 3 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 4 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 5 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp1.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 2 && temp2.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 3 && temp2.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 4 && temp2.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 5 && temp2.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp2.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 3 && temp3.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 4 && temp3.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 5 && temp3.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp3.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 4 && temp4.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 5 && temp4.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp4.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 5 && temp5.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp5.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (nbrTemp == 6 && temp6.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Temp", Toast.LENGTH_SHORT).show();
            } else if (dateDebut.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Date Debut", Toast.LENGTH_SHORT).show();
            } else if (dateFin.isEmpty()) {
                Toast.makeText(AjouterTraitement.this, "Erreur Date Fin", Toast.LENGTH_SHORT).show();
            } else if (repetitionMode.equals("custom") && (days.isEmpty() || days.equals("No days selected"))) {
                Toast.makeText(AjouterTraitement.this, "Erreur Jour Selection pour Repetition", Toast.LENGTH_SHORT).show();
            } else {
                updateIdTraitement();
                TraitementClasse traitementClasse = new TraitementClasse(idTrait, typeTraitement, nom, dose,unite,
                        repetitionMode, days, dateDebut, dateFin, nbrTemp,
                        temp1, temp2, temp3, temp4, temp5, temp6, idRes);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("traitement").push();
                myRef.setValue(traitementClasse);
                DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Traitement").push();
                myRef2.setValue(traitementClasse);
                finish();
            }
        }
    }

    private void updateIdTraitement() {
        //ajout 1 a l'id
        idTrait++;
        //update l'id
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference idUpdateRef = database.getReference();
        idUpdateRef.child("ID").child("IDTraitement").setValue(idTrait);
    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AjouterTraitement.this,
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
        String monthString = "" + month;
        String dayString = "" + day;

        if (month < 10) {
            monthString = "0" + month;
        }
        if (day < 10) {
            dayString = "0" + day;
        }
        String date = year + "/" + monthString + "/" + dayString;

        if (d1 == 1) {
            dateDebutTraitement.setText(date);
        }
        if (d2 == 1) {
            dateFinTraitement.setText(date);
        }
    }

    private void getTimeAndDate() {
        temp1 = (String) tempTaitement1.getText();
        temp2 = (String) tempTaitement2.getText();
        temp3 = (String) tempTaitement3.getText();
        temp4 = (String) tempTaitement4.getText();
        temp5 = (String) tempTaitement5.getText();
        temp6 = (String) tempTaitement6.getText();
        dateDebut = (String) dateDebutTraitement.getText();
        dateFin = (String) dateFinTraitement.getText();
        if (temp1.equals("HH:MM")) {
            temp1 = "";
        }
        if (temp2.equals("HH:MM")) {
            temp2 = "";
        }
        if (temp3.equals("HH:MM")) {
            temp3 = "";
        }
        if (temp4.equals("HH:MM")) {
            temp4 = "";
        }
        if (temp5.equals("HH:MM")) {
            temp5 = "";
        }
        if (temp6.equals("HH:MM")) {
            temp6 = "";
        }
        if (dateDebut.equals("AAAA/MM/JJ")) {
            dateDebut = "";
        }
        if (dateFin.equals("AAAA/MM/JJ")) {
            dateFin = "";
        }
    }
}