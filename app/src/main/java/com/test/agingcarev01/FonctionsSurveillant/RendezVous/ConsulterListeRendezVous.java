package com.test.agingcarev01.FonctionsSurveillant.RendezVous;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.RendezVousClasse;
import com.test.agingcarev01.R;

public class ConsulterListeRendezVous extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ConsulterListeRendezVou";
    private Button retourFrConsulRDVBT, ajouterNvRDV;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<RendezVousClasse> options;
    private FirebaseRecyclerAdapter<RendezVousClasse, RendezVousListViewHolder> adapter;
    private String keyResident, keyRDVDel;
    private int idRdv;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_rendez_vous);

        retourFrConsulRDVBT = findViewById(R.id.retourFrConsulRDV);
        retourFrConsulRDVBT.setOnClickListener(this);
        ajouterNvRDV = findViewById(R.id.ajouterNvRDV);
        ajouterNvRDV.setOnClickListener(this);

        //fetch
        keyResident = getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("rendezVous");
        Query query = databaseReference.orderByChild("dateRDV");

        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeRDV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<RendezVousClasse>().setQuery(query, RendezVousClasse.class).build();
        adapter = new FirebaseRecyclerAdapter<RendezVousClasse, RendezVousListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RendezVousListViewHolder holder, int position, @NonNull RendezVousClasse model) {
                final String key = getRef(position).getKey();
                idRdv = model.getIdRDV();
                holder.RDVItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyRDVDel = key;
                        DeleteRDV(keyRDVDel);
                    }
                });
                holder.dateRDVItem.setText(model.getDateRDV());
                holder.tempRDVItem.setText(String.valueOf(model.getTimeRDV()));
                holder.nomRDItem.setText(model.getNomRDV());
                holder.lieuRDVItem.setText(model.getLieuRDV());
                holder.detailRDVItem.setText(model.getNotesRDV());
                holder.numeroRDVItem.setText(model.getNumTelRDV());
            }

            @NonNull
            @Override
            public RendezVousListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rendez_vous_resident, parent, false);
                return new RendezVousListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void DeleteRDV(final String keyRDVDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeRendezVous.this);
        builder.setTitle("Supprimer Le Rendez-vous");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce rendez-vous ?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("rendezVous").child(keyRDVDel);
                delRef.removeValue();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("RendezVous");
                Query query = reference.orderByChild("idRDV").equalTo(idRdv);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                            Log.e(TAG, "onDataChange: " + postsnap.getKey());
                            DeleteFromRDV(postsnap.getKey());
                        }
                    }

                    private void DeleteFromRDV(String key) {
                        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("RendezVous").child(key);
                        myRef2.removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ajouterNvRDV) {
            Intent intent = new Intent(ConsulterListeRendezVous.this, AjouterRendezVous.class);
            intent.putExtra("key", keyResident);
            startActivity(intent);
        }
        if (view.getId() == R.id.retourFrConsulRDV) {
            finish();
        }

    }
}
