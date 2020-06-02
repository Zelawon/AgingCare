package com.test.agingcarev01.FonctionsSurveillant.TensionArterielle;

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
import com.test.agingcarev01.Classe.TensionArterielleClasse;
import com.test.agingcarev01.R;

public class ConsulterListeTensionArterielle extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulTensionArterielle,ajouterNvTTensionArterielle;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<TensionArterielleClasse> options;
    private FirebaseRecyclerAdapter<TensionArterielleClasse, TensionArterielleListViewHolder> adapter;
    private String keyResident, keyTensionArtDel;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_tension_arterielle);

        retourFrConsulTensionArterielle=findViewById(R.id.retourFrConsulTensionArterielle);
        retourFrConsulTensionArterielle.setOnClickListener(this);
        ajouterNvTTensionArterielle=findViewById(R.id.ajouterNvTTensionArterielle);
        ajouterNvTTensionArterielle.setOnClickListener(this);

        //fetch
        keyResident =getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tensionArterielle");
        Query query = databaseReference.orderByChild("dateTensionArterielle");

        //recyclerView
        recyclerView=findViewById(R.id.recyclerViewListeTensionArterielle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<TensionArterielleClasse>().setQuery(query, TensionArterielleClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<TensionArterielleClasse, TensionArterielleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TensionArterielleListViewHolder holder, int position, @NonNull TensionArterielleClasse model) {
                final String key = getRef(position).getKey();

                holder.tensionArtArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyTensionArtDel =key;
                        DeleteTensionArterielle(keyTensionArtDel);
                    }
                });
                holder.tensionArtItemDate.setText(model.getDateTensionArterielle());
                holder.tensionArtItem.setText(String.valueOf(model.getPressionSystolique())+"/"+String.valueOf(model.getPreessionDiatolique()));
                holder.brasMesureItem.setText(model.getBrasMesure());
                holder.tensionArtItemNote.setText(model.getNoteTensionArterielle());
            }

            @NonNull
            @Override
            public TensionArterielleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tension_arterielle_resident,parent,false);
                return new TensionArterielleListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void DeleteTensionArterielle(final String keyTensionArtDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeTensionArterielle.this);
        builder.setTitle("Supprimer La Tension Arterielle");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer la tension arterielle de ce resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("tensionArterielle").child(keyTensionArtDel);
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
        if (view.getId() == R.id.ajouterNvTTensionArterielle){
            Intent intent=new Intent(ConsulterListeTensionArterielle.this, AjouterTensionArterielle.class);
            intent.putExtra("key", keyResident);
            startActivity(intent);
        }
        if (view.getId() == R.id.retourFrConsulTensionArterielle){
            finish();
        }
    }
}
