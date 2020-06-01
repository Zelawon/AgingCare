package com.test.agingcarev01.FonctionsSurveillant.Poids;

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
import com.test.agingcarev01.Classe.PoidsClasse;
import com.test.agingcarev01.R;

public class ConsulterListePoids extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulPoids,ajouterNvPoids;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<PoidsClasse> options;
    private FirebaseRecyclerAdapter<PoidsClasse, PoidsListViewHolder> adapter;
    private String keyResident,keyPoidsDel;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_poids);

        retourFrConsulPoids=findViewById(R.id.retourFrConsulPoids);
        retourFrConsulPoids.setOnClickListener(this);
        ajouterNvPoids=findViewById(R.id.ajouterNvPoids);
        ajouterNvPoids.setOnClickListener(this);

        //fetch Poids
        keyResident =getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poids");
        Query query = databaseReference.orderByChild("datePoidRes");

        //recyclerView
        recyclerView=findViewById(R.id.recyclerViewListePoids);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<PoidsClasse>().setQuery(query, PoidsClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<PoidsClasse, PoidsListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PoidsListViewHolder holder, int position, @NonNull PoidsClasse model) {
                final String keyPoids = getRef(position).getKey();

                holder.poidsItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyPoidsDel=keyPoids;
                        DeletePoids(keyPoidsDel);
                    }
                });
                holder.poidsItemDate.setText(model.getDatePoidRes());
                holder.poidsItemNote.setText(model.getNotePoidRes());
                holder.poidsItemPoids.setText(String.valueOf(model.getPoidsRes()));
            }

            @NonNull
            @Override
            public PoidsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_poids_resident,parent,false);
                return new PoidsListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void DeletePoids(final String keyPoidsDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListePoids.this);
        builder.setTitle("Supprimer Le Poids");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer ce poids de ce resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("poids").child(keyPoidsDel);
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
        if (view.getId() == R.id.ajouterNvPoids){
            Intent intent=new Intent(ConsulterListePoids.this, AjouterPoidsResident.class);
            intent.putExtra("key", keyResident);
            startActivity(intent);
        }
        if (view.getId() == R.id.retourFrConsulPoids){
            finish();
        }
    }
}
