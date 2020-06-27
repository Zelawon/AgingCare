package com.test.agingcarev01.FonctionsSurveillant.AffecterInfirmier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AffecteeInfirmiereResident extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AffecteeInfirmiereResid";
    private Button affecterNouveauInfirmierShowBT, retourFrAffecterInfBT, annulerAffectationBT;
    private RecyclerView recyclerViewInfAffecter, recyclerViewNouveauInfAffecter;
    private TextView nvInfAffecteeTextView;
    private String keyResident, keyInfirmierSupprimer;
    private DatabaseReference databaseReferenceRemplir, databaseReferenceAffecter;
    private FirebaseRecyclerOptions<InfirmierClasse> options;
    private FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeSupprimerListViewHolder> adapterSupprimer;
    private List<Integer> listeInfirmierAffecter;
    private List<InfirmierClasse> infirmierClasseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affectee_infirmiere_resident);

        affecterNouveauInfirmierShowBT = findViewById(R.id.affecterNouveauInfirmierShow);
        affecterNouveauInfirmierShowBT.setOnClickListener(this);
        retourFrAffecterInfBT = findViewById(R.id.retourFrAffecterInf);
        retourFrAffecterInfBT.setOnClickListener(this);
        annulerAffectationBT = findViewById(R.id.annulerAffectation);
        annulerAffectationBT.setOnClickListener(this);

        recyclerViewInfAffecter = findViewById(R.id.recyclerViewInfirmierAffectee);
        recyclerViewInfAffecter.setHasFixedSize(true);
        recyclerViewInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewNouveauInfAffecter = findViewById(R.id.recyclerViewNouveauInfirmierAffectee);
        recyclerViewNouveauInfAffecter.setHasFixedSize(true);
        recyclerViewNouveauInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        nvInfAffecteeTextView = findViewById(R.id.nvInfAffecteeTextView);

        //fetch Resident
        keyResident = getIntent().getStringExtra("key");

        HideRecycleView();

        replirListeIdDesInfirmierAffecterAuResident();
        remplirRecyclerViewDesInfirmierDejaAffecterAuResident();
        remplirRecyclerViewDesInfirmierNonAffecterAuResident();
    }

    private void replirListeIdDesInfirmierAffecterAuResident() {
        listeInfirmierAffecter = new ArrayList<>();
        DatabaseReference refList = FirebaseDatabase.getInstance().getReference()
                .child("Resident").child(keyResident).child("infirmier").child("listID");
        refList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    listeInfirmierAffecter.add(tierSnapshot.getValue(Integer.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void remplirRecyclerViewDesInfirmierDejaAffecterAuResident() {
        //fetch Liste infirmier
        databaseReferenceAffecter = FirebaseDatabase.getInstance().getReference()
                .child("Resident").child(keyResident).child("infirmier").child("listeInfirmier");


        recyclerViewInfAffecter = findViewById(R.id.recyclerViewInfirmierAffectee);
        recyclerViewInfAffecter.setHasFixedSize(true);
        recyclerViewInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<InfirmierClasse>().setQuery(databaseReferenceAffecter, InfirmierClasse.class).build();
        adapterSupprimer = new FirebaseRecyclerAdapter<InfirmierClasse, InfirmierAffecteeSupprimerListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull InfirmierAffecteeSupprimerListViewHolder holder, int position, @NonNull final InfirmierClasse model) {
                holder.nomInf.setText(model.getNom());
                holder.prenomInf.setText(model.getPrenom());
                holder.sexeInf.setText((model.getSexe()));
                final String keyInfirmier = getRef(position).getKey();

                holder.infAffecterSupprimerItemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        keyInfirmierSupprimer = keyInfirmier;
                        DeleteInfirmierDuResident(keyInfirmierSupprimer, model);
                    }
                });
            }

            @NonNull
            @Override
            public InfirmierAffecteeSupprimerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_infirmier_affectee_supprimer, parent, false);
                return new InfirmierAffecteeSupprimerListViewHolder(v);
            }
        };
        adapterSupprimer.startListening();
        recyclerViewInfAffecter.setAdapter(adapterSupprimer);
    }

    private void DeleteInfirmierDuResident(final String keyInfirmierSupprimer, final InfirmierClasse model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AffecteeInfirmiereResident.this);
        builder.setTitle("Supprimer l'infirmier");
        builder.setMessage("Êtes-vous sûr de vouloir supprimer cet infirmier de ce resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final int infID = model.getId();

                replirListeIdDesInfirmierAffecterAuResident();

                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("infirmier").child("listeInfirmier").child(keyInfirmierSupprimer);
                delRef.removeValue();

                DatabaseReference delIdRef = FirebaseDatabase.getInstance().getReference()
                        .child("Resident").child(keyResident).child("infirmier").child("listID");
                delIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String keyIdInf = "";
                        Integer idInf = null;
                        for (DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                            if (tierSnapshot.getValue(Integer.class).equals(infID)) {
                                keyIdInf = tierSnapshot.getKey();
                                idInf = tierSnapshot.getValue(Integer.class);
                            }
                        }
                        supprimerIdFromList(keyIdInf);
                        supprimerIdFromInfirmier(idInf);
                        remplirRecyclerViewDesInfirmierNonAffecterAuResident();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void supprimerIdFromInfirmier(Integer idInf) {
                //get key de l'infirmier idInf
                final DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
                Query emailQuery = employeeRef.orderByChild("id").equalTo(idInf);
                emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final String keyInfDelete = dataSnapshot1.getKey();
                            Log.e(TAG, "key Infirmier delete: " + keyInfDelete);
                            Log.e(TAG, "key Resident delete:: " + keyResident);
                            final DatabaseReference myRef6 = employeeRef.child(keyInfDelete).child("idResidentAffecte");
                            myRef6.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                                        String CurrentValue = tierSnapshot.getValue(String.class);
                                        Log.e(TAG, "onDataChange: " + CurrentValue);
                                        if (CurrentValue.equals(keyResident)) {
                                            deleteResFromInf(tierSnapshot.getKey());
                                        }
                                    }
                                }

                                private void deleteResFromInf(String key) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee")
                                            .child(keyInfDelete).child("idResidentAffecte").child(key);
                                    databaseReference.removeValue();
                                    Log.e(TAG, "Deleted !");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
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


    private void remplirRecyclerViewDesInfirmierNonAffecterAuResident() {
        replirListeIdDesInfirmierAffecterAuResident();
        //fetch Liste infirmier
        databaseReferenceRemplir = FirebaseDatabase.getInstance().getReference().child("Employee");
        //check if statut d'Archivage est 0 (Profil non Archiver) et role est Infirmier
        Query roleEtStatutQuery = databaseReferenceRemplir.orderByChild("statutEtRole").equalTo("Infirmier_0");

        recyclerViewNouveauInfAffecter = findViewById(R.id.recyclerViewNouveauInfirmierAffectee);
        recyclerViewNouveauInfAffecter.setHasFixedSize(true);
        recyclerViewNouveauInfAffecter.setLayoutManager(new LinearLayoutManager(this));

        roleEtStatutQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                infirmierClasseList = new ArrayList<InfirmierClasse>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {
                    InfirmierClasse post = postsnap.getValue(InfirmierClasse.class);
                    //check si l'infirmier est deja affecter
                    if (!(listeInfirmierAffecter.contains(post.getId()))) {
                        infirmierClasseList.add(post);
                    }
                }
                final InfirmierAffecteeAjouterAdapter adapter = new InfirmierAffecteeAjouterAdapter(AffecteeInfirmiereResident.this, infirmierClasseList);
                recyclerViewNouveauInfAffecter.setAdapter(adapter);
                adapter.setOnItemClickListener(new InfirmierAffecteeAjouterAdapter.OnItemClickListener() {
                    @Override
                    public void onIconClick(int position) {
                        ConfirmerAffectationDialog(infirmierClasseList.get(position));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ConfirmerAffectationDialog(final InfirmierClasse model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AffecteeInfirmiereResident.this);
        builder.setTitle("Affecter l'infirmier");
        builder.setMessage("Êtes-vous sûr de vouloir affecter cet infirmier au resident?");
        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int idInf = model.getId();
                String emailInf = model.getEmail();
                InfirmierClasse nvInfirmierClasse = new InfirmierClasse(idInf, model.getEmail(), model.getNom(), model.getPrenom(), model.getSexe());
                //ajout de l'id de l'infirmier a la liste des id
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference().child("Resident")
                        .child(keyResident).child("infirmier").child("listID").push();
                myRef1.setValue(idInf);

                //ajout de l'objet infirmier au resident
                DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Resident")
                        .child(keyResident).child("infirmier").child("listeInfirmier").push();
                myRef2.setValue(nvInfirmierClasse);

                //ajout de l'id du resident a l'infirmier
                AffecterResidentInfirmier(emailInf);

                HideRecycleView();
                Toasty.success(AffecteeInfirmiereResident.this, "Affectation Réussite.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void AffecterResidentInfirmier(String emailInf) {
        //get key de l'infirmier idInf
        final DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(emailInf);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //get id Resident
                    final DatabaseReference myRef4 = FirebaseDatabase.getInstance().getReference()
                            .child("Resident").child(keyResident);
                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DatabaseReference myRef5 = employeeRef.child(dataSnapshot1.getKey()).child("idResidentAffecte").push();
//                            myRef5.setValue(dataSnapshot.child("id").getValue(Integer.class));// set value id du resident
                            myRef5.setValue(dataSnapshot.getKey());//set value key du resident (mieux)
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrAffecterInf) {
            finish();
        }
        if (view.getId() == R.id.affecterNouveauInfirmierShow) {
            showRecycleView();
        }
        if (view.getId() == R.id.annulerAffectation) {
            HideRecycleView();
        }
    }

    private void showRecycleView() {
        remplirRecyclerViewDesInfirmierNonAffecterAuResident();
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
