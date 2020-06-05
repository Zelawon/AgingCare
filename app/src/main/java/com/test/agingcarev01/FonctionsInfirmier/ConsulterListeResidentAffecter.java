package com.test.agingcarev01.FonctionsInfirmier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.ResidentClasse;
import com.test.agingcarev01.FonctionsProfil.ConsulterProfilResident;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListeResidentAffecter extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ConsulterListeResidentA";

    private Button retourFrConsulResidentAffecter;
    private List<String> listeResidentAffecter;
    private String keyInfirmier;
    private RecyclerView recyclerViewListeResidentAffecter;
    private List<ResidentClasse> residentClasseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_liste_resident_affecter);

        retourFrConsulResidentAffecter = findViewById(R.id.retourFrConsulResidentAffecter);
        retourFrConsulResidentAffecter.setOnClickListener(this);

        recyclerViewListeResidentAffecter = findViewById(R.id.recyclerViewListeResidentAffecter);
        recyclerViewListeResidentAffecter.setHasFixedSize(true);
        recyclerViewListeResidentAffecter.setLayoutManager(new LinearLayoutManager(this));

        RempirRecyclerViewResidentAffecter();

    }

    private void GetKeyInfirmier() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();
        final DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    keyInfirmier = tierSnapshot.getKey();
                    //Log.e(TAG, "key Infirmier fn: " + keyInfirmier);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void RemplirListeResidentAffecter() {
        GetKeyInfirmier();
        //wait 1s pour le get de la key de l'infirmier
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                listeResidentAffecter = new ArrayList<>();
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Employee")
                        .child(keyInfirmier).child("idResidentAffecte");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String keyRes = snapshot.getValue(String.class);
                            listeResidentAffecter.add(keyRes);
                            //Log.e(TAG, "key Resident: fn" + keyRes);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }, 1500);

    }

    private void RempirRecyclerViewResidentAffecter() {
        RemplirListeResidentAffecter();
        final DatabaseReference residentRef = FirebaseDatabase.getInstance().getReference("Resident");
        final Query statutQuery = residentRef.orderByChild("statutArchivage").equalTo(0);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                statutQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        residentClasseList = new ArrayList<ResidentClasse>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //Log.e(TAG, "Key all Resident: " + snapshot.getKey());
                            ResidentClasse residentClasse = snapshot.getValue(ResidentClasse.class);
                            if (listeResidentAffecter.contains(snapshot.getKey())) {
                                residentClasseList.add(residentClasse);
                                //Log.e(TAG, "key resident ajouter: " + snapshot.getKey());
                            }
                        }
                        final ResidentAffecterAdapter adapter = new ResidentAffecterAdapter(ConsulterListeResidentAffecter.this, residentClasseList);
                        recyclerViewListeResidentAffecter.setAdapter(adapter);
                        adapter.setOnItemClickListener(new ResidentAffecterAdapter.OnItemClickListener() {
                            @Override
                            public void onIconClick(int position) {
                                ConfirmerAffectationDialog(position);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }, 2000);
    }

    private void ConfirmerAffectationDialog(final int position) {
        //Log.e(TAG, "position dans le recylcerView: "+position );
        //Log.e(TAG, "key du item: "+listeResidentAffecter.get(position));
        String key = listeResidentAffecter.get(position);
        Intent intent = new Intent(ConsulterListeResidentAffecter.this, ConsulterProfilResident.class);
        intent.putExtra("id", key);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrConsulResidentAffecter) {
            finish();
        }
    }
}