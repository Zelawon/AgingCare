package com.test.agingcarev01.FonctionsInfirmier.RendezVous;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.R;

public class ConsulterDetailRendezVous extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ConsulterDetailRendezVo";
    private String date, temp, nom, lieu, detail, numtel, nomRes, prenomRes;
    private int idRes;

    private TextView nomResDetailRDV,prenomDetailRDV,dateDetailRDV,
            tempDetailRDV,nomDetailRDV,detailDetailRDV,lieuDetailRDV,numeroDetailRDV;
    private Button retourFrDetailRDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_detail_rendez_vous);

        nomResDetailRDV=findViewById(R.id.nomResDetailRDV);
        prenomDetailRDV=findViewById(R.id.prenomDetailRDV);
        dateDetailRDV=findViewById(R.id.dateDetailRDV);
        tempDetailRDV=findViewById(R.id.tempDetailRDV);
        nomDetailRDV=findViewById(R.id.nomDetailRDV);
        detailDetailRDV=findViewById(R.id.detailDetailRDV);
        lieuDetailRDV=findViewById(R.id.lieuDetailRDV);
        numeroDetailRDV=findViewById(R.id.numeroDetailRDV);
        retourFrDetailRDV=findViewById(R.id.retourFrDetailRDV);
        retourFrDetailRDV.setOnClickListener(this);

        //fetch
        idRes = Integer.valueOf(getIntent().getStringExtra("idRESIDENT"));
        date = getIntent().getStringExtra("date");
        temp = getIntent().getStringExtra("temp");
        nom = getIntent().getStringExtra("nom");
        lieu = getIntent().getStringExtra("lieu");
        detail = getIntent().getStringExtra("detail");
        numtel = getIntent().getStringExtra("numTel");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Resident");
        Query query = ref.orderByChild("id").equalTo(idRes);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    ResidentClasse residentClasse = tierSnapshot.getValue(ResidentClasse.class);
                    nomRes = residentClasse.getNom();
                    prenomRes = residentClasse.getPrenom();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                nomResDetailRDV.setText(nomRes);
                prenomDetailRDV.setText(prenomRes);
                dateDetailRDV.setText(date);
                tempDetailRDV.setText(temp);
                nomDetailRDV.setText(nom);
                detailDetailRDV.setText(detail);
                lieuDetailRDV.setText(lieu);
                numeroDetailRDV.setText(numtel);
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.retourFrDetailRDV){
            finish();
        }
    }
}