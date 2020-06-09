package com.test.agingcarev01.FonctionsInfirmier.RendezVous;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
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
import com.test.agingcarev01.Classe.RendezVousClasse;
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ConsulterRendezVousAffecter extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ConsulterRendezVousAffe";

    private Button retourFrConsultRDVAffecterInfirmier;
    private HorizontalCalendar horizontalCalendar;
    private RecyclerView recyclerView;

    private String keyInfirmier;
    private List<String> listeResidentAffecter;
    private List<RendezVousClasse> rendezVousClasseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_rendez_vous_affecter);

        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeRDVCalender);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetKeyInfirmier();
        RemplirListeResidentAffecter();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //show RDV du jour avant click sur date
                String selectedDateStr = DateFormat.format("yyyy/MM/dd", Calendar.getInstance()).toString();
                GetAllRendezVousInfirmierEtShow(selectedDateStr);
            }
        }, 2000);

        retourFrConsultRDVAffecterInfirmier = findViewById(R.id.retourFrConsultRDVAffecterInfirmier);
        retourFrConsultRDVAffecterInfirmier.setOnClickListener(this);

        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);


        final Calendar defaultSelectedDate = Calendar.getInstance();

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarViewRDV)
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
                /*.addEvents(new CalendarEventsPredicate() {

                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date) {
                        List<CalendarEvent> events = new ArrayList<>();
                        int count = rnd.nextInt(6);

                        for (int i = 0; i <= count; i++){
                            events.add(new CalendarEvent(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)), "event"));
                        }

                        return events;
                    }
                })*/
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("yyyy/MM/dd", date).toString();
                GetAllRendezVousInfirmierEtShow(selectedDateStr);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
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

    private void GetAllRendezVousInfirmierEtShow(final String selectedDateStr) {
        //recyclerView
        recyclerView = findViewById(R.id.recyclerViewListeRDVCalender);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("RendezVous");
//        Query query = databaseReference1.orderByChild("idResident");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rendezVousClasseList = new ArrayList<RendezVousClasse>();
                for (final DataSnapshot tierSnapshot : dataSnapshot.getChildren()) {
//                    Log.e(TAG, "key RDV: " + tierSnapshot.getKey());

                    final DatabaseReference myRef = databaseReference1.child(tierSnapshot.getKey()).child("idResident");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Log.e(TAG, "idResident RDV: " + dataSnapshot.getValue());
                            if (listeResidentAffecter.contains(String.valueOf(dataSnapshot.getValue()))) {
//                                Log.e(TAG, "idResident affecter RDV: " + dataSnapshot.getValue());
                                RendezVousClasse rendezVousClasse = tierSnapshot.getValue(RendezVousClasse.class);
//                                Log.e(TAG, "onDataChange: " + selectedDateStr);
                                String dateRdvRes = rendezVousClasse.getDateRDV();

                                if (dateRdvRes.equals(selectedDateStr)) {
                                    rendezVousClasseList.add(rendezVousClasse);
                                }
                            }
                            final RendezVousAdapter adapter = new RendezVousAdapter(ConsulterRendezVousAffecter.this, rendezVousClasseList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new RendezVousAdapter.OnItemClickListener() {
                                @Override
                                public void onIconClick(int position) {
                                    AfficherDetailRDV(rendezVousClasseList.get(position));
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

    private void AfficherDetailRDV(RendezVousClasse rendezVousClasse) {
        final Intent intent = new Intent(ConsulterRendezVousAffecter.this, ConsulterDetailRendezVous.class);
        intent.putExtra("date", rendezVousClasse.getDateRDV());
        intent.putExtra("temp", rendezVousClasse.getTimeRDV());
        intent.putExtra("nom", rendezVousClasse.getNomRDV());
        intent.putExtra("lieu", rendezVousClasse.getLieuRDV());
        intent.putExtra("detail", rendezVousClasse.getNotesRDV());
        intent.putExtra("numTel", rendezVousClasse.getNumTelRDV());
        intent.putExtra("idRESIDENT", String.valueOf(rendezVousClasse.getIdResident()));
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrConsultRDVAffecterInfirmier) {
            finish();
        }
    }
}