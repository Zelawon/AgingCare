package com.test.agingcarev01.ConsulterListes.Activities;

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
import com.test.agingcarev01.Classe.MaladieClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.MaladieListViewHolder;
import com.test.agingcarev01.FonctionsSurveillant.AjouterMaladieDialog;
import com.test.agingcarev01.R;

public class ConsulterListeMaladie extends AppCompatActivity implements View.OnClickListener, AjouterMaladieDialog.AjoutMaladieDialogListner {

    private Button retourFrConsulMaladieBT,creeNvMaladieResidentBT;
    private DatabaseReference databaseReference ;
    String keyResident,keyMaladieDel;

    private FirebaseRecyclerOptions<MaladieClasse> options;
    private FirebaseRecyclerAdapter<MaladieClasse, MaladieListViewHolder> adapter;


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_maladie);

        retourFrConsulMaladieBT=findViewById(R.id.retourFrConsulMaladie);
        retourFrConsulMaladieBT.setOnClickListener(this);
        creeNvMaladieResidentBT=findViewById(R.id.creeNvMaladieResident);
        creeNvMaladieResidentBT.setOnClickListener(this);

        //fetch Maladie
        keyResident =getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("maladie");

        //recyclerView
        recyclerView=findViewById(R.id.recyclerViewListeMaladie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<MaladieClasse>().setQuery(databaseReference,MaladieClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<MaladieClasse, MaladieListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MaladieListViewHolder holder, int position, @NonNull MaladieClasse model) {
                final String keyMaladie = getRef(position).getKey();

                holder.maladieItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyMaladieDel=keyMaladie;
                        DeleteMAladie(keyMaladieDel);
                    }
                });
                holder.nomMaladie.setText(model.getNomMaladie());
            }

            @NonNull
            @Override
            public MaladieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_maladie,parent,false);
                return new MaladieListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);



    }

    private void DeleteMAladie(String keyMaladieDel) {
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("maladie").child(keyMaladieDel);
        delRef.removeValue();
    }

    private void AjouterMaladie() {
        AjouterMaladieDialog ajouterMaladieDialog = new AjouterMaladieDialog();
        ajouterMaladieDialog.show(getSupportFragmentManager(),"Ajouter Maladie");
    }
    @Override
    public void applyNvNom(String nom) {
        MaladieClasse nvMaladieClasse = new MaladieClasse(nom);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("maladie").push();
        myRef.setValue(nvMaladieClasse);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrConsulMaladie){
            finish();
        }
        if(view.getId()==R.id.creeNvMaladieResident){
            AjouterMaladie();
        }
    }
}
