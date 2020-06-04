package com.test.agingcarev01.ConsulterListes.Activities;

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
import com.test.agingcarev01.Classe.DirecteurClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.DirecteurListViewHolder;
import com.test.agingcarev01.FonctionsAdmin.CreerCompteDirecteur;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilEmployee;
import com.test.agingcarev01.R;

public class ConsulterListeDirecteur extends AppCompatActivity implements View.OnClickListener {
    private Button retourFrConsulDirecBT, creeNvCompteDirecteurBT;
    private DatabaseReference databaseReference;

    private FirebaseRecyclerOptions<DirecteurClasse> options;
    private FirebaseRecyclerAdapter<DirecteurClasse, DirecteurListViewHolder> adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_directeur);

        retourFrConsulDirecBT = findViewById(R.id.retourFrConsulDirec);
        retourFrConsulDirecBT.setOnClickListener(this);
        creeNvCompteDirecteurBT = findViewById(R.id.creeNvCompteDirecteur);
        creeNvCompteDirecteurBT.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        //check if statut d'Archivage est 0 (Profil non Archiver) et role est Directeur
        Query roleEtStatutQuery = databaseReference.orderByChild("statutEtRole").equalTo("Directeur_0");

        recyclerView = findViewById(R.id.recyclerViewListeDirecteur);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<DirecteurClasse>().setQuery(roleEtStatutQuery, DirecteurClasse.class).build();
        adapter = new FirebaseRecyclerAdapter<DirecteurClasse, DirecteurListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DirecteurListViewHolder holder, int position, @NonNull DirecteurClasse model) {
                final String emailKey = model.getEmail();
                final String key = getRef(position).getKey();
                holder.DirecItemModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ConsulterListeDirecteur.this, ConsulterProfilEmployee.class);
                        intent.putExtra("email", emailKey);
                        startActivity(intent);
                    }
                });
                holder.DirecItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String keyDel = key;
                        ArchivageDialog(keyDel);
                    }
                });
                holder.nomDirec.setText(model.getNom());
                holder.prenomDirec.setText(model.getPrenom());
                holder.emailDirec.setText((model.getEmail()));
            }

            @NonNull
            @Override
            public DirecteurListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_directeur, parent, false);
                return new DirecteurListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void ArchivageDialog(final String keyDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeDirecteur.this);
        builder.setTitle("Archiver Le Compte Directeur");
        builder.setMessage("Êtes-vous sûr de vouloir archiver le compte de ce directeur?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //statut d'Archivage est 1 (Profil  Archiver)
                FirebaseDatabase.getInstance().getReference("Employee").child(keyDel).child("statutEtRole").setValue("Directeur_1");
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
        if (view.getId() == R.id.retourFrConsulDirec) {
            finish();
        }
        if (view.getId() == R.id.creeNvCompteDirecteur) {
            startActivity(new Intent(ConsulterListeDirecteur.this, CreerCompteDirecteur.class));
        }
    }
}
