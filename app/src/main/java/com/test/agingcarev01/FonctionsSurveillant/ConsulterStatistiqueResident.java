package com.test.agingcarev01.FonctionsSurveillant;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.PoidsClasse;
import com.test.agingcarev01.FonctionsSurveillant.Poids.AjouterPoidsCibleDialog;
import com.test.agingcarev01.R;

import java.util.ArrayList;

public class ConsulterStatistiqueResident extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        AjouterPoidsCibleDialog.AjouterVideDialogListner{
    private TextView cibleStatResNbr,cibleStatResNom;
    private ImageView ajouterFrStatRes,modifCibleRes;
    private static final String TAG = "ConsulterStatistiqueResident";
    private Button retourFrStatRes;

    private LineChart lineChart;
    private String keyResident;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_statistique_resident);

        cibleStatResNbr = findViewById(R.id.cibleStatResNbr);
        cibleStatResNom = findViewById(R.id.cibleStatResNom);
        lineChart = findViewById(R.id.lineChartStatistique);

        ajouterFrStatRes = findViewById(R.id.ajouterFrStatRes);
        ajouterFrStatRes.setOnClickListener(this);
        modifCibleRes = findViewById(R.id.modifCibleRes);
        retourFrStatRes = findViewById(R.id.retourFrStatRes);
        retourFrStatRes.setOnClickListener(this);

        Spinner spinnerSelectGrephe = (Spinner) findViewById(R.id.spinnerSelectGrephe);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.typeGraphe,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerSelectGrephe.setAdapter(staticAdapter);
        spinnerSelectGrephe.setOnItemSelectedListener(this);


        //fetch Key Resident
        keyResident =getIntent().getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrStatRes){
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                cibleStatResNom.setText("Poids");
                retrievePoidsAndShowChart();
                loadPoidCible();
                modifCibleRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AjouterPoidsCibleDialog ajouterMaladieDialog = new AjouterPoidsCibleDialog();
                        ajouterMaladieDialog.show(getSupportFragmentManager(),"Nouveau Poids Ciblé");
                    }
                });
                break;
            case 1:
                cibleStatResNom.setText("Taux Glycémique");
//                databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemique");
                break;
            case 2:
                cibleStatResNom.setText("Tension Artérielle");
//                databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tensionArterielle\"");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void retrievePoidsAndShowChart() {
        //No Chart Data Text
        lineChart.setNoDataText("Graphe non disponible, veuillez entrer plus de données de poids");
        lineChart.setNoDataTextColor(Color.RED);

        Query query = databaseReference.child("poids").orderByChild("datePoidRes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                ArrayList xAxisLabel = new ArrayList<>();
                LineDataSet d1;
                int i=0;
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()){
                        PoidsClasse poidsClasse =myDataSnapshot.getValue(PoidsClasse.class);
                        float x_points = i;
                        i++;
                        float y_points = poidsClasse.getPoidsRes();
                        dataVals.add(new Entry(x_points,y_points));
                        //String dateString = (poidsClasse.getDatePoidRes()).substring(5);
                        xAxisLabel.add(poidsClasse.getDatePoidRes());
                    }
                    d1 = new LineDataSet(dataVals, null);

                    chartCustomization(d1);

                    LineData lineData = new LineData(d1);
                    lineChart.setData(lineData);
                    lineChart.setVisibleXRangeMaximum(5);
                    lineChart.setDragEnabled(true);
                    lineChart.setScaleEnabled(false);
                    lineChart.getXAxis().setLabelCount(i,false);
                    lineChart.getXAxis().setGranularityEnabled(true);
                    lineChart.getXAxis().setGranularity(1.0f);
                    lineChart.getXAxis().setTextSize(8);
                    lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    //lineChart.getXAxis().setLabelRotationAngle(90f);
                    lineChart.getDescription().setEnabled(false);
                    lineChart.getLegend().setEnabled(false);
                    lineChart.getAxisRight().setDrawLabels(true);
                    lineChart.setBackgroundColor(Color.WHITE);
                    lineChart.setGridBackgroundColor(R.color.colorGray);
                    lineChart.setBorderColor(Color.BLACK);
                    lineChart.setBorderWidth(10f);
//                    lineChart.getXAxis().setAvoidFirstLastClipping(true);
                    lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

                    // do not forget to refresh the chart
                    lineChart.invalidate();
                    //lineChart.animateX(750);
                }else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void chartCustomization(LineDataSet d1) {
        //Chart Lines Custumization
        d1.setLineWidth(1.5f);
        d1.setColor(Color.BLACK);
        //Chart Values Custumization
        d1.setDrawValues(true);
        d1.setValueTextSize(10f);
        d1.setValueTextColor(Color.BLACK);
        //Chart Circles Custumization
        d1.setCircleRadius(2.5f);
        d1.setCircleColor(Color.BLACK);
        d1.setDrawCircleHole(false);
        //Chart Highlight Custumization
        d1.setHighlightEnabled(true);
        d1.setHighLightColor(R.color.colorBlack);
        //Chart Filled Custumization
        d1.setDrawFilled(true);
        d1.setFillColor(Color.BLUE);
        d1.setFillAlpha(50);
    }

    @Override
    public void applyNvPoidsCible(Float poidsCible) {
        FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poidsCible").setValue(poidsCible);
        loadPoidCible();
    }
    private void loadPoidCible() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poidsCible");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float poidsCibleFloat = dataSnapshot.getValue(Float.class);
                if (poidsCibleFloat>0) {
                    cibleStatResNbr.setText(String.valueOf(poidsCibleFloat));
                    cibleStatResNbr.setTextColor(Color.BLACK);
                    //initialize Lower Limit
                    LimitLine lower_limit = new LimitLine(poidsCibleFloat,"Poids Ciblé");
                    lower_limit.setLineWidth(2f);
                    lower_limit.enableDashedLine(10f,10f,0);
                    lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                    lower_limit.setTextSize(5f);
                    lower_limit.setTextColor(Color.RED);

                    //Add Limits
                    YAxis leftAxs = lineChart.getAxisLeft();
                    leftAxs.removeAllLimitLines();
                    leftAxs.addLimitLine(lower_limit);
                    leftAxs.setDrawLimitLinesBehindData(true);
                }else {
                    cibleStatResNbr.setText("pas encore spécifié");
                    cibleStatResNbr.setTextColor(Color.RED);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
