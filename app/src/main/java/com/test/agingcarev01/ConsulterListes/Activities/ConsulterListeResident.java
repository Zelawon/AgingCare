package com.test.agingcarev01.ConsulterListes.Activities;

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
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.ResidentListViewHolder;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilResident;
import com.test.agingcarev01.Login;
import com.test.agingcarev01.R;

public class ConsulterListeResident extends AppCompatActivity implements View.OnClickListener {

    private Button retourFrConsulResBT;
    DatabaseReference databaseReference ;

    private FirebaseRecyclerOptions<ResidentClasse> options;
    private FirebaseRecyclerAdapter<ResidentClasse, ResidentListViewHolder> adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_resident);
        retourFrConsulResBT=findViewById(R.id.retourFrConsulResident);
        retourFrConsulResBT.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident");

        recyclerView=findViewById(R.id.recyclerViewListeResident);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<ResidentClasse>().setQuery(databaseReference,ResidentClasse.class).build();
        adapter=new FirebaseRecyclerAdapter<ResidentClasse, ResidentListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ResidentListViewHolder holder, int position, @NonNull ResidentClasse model) {
                final String key=getRef(position).getKey();
                holder.ResItemModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ConsulterListeResident.this, ConsulterProfilResident.class);
                        intent.putExtra("id",key);
                        startActivity(intent);
                    }
                });
                holder.ResItemArchiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(ConsulterListeResident.this, Login.class);
                        intent.putExtra("id",key);
                        startActivity(intent);
                    }
                });
                holder.nomRes.setText(model.getNom());
                holder.prenomRes.setText(model.getPrenom());
                holder.sexeRes.setText((model.getSexeRes()));
                holder.idRes.setText(String.valueOf(model.getId()));
            }

            @NonNull
            @Override
            public ResidentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_resident,parent,false);
                return new ResidentListViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrConsulResident){
            finish();
        }
    }
}
