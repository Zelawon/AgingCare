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
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.ResidentListAdapter;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListeResident extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecycler;
    DatabaseReference databaseReference;
    List<ResidentClasse> resList;
    Button retourFrConsulResBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_resident);

        retourFrConsulResBT=findViewById(R.id.retourFrConsulResident);
        retourFrConsulResBT.setOnClickListener(this);
        mRecycler = findViewById(R.id.recyclerViewListeResident);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Resident");
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                resList =new ArrayList<ResidentClasse>();
                for (DataSnapshot resListSnap: dataSnapshot.getChildren()) {
                    ResidentClasse post = resListSnap.getValue(ResidentClasse.class);
                    resList.add(post) ;
                }
                ResidentListAdapter adapter = new ResidentListAdapter(ConsulterListeResident.this, resList);
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
        if(view.getId()==R.id.retourFrConsulResident){
            finish();
        }
    }
}
