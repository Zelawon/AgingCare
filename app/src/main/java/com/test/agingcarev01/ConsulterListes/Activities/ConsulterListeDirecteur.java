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
import com.test.agingcarev01.Classe.DirecteurClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.DirecteurListAdapter;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListeDirecteur extends AppCompatActivity implements View.OnClickListener{

    RecyclerView mRecycler;
    DatabaseReference databaseReference;
    List<DirecteurClasse> direcList;
    Button retourFrConsulDirecBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_directeur);

        retourFrConsulDirecBT =findViewById(R.id.retourFrConsulDirec);
        retourFrConsulDirecBT.setOnClickListener(this);

        mRecycler = findViewById(R.id.recyclerViewListeDirecteur);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                direcList =new ArrayList<DirecteurClasse>();
                for (DataSnapshot direcListSnap: dataSnapshot.getChildren()) {
                    String currentRole = direcListSnap.child("role").getValue(String.class);
                    if (currentRole.equals("Directeur")) {
                        DirecteurClasse post = direcListSnap.getValue(DirecteurClasse.class);
                        direcList.add(post) ;
                    }
                }
                DirecteurListAdapter adapter = new DirecteurListAdapter(ConsulterListeDirecteur.this, direcList);
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
