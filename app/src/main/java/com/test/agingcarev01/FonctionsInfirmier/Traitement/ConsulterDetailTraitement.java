package com.test.agingcarev01.FonctionsInfirmier.Traitement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.agingcarev01.R;

public class ConsulterDetailTraitement extends AppCompatActivity implements View.OnClickListener {

    private String nom, type, unite, dose, dateDebut,
            DateFin, repetition, temp1, temp2, temp3,
            temp4, temp5, temp6;

    private TextView nomResDetailTraitement, TypeResDetailTraitement, doseResDetailTraitement,
            uniteResDetailTraitement, dateDebutResDetailTraitement, dateFinResDetailTraitement,
            repetitionResDetailTraitement, tempResDetailTraitement1, tempResDetailTraitement2,
            tempResDetailTraitement3, tempResDetailTraitement4, tempResDetailTraitement5, tempResDetailTraitement6;
    private Button retourFrDetailTraitement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_detail_traitement);

        nomResDetailTraitement = findViewById(R.id.nomResDetailTraitement);
        TypeResDetailTraitement = findViewById(R.id.TypeResDetailTraitement);
        doseResDetailTraitement = findViewById(R.id.doseResDetailTraitement);
        uniteResDetailTraitement = findViewById(R.id.uniteResDetailTraitement);
        dateDebutResDetailTraitement = findViewById(R.id.dateDebutResDetailTraitement);
        dateFinResDetailTraitement = findViewById(R.id.dateFinResDetailTraitement);
        repetitionResDetailTraitement = findViewById(R.id.repetitionResDetailTraitement);
        tempResDetailTraitement1 = findViewById(R.id.tempResDetailTraitement1);
        tempResDetailTraitement2 = findViewById(R.id.tempResDetailTraitement2);
        tempResDetailTraitement3 = findViewById(R.id.tempResDetailTraitement3);
        tempResDetailTraitement4 = findViewById(R.id.tempResDetailTraitement4);
        tempResDetailTraitement5 = findViewById(R.id.tempResDetailTraitement5);
        tempResDetailTraitement6 = findViewById(R.id.tempResDetailTraitement6);
        retourFrDetailTraitement = findViewById(R.id.retourFrDetailTraitement);
        retourFrDetailTraitement.setOnClickListener(this);

        //fetch
        nom = getIntent().getStringExtra("nom");
        type = getIntent().getStringExtra("type");
        unite = getIntent().getStringExtra("unite");
        dose = getIntent().getStringExtra("dose");
        dateDebut = getIntent().getStringExtra("dateDebut");
        DateFin = getIntent().getStringExtra("DateFin");
        repetition = getIntent().getStringExtra("repetition");
        temp1 = getIntent().getStringExtra("temp1");
        temp2 = getIntent().getStringExtra("temp2");
        temp3 = getIntent().getStringExtra("temp3");
        temp4 = getIntent().getStringExtra("temp4");
        temp5 = getIntent().getStringExtra("temp5");
        temp6 = getIntent().getStringExtra("temp6");

        nomResDetailTraitement.setText(nom);
        TypeResDetailTraitement.setText(type);
        doseResDetailTraitement.setText(dose);
        uniteResDetailTraitement.setText(unite);
        dateDebutResDetailTraitement.setText(dateDebut);
        dateFinResDetailTraitement.setText(DateFin);
        repetitionResDetailTraitement.setText(repetition);
        tempResDetailTraitement1.setText(temp1);
        tempResDetailTraitement2.setText(temp2);
        tempResDetailTraitement3.setText(temp3);
        tempResDetailTraitement4.setText(temp4);
        tempResDetailTraitement5.setText(temp5);
        tempResDetailTraitement6.setText(temp6);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrDetailTraitement) {
            finish();
        }
    }
}