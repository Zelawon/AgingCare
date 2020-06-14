package com.test.agingcarev01.FonctionsSurveillant.Traitement;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.TraitementClasse;
import com.test.agingcarev01.R;

public class ConsulterListeTraitement extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ConsulterListeTraitemen";

    private Button retourFrConsulTraitement;
    private FloatingActionButton ajouterNvTraitement;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<TraitementClasse> options;
    private FirebaseRecyclerAdapter<TraitementClasse, TraitementListViewHolder> adapter;
    private String keyResident, keyTraitDel;
    private int idTrait;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_traitement);

        retourFrConsulTraitement = findViewById(R.id.retourFrConsulTraitement);
        retourFrConsulTraitement.setOnClickListener(this);
        ajouterNvTraitement = findViewById(R.id.ajouterNvTraitement);
        ajouterNvTraitement.setOnClickListener(this);

        //fetch
        keyResident = getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("traitement");
        Query query = databaseReference.orderByChild("dateDebutTaitement");

        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeTraitement);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<TraitementClasse>().setQuery(query, TraitementClasse.class).build();
        adapter = new FirebaseRecyclerAdapter<TraitementClasse, TraitementListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TraitementListViewHolder holder, int position, @NonNull TraitementClasse model) {
                final String key = getRef(position).getKey();
                idTrait = model.getId();
                holder.TraitementItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyTraitDel = key;
                        DeleteTraitment(keyTraitDel);
                    }
                });
                holder.dateDebutTraitementItem.setText(model.getDateDebutTaitement());
                holder.dateFinTraitementItem.setText(String.valueOf(model.getDateFinTaitement()));
                holder.nomTraitementItem.setText(model.getNom());
                holder.typeTraitementItem.setText(model.getType());
                holder.doseTraitementItem.setText(String.valueOf(model.getDose()));
                holder.uniteTraitementItem.setText(model.getUnite());
                holder.repetitionTraitementItem.setText(model.getJoursRepetition());
                holder.temp1TraitementItem.setText(model.getTemp1());
                holder.temp2TraitementItem.setText(model.getTemp2());
                holder.temp3TraitementItem.setText(model.getTemp3());
                holder.temp4TraitementItem.setText(model.getTemp4());
                holder.temp5TraitementItem.setText(model.getTemp5());
                holder.temp6TraitementItem.setText(model.getTemp6());
            }

            @NonNull
            @Override
            public TraitementListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_traitement_resident, parent, false);
                return new TraitementListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void DeleteTraitment(final String keyTraitDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeTraitement.this);
        builder.setTitle("Supprimer Le Traitement");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce traitement ?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("traitement").child(keyTraitDel);
                delRef.removeValue();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Traitement");
                Query query = reference.orderByChild("id").equalTo(idTrait);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                            Log.e(TAG, "onDataChange: " + postsnap.getKey());
                            DeleteFromRDV(postsnap.getKey());
                        }
                    }

                    private void DeleteFromRDV(String key) {
                        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Traitement").child(key);
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
        if (view.getId() == R.id.ajouterNvTraitement) {
            Intent intent = new Intent(ConsulterListeTraitement.this, AjouterTraitement.class);
            intent.putExtra("key", keyResident);
            startActivity(intent);
        }
        if (view.getId() == R.id.retourFrConsulTraitement) {
            finish();
        }

    }
}