package com.test.agingcarev01.ConsulterListes.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.SurveillantClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.SurveillantListAdapter;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListeSurveillant extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecycler;
    DatabaseReference databaseReference;
    List<SurveillantClasse> survlist;
    Button retourFrConsulSurvBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_surveillant);

        retourFrConsulSurvBT=findViewById(R.id.retourFrConsulDirec);
        retourFrConsulSurvBT.setOnClickListener(this);

        mRecycler = findViewById(R.id.recyclerViewListeDirecteur);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                survlist =new ArrayList<SurveillantClasse>();
                for (DataSnapshot survListSnap: dataSnapshot.getChildren()) {
                    String currentRole = survListSnap.child("role").getValue(String.class);
                    if (currentRole.equals("Surveillant")) {
                        SurveillantClasse post = survListSnap.getValue(SurveillantClasse.class);
                        survlist.add(post) ;
                    }
                }
                SurveillantListAdapter adapter = new SurveillantListAdapter(ConsulterListeSurveillant.this,survlist);
                mRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrConsulDirec){
            finish();
        }
    }
}
