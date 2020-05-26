package com.test.agingcarev01.FonctionsSurveillant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.test.agingcarev01.Classe.InfirmierClasse;
import com.test.agingcarev01.ConsulterListes.Adapters.InfirmierAffecteeAjouterListViewHolder;
import com.test.agingcarev01.ConsulterListes.Adapters.InfirmierAffecteeSupprimerListViewHolder;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class AffecteeInfirmiereResident extends AppCompatActivity implements View.OnClickListener {
    private Button affecterNouveauInfirmierShowBT,retourFrAffecterInfBT,annulerAffectationBT;
    private RecyclerView recyclerViewInfAffecter,recyclerViewNouveauInfAffecter;
    private TextView nvInfAffecteeTextView;
    private String keyResident,keyInfirmierSupprimer;
    private DatabaseReference databaseReferenceRemplir,databaseReferenceAffecter;
    private FirebaseRecyclerOptions<InfirmierClasse> options;
    private FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeAjouterListViewHolder> adapterAjouter;
    private FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeSupprimerListViewHolder> adapterSupprimer;
    private List<Integer> listeInfirmierAffecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affectee_infirmiere_resident);

        affecterNouveauInfirmierShowBT =findViewById(R.id.affecterNouveauInfirmierShow);
        affecterNouveauInfirmierShowBT.setOnClickListener(this);
        retourFrAffecterInfBT =findViewById(R.id.retourFrAffecterInf);
        retourFrAffecterInfBT.setOnClickListener(this);
        annulerAffectationBT =findViewById(R.id.annulerAffectation);
        annulerAffectationBT.setOnClickListener(this);

        recyclerViewInfAffecter = findViewById(R.id.recyclerViewInfirmierAffectee);
        recyclerViewInfAffecter.setHasFixedSize(true);
        recyclerViewInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewNouveauInfAffecter = findViewById(R.id.recyclerViewNouveauInfirmierAffectee);
        recyclerViewNouveauInfAffecter.setHasFixedSize(true);
        recyclerViewNouveauInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        nvInfAffecteeTextView=findViewById(R.id.nvInfAffecteeTextView);

        //fetch Resident
        keyResident = getIntent().getStringExtra("key");

        HideRecycleView();

        replirListeInfirmierAffecter();
        remplirRecycleViewInfirmierAffecter();
        remplirRecycleViewAjouterInfirmier();
    }

    private void replirListeInfirmierAffecter() {
        listeInfirmierAffecter = new ArrayList<>();
        DatabaseReference refList = FirebaseDatabase.getInstance().getReference()
                .child("Resident").child(keyResident).child("infirmier").child("listID");
        refList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tierSnapshot: dataSnapshot.getChildren()){
                    listeInfirmierAffecter.add(tierSnapshot.getValue(Integer.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void remplirRecycleViewInfirmierAffecter() {
        //fetch Liste infirmier
        databaseReferenceAffecter = FirebaseDatabase.getInstance().getReference()
                .child("Resident").child(keyResident).child("infirmier").child("listeInfirmier");


        recyclerViewInfAffecter = findViewById(R.id.recyclerViewInfirmierAffectee);
        recyclerViewInfAffecter.setHasFixedSize(true);
        recyclerViewInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<InfirmierClasse>().setQuery(databaseReferenceAffecter,InfirmierClasse.class).build();
        adapterSupprimer=new FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeSupprimerListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InfirmierAffecteeSupprimerListViewHolder holder, int position, @NonNull final InfirmierClasse model) {
                holder.nomInf.setText(model.getNom());
                holder.prenomInf.setText(model.getPrenom());
                holder.sexeInf.setText((model.getSexe()));
                final String keyInfirmier = getRef(position).getKey();

                holder.infAffecterSupprimerItemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyInfirmierSupprimer=keyInfirmier;
                        DeleteInfirmier(keyInfirmierSupprimer,model);
                    }
                });
            }

            @NonNull
            @Override
            public InfirmierAffecteeSupprimerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier_affectee_supprimer,parent,false);
                return new InfirmierAffecteeSupprimerListViewHolder(v);
            }
        };
        adapterSupprimer.startListening();
        recyclerViewInfAffecter.setAdapter(adapterSupprimer);
    }

    private void DeleteInfirmier(final String keyInfirmierSupprimer, final InfirmierClasse model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AffecteeInfirmiereResident.this);
        builder.setTitle("Supprimer l'infirmier");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer cet infirmier de ce resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int infID=model.getId();

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("infirmier").child("listeInfirmier").child(keyInfirmierSupprimer);
                delRef.removeValue();

                DatabaseReference delIdRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("infirmier").child("listID");
                delIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String keyIdInf = "";
                        for (DataSnapshot tierSnapshot: dataSnapshot.getChildren()){
                            if (tierSnapshot.getValue(Integer.class).equals(infID)){
                                keyIdInf = tierSnapshot.getKey();
                            }
                        }
                        supprimerIdFromList(keyIdInf);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void supprimerIdFromList(String keyIdInf) {
                DatabaseReference delIdRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("infirmier").child("listID").child(keyIdInf);
                delIdRef.removeValue();
            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    private void remplirRecycleViewAjouterInfirmier() {
        //fetch Liste infirmier
        databaseReferenceRemplir = FirebaseDatabase.getInstance().getReference().child("Employee");
        //check if statut d'Archivage est 0 (Profil non Archiver) et role est Infirmier
        Query roleEtStatutQuery = databaseReferenceRemplir.orderByChild("statutEtRole").equalTo("Infirmier_0");

        recyclerViewNouveauInfAffecter = findViewById(R.id.recyclerViewNouveauInfirmierAffectee);
        recyclerViewNouveauInfAffecter.setHasFixedSize(true);
        recyclerViewNouveauInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        options=new FirebaseRecyclerOptions.Builder<InfirmierClasse>().setQuery(roleEtStatutQuery,InfirmierClasse.class).build();
        adapterAjouter=new FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeAjouterListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InfirmierAffecteeAjouterListViewHolder holder, int position, @NonNull final InfirmierClasse model) {
                holder.nomInf.setText(model.getNom());
                holder.prenomInf.setText(model.getPrenom());
                holder.sexeInf.setText((model.getSexe()));

                holder.infAffecterAjouterItemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConfirmerAffectationDialog(model);
                    }
                });
            }

            @NonNull
            @Override
            public InfirmierAffecteeAjouterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier_affectee_ajouter,parent,false);
                return new InfirmierAffecteeAjouterListViewHolder(v);
            }
        };
        adapterAjouter.startListening();
        recyclerViewNouveauInfAffecter.setAdapter(adapterAjouter);
    }


    private void ConfirmerAffectationDialog(final InfirmierClasse model) {
        replirListeInfirmierAffecter();
        AlertDialog.Builder builder = new AlertDialog.Builder(AffecteeInfirmiereResident.this);
        builder.setTitle("Affecter l'infirmier");
        builder.setMessage("Êtes-vous sûr de vouloir affecter cet infirmier au resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int idInf = model.getId();
                InfirmierClasse nvInfirmierClasse = new InfirmierClasse(idInf,model.getEmail(),model.getNom(),model.getPrenom(),model.getSexe());

                if (!(listeInfirmierAffecter.contains(idInf))){
                    DatabaseReference myRefID = FirebaseDatabase.getInstance().getReference().child("Resident")
                            .child(keyResident).child("infirmier").child("listID").push();
                    myRefID.setValue(idInf);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident")
                            .child(keyResident).child("infirmier").child("listeInfirmier").push();
                    myRef.setValue(nvInfirmierClasse);
                    HideRecycleView();
                    Toast.makeText(AffecteeInfirmiereResident.this, "Affectation Réussite.", Toast.LENGTH_SHORT).show();
                }else {
                    DejaAffecterDialog();
                }

            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void DejaAffecterDialog() {
        //Toast.makeText(AffecteeInfirmiereResident.this, "Infirmier est deja affecter.", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(AffecteeInfirmiereResident.this);
        builder.setTitle("Infirmier est deja affecté a ce resident!");
        builder.setPositiveButton("D’accord", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.retourFrAffecterInf){
            finish();
        }
        if(view.getId()==R.id.affecterNouveauInfirmierShow){
            showRecycleView();
        }
        if(view.getId()==R.id.annulerAffectation){
            HideRecycleView();
        }
    }

    private void showRecycleView() {
        recyclerViewNouveauInfAffecter.setVisibility(View.VISIBLE);
        nvInfAffecteeTextView.setVisibility(View.VISIBLE);
        annulerAffectationBT.setVisibility(View.VISIBLE);
    }
    private void HideRecycleView() {
        recyclerViewNouveauInfAffecter.setVisibility(View.INVISIBLE);
        nvInfAffecteeTextView.setVisibility(View.INVISIBLE);
        annulerAffectationBT.setVisibility(View.INVISIBLE);
    }
}
