package com.test.agingcarev01.FonctionsCommunes.ConsulterListeX;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Adapters.InfirmierListAdapter;
import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListeInfirmier extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecycler;
    DatabaseReference databaseReference;
    List<InfirmierClasse> infList;
    Button retourFrConsulInfBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_infirmier);

        retourFrConsulInfBT=findViewById(R.id.retourFrConsulInf);
        retourFrConsulInfBT.setOnClickListener(this);

        mRecycler = findViewById(R.id.recyclerViewListeInfirmier);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                infList =new ArrayList<InfirmierClasse>();
                for (DataSnapshot infListSnap: dataSnapshot.getChildren()) {
                    String currentRole = infListSnap.child("role").getValue(String.class);
                    if (currentRole.equals("Infirmier")) {
                        InfirmierClasse post = infListSnap.getValue(InfirmierClasse.class);
                        infList.add(post) ;
                    }
                }
                InfirmierListAdapter adapter = new InfirmierListAdapter(ConsulterListeInfirmier.this,infList);
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
        if(view.getId()==R.id.retourFrConsulInf){
            finish();
        }
    }
}
