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
import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.InfirmierListViewHolder;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteInfirmier;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilEmployee;
import com.test.agingcarev01.R;

public class ConsulterListeInfirmier extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulInfBT, creeNvCompteInfirmierBT;
    private DatabaseReference databaseReference;

    private FirebaseRecyclerOptions<InfirmierClasse> options;
    private FirebaseRecyclerAdapter<InfirmierClasse, InfirmierListViewHolder> adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_infirmier);

        retourFrConsulInfBT = findViewById(R.id.retourFrConsulInf);
        retourFrConsulInfBT.setOnClickListener(this);
        creeNvCompteInfirmierBT = findViewById(R.id.creeNvCompteInfirmier);
        creeNvCompteInfirmierBT.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        //check if statut d'Archivage est 0 (Profil non Archiver) et role est Infirmier
        Query roleEtStatutQuery = databaseReference.orderByChild("statutEtRole").equalTo("Infirmier_0");

        recyclerView = findViewById(R.id.recyclerViewListeInfirmier);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<InfirmierClasse>().setQuery(roleEtStatutQuery, InfirmierClasse.class).build();
        adapter = new FirebaseRecyclerAdapter<InfirmierClasse, InfirmierListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InfirmierListViewHolder holder, int position, @NonNull InfirmierClasse model) {
                final String emailKey = model.getEmail();
                final String key = getRef(position).getKey();
                holder.InfItemModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ConsulterListeInfirmier.this, ConsulterProfilEmployee.class);
                        intent.putExtra("email", emailKey);
                        startActivity(intent);
                    }
                });
                holder.InfItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String keyDel = key;
                        ArchivageDialog(keyDel);
                    }
                });
                holder.nomInf.setText(model.getNom());
                holder.prenomInf.setText(model.getPrenom());
                holder.emailInf.setText((model.getEmail()));
                holder.sexeInf.setText((model.getSexe()));
            }

            @NonNull
            @Override
            public InfirmierListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier, parent, false);
                return new InfirmierListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void ArchivageDialog(final String keyDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeInfirmier.this);
        builder.setTitle("Archiver Le Compte Infirmier");
        builder.setMessage("Êtes-vous sûr de vouloir archiver le compte de ce infirmier?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //statut d'Archivage est 1 (Profil  Archiver)
                FirebaseDatabase.getInstance().getReference("Employee").child(keyDel).child("statutEtRole").setValue("Infirmier_1");
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
        if (view.getId() == R.id.retourFrConsulInf) {
            finish();
        }
        if (view.getId() == R.id.creeNvCompteInfirmier) {
            startActivity(new Intent(ConsulterListeInfirmier.this, CreerCompteInfirmier.class));
        }
    }
}
