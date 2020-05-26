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
import com.test.agingcarev01.Classe.SurveillantClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.SurveillantListViewHolder;
import com.test.agingcarev01.FonctionsDirectuer.CreerCompteSurveillant;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilEmployee;
import com.test.agingcarev01.R;

public class ConsulterListeSurveillant extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulSurvBT,creeNvCompteSurveillantBT;
    private DatabaseReference databaseReference ;

    private FirebaseRecyclerOptions<SurveillantClasse> options;
    private FirebaseRecyclerAdapter<SurveillantClasse, SurveillantListViewHolder> adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_surveillant);

        retourFrConsulSurvBT=findViewById(R.id.retourFrConsulSurv);
        retourFrConsulSurvBT.setOnClickListener(this);

        creeNvCompteSurveillantBT =findViewById(R.id.creeNvCompteSurveillant);
        creeNvCompteSurveillantBT.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");
        //check if statut d'Archivage est 0 (Profil non Archiver) et role est Surveillant
        Query roleEtStatutQuery = databaseReference.orderByChild("statutEtRole").equalTo("Surveillant_0");


        recyclerView = findViewById(R.id.recyclerViewListeSurveillant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<SurveillantClasse>().setQuery(roleEtStatutQuery,SurveillantClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<SurveillantClasse, SurveillantListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SurveillantListViewHolder holder, int position, @NonNull SurveillantClasse model) {
                final String emailKey = model.getEmail();
                final String key = getRef(position).getKey();
                holder.survItemModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ConsulterListeSurveillant.this, ConsulterProfilEmployee.class);
                        intent.putExtra("email",emailKey);
                        startActivity(intent);
                    }
                });
                holder.survItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String keyDel=key;
                        ArchivageDialog(keyDel);
                    }
                });
                holder.nomSurv.setText(model.getNom());
                holder.prenomSurv.setText(model.getPrenom());
                holder.emailSurv.setText((model.getEmail()));
            }

            @NonNull
            @Override
            public SurveillantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_surveillant,parent,false);
                return new SurveillantListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void ArchivageDialog(final String keyDel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsulterListeSurveillant.this);
        builder.setTitle("Archiver Le Compte Surveillant");
        builder.setMessage("Êtes-vous sûr de vouloir archiver le compte de ce Surveillant?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //statut d'Archivage est 1 (Profil  Archiver)
                FirebaseDatabase.getInstance().getReference("Employee").child(keyDel).child("statutEtRole").setValue("Surveillant_1");
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
        if(view.getId()==R.id.retourFrConsulSurv){
            finish();
        }
        if(view.getId()==R.id.creeNvCompteSurveillant){
            startActivity(new Intent(ConsulterListeSurveillant.this, CreerCompteSurveillant.class));
        }
    }
}
