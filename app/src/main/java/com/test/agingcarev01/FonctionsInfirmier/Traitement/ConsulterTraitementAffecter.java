package com.test.agingcarev01.FonctionsInfirmier.Traitement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.TraitementClasse;
import com.test.agingcarev01.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ConsulterTraitementAffecter extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ConsulterTraitementAffe";

    private Button retourFrConsultTriatementAffecterInfirmier;
    private HorizontalCalendar horizontalCalendar;
    private RecyclerView recyclerView;

    private String keyInfirmier;
    private List<String> listeResidentAffecter;
    private List<TraitementClasse> traitementClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_traitement_affecter);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeTraitementCalender);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetKeyInfirmier();
        RemplirListeResidentAffecter();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //show RDV du jour avant click sur date
                String selectedDateStr = DateFormat.format("yyyy/MM/dd", Calendar.getInstance()).toString();
                GetAllTraitementInfirmierEtShow(selectedDateStr);
            }
        }, 2000);

        retourFrConsultTriatementAffecterInfirmier = findViewById(R.id.retourFrConsultTriatementAffecterInfirmier);
        retourFrConsultTriatementAffecterInfirmier.setOnClickListener(this);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);


        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarViewTraitement)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(true)
                .textColor(Color.LTGRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("yyyy/MM/dd", date).toString();
                GetAllTraitementInfirmierEtShow(selectedDateStr);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalCalendar.goToday(false);
            }
        });

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
//                    Log.e(TAG, "key Infirmier fn: " + keyInfirmier);
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
                            DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("Resident")
                                    .child(keyRes).child("id");
                            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Log.e(TAG, "id res liste: " + dataSnapshot.getValue());
                                    listeResidentAffecter.add(String.valueOf(dataSnapshot.getValue(Integer.class)));
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
        }, 2000);

    }

    private void GetAllTraitementInfirmierEtShow(final String selectedDateStr) {
        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeTraitementCalender);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Traitement");
//        Query query = databaseReference1.orderByChild("idResident");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                traitementClasses = new ArrayList<TraitementClasse>();
                for (final DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "key Traitement: " + tierSnapshot.getKey());

                    final DatabaseReference myRef = databaseReference1.child(tierSnapshot.getKey()).child("idResident");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.e(TAG, "id Resident: " + dataSnapshot.getValue());
                            if (listeResidentAffecter.contains(String.valueOf(dataSnapshot.getValue()))) {
                                Log.e(TAG, "idResident affecter Traitement: " + dataSnapshot.getValue());
                                TraitementClasse traitementClasse = tierSnapshot.getValue(TraitementClasse.class);
                                String dateDebut = traitementClasse.getDateDebutTaitement();
                                String dateFin = traitementClasse.getDateFinTaitement();

                                SimpleDateFormat dD = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat dF = new SimpleDateFormat("yyyy/MM/dd");
                                SimpleDateFormat dQ = new SimpleDateFormat("yyyy/MM/dd");
                                try {
                                    Date dd = dD.parse(dateDebut);
                                    Date df = dF.parse(dateFin);
                                    Date dq = dQ.parse(selectedDateStr);
                                    long timeDebutInMilliseconds = dd.getTime();
                                    long timeFinInMilliseconds = df.getTime();
                                    long timeCompareInMilliseconds = dq.getTime();

                                    if (timeDebutInMilliseconds <= timeCompareInMilliseconds && timeFinInMilliseconds >= timeCompareInMilliseconds) {
//                                        //rendezVousClasseList.add(rendezVousClasse);
//                                        Log.e(TAG, "-------------------------");
//                                        Log.e(TAG, "key: " + tierSnapshot.getKey());
//                                        Log.e(TAG, "date deb: " + timeDebutInMilliseconds);
//                                        Log.e(TAG, "date comp: " + timeCompareInMilliseconds);
//                                        Log.e(TAG, "-------------------------");
                                        String repetition = traitementClasse.getJoursRepetition();
                                        if (repetition.equals("Tous Les Jour")){
                                            Log.e(TAG, "tous les jour: " );
                                            traitementClasses.add(traitementClasse);
                                        }else {
                                            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                                            Date dateFormat = new java.util.Date(timeCompareInMilliseconds);
                                            String weekday = sdf.format(dateFormat);
                                            String[] arrOfStr = repetition.split(",");
                                            for (String a : arrOfStr){
                                                if (weekday.equals(a)){
//                                                    Log.e(TAG, "ici: "+weekday );
                                                    traitementClasses.add(traitementClasse);
                                                }
                                            }
                                        }
                                    }
                                } catch (ParseException ex) {
                                    Log.e("Exception", ex.getLocalizedMessage());
                                }
                            }
                            final TraitementAdapter adapter = new TraitementAdapter(ConsulterTraitementAffecter.this, traitementClasses);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new TraitementAdapter.OnItemClickListener() {
                                @Override
                                public void onIconClick(int position) {
                                    AfficherDetailRDV(traitementClasses.get(position));
                                }
                            });
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
    private void AfficherDetailRDV(TraitementClasse traitementClasse) {
        final Intent intent = new Intent(ConsulterTraitementAffecter.this, ConsulterDetailTraitement.class);
        intent.putExtra("nom", traitementClasse.getNom());
        intent.putExtra("type", traitementClasse.getType());
        intent.putExtra("dose", String.valueOf(traitementClasse.getDose()));
        intent.putExtra("unite", traitementClasse.getUnite());
        intent.putExtra("dateDebut", traitementClasse.getDateDebutTaitement());
        intent.putExtra("DateFin", traitementClasse.getDateFinTaitement());
        intent.putExtra("repetition",traitementClasse.getJoursRepetition());
        intent.putExtra("temp1", traitementClasse.getTemp1());
        intent.putExtra("temp2", traitementClasse.getTemp2());
        intent.putExtra("temp3", traitementClasse.getTemp3());
        intent.putExtra("temp4", traitementClasse.getTemp4());
        intent.putExtra("temp5", traitementClasse.getTemp5());
        intent.putExtra("temp6", traitementClasse.getTemp6());
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrConsultTriatementAffecterInfirmier) {
            finish();
        }
    }
}