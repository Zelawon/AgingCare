package com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.test.agingcarev01.Classe.TauxGlycemiqueClasse;
import com.test.agingcarev01.R;

public class ConsulterListeTauxGlycemique extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulTauxGlycemique,ajouterNvTauxGlycemique;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<TauxGlycemiqueClasse> options;
    private FirebaseRecyclerAdapter<TauxGlycemiqueClasse, TauxGlycemiqueListViewHolder> adapter;
    private String keyResident,keyTauxGlyceDel;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_taux_glycemique);

        retourFrConsulTauxGlycemique=findViewById(R.id.retourFrConsulTauxGlycemique);
        retourFrConsulTauxGlycemique.setOnClickListener(this);
        ajouterNvTauxGlycemique=findViewById(R.id.ajouterNvTauxGlycemique);
        ajouterNvTauxGlycemique.setOnClickListener(this);

        //fetch
        keyResident =getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemique");
        Query query = databaseReference.orderByChild("dateTauxGlyceRes");

        //recyclerView
        recyclerView=findViewById(R.id.recyclerViewListeTauxGlycemique);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<TauxGlycemiqueClasse>().setQuery(query, TauxGlycemiqueClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<TauxGlycemiqueClasse, TauxGlycemiqueListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TauxGlycemiqueListViewHolder holder, int position, @NonNull TauxGlycemiqueClasse model) {
                final String key = getRef(position).getKey();

                holder.tauxItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyTauxGlyceDel=key;
                        DeleteTauxGlycemique(keyTauxGlyceDel);
                    }
                });
                holder.tauxGlyceItemDate.setText(model.getDateTauxGlyceRes());
                holder.tauxGlycemiqueItem.setText(String.valueOf(model.getTauxGlyceRes()));
                holder.meureItem.setText(model.getMesureTauxGlyceRes());
                holder.tauxItemNote.setText(model.getNoteTauxGlyceRes());
            }

            @NonNull
            @Override
            public TauxGlycemiqueListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_taux_glycemique_resident,parent,false);
                return new TauxGlycemiqueListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void DeleteTauxGlycemique(final String keyTauxGlyceDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeTauxGlycemique.this);
        builder.setTitle("Supprimer Le Taux Glycemique");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce taux glycemique de ce resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("tauxGlycemique").child(keyTauxGlyceDel);
                delRef.removeValue();
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
        if (view.getId() == R.id.ajouterNvTauxGlycemique){
            Intent intent=new Intent(ConsulterListeTauxGlycemique.this, AjouterTauxGlycemie.class);
            intent.putExtra("key", keyResident);
            startActivity(intent);
        }
        if (view.getId() == R.id.retourFrConsulTauxGlycemique){
            finish();
        }

    }
}
