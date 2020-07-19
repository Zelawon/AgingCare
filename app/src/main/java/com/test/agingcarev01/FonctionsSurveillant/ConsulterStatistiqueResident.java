package com.test.agingcarev01.FonctionsSurveillant;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.Classe.PoidsClasse;
import com.test.agingcarev01.Classe.TauxGlycemiqueClasse;
import com.test.agingcarev01.Classe.TensionArterielleClasse;
import com.test.agingcarev01.FonctionsSurveillant.Poids.AjouterPoidsCibleDialog;
import com.test.agingcarev01.FonctionsSurveillant.TauxGlycemie.AjouterTauxGlycemiqueCibleDialog;
import com.test.agingcarev01.FonctionsSurveillant.TensionArterielle.AjouterPressionDiastoliqueCibleDialog;
import com.test.agingcarev01.FonctionsSurveillant.TensionArterielle.AjouterPressionSystoliqueCibleDialog;
import com.test.agingcarev01.R;

import java.util.ArrayList;

public class ConsulterStatistiqueResident extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        AjouterPoidsCibleDialog.AjouterVideDialogListner, AjouterTauxGlycemiqueCibleDialog.AjouterTauxGlycemiqueDialogListner,
        AjouterPressionDiastoliqueCibleDialog.AjouterPressionDiastoliqueCibleDialogListner,
        AjouterPressionSystoliqueCibleDialog.AjouterPressionSystoliqueCibleDialogListner {

    private static final String TAG = "ConsulterStatistiqueResident";
    private TextView cibleStatResNbr, cibleStatResNom, cibleStatResNom2, cibleStatResNbr2, cibleText, cibleText2;
    private ImageView modifCibleRes, modifCibleRes2, retourFrStatRes;
    private LineChart lineChart1, lineChart2, lineChart3;
    private String keyResident;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_statistique_resident);

        cibleStatResNbr = findViewById(R.id.cibleStatResNbr);
        cibleStatResNom = findViewById(R.id.cibleStatResNom);
        cibleStatResNbr2 = findViewById(R.id.cibleStatResNbr2);
        cibleStatResNom2 = findViewById(R.id.cibleStatResNom2);
        cibleText = findViewById(R.id.cibleText);
        cibleText2 = findViewById(R.id.cibleText2);
        lineChart1 = findViewById(R.id.lineChartStatistique);
        lineChart2 = findViewById(R.id.lineChartStatistique);
        lineChart3 = findViewById(R.id.lineChartStatistique);

        modifCibleRes = findViewById(R.id.modifCibleRes);
        modifCibleRes2 = findViewById(R.id.modifCibleRes2);
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
        keyResident = getIntent().getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retourFrStatRes) {
            finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                hideCile2();
                cibleStatResNom.setText("Poids");
                retrievePoidsAndShowChart();
                loadPoidCible();
                modifCibleRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AjouterPoidsCibleDialog ajouterMaladieDialog = new AjouterPoidsCibleDialog();
                        ajouterMaladieDialog.show(getSupportFragmentManager(), "Nouveau Poids Ciblé");
                    }
                });
                break;
            case 1:
                hideCile2();
                cibleStatResNom.setText("Taux Glycémique");
                retrieveTauxGlycemiqueAndShowChart();
                loadTauxGlycemiqueCible();
                modifCibleRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AjouterTauxGlycemiqueCibleDialog ajouterTauxGlycemiqueCibleDialog = new AjouterTauxGlycemiqueCibleDialog();
                        ajouterTauxGlycemiqueCibleDialog.show(getSupportFragmentManager(), "Nouveau Taux Glycemique Ciblé");
                    }
                });
                break;
            case 2:
                showCible2();
                retrieveTensionArterielleAndShowChart();
                loadTensionArterielleCible();
                modifCibleRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AjouterPressionDiastoliqueCibleDialog ajouterPressionDiastoliqueCibleDialog = new AjouterPressionDiastoliqueCibleDialog();
                        ajouterPressionDiastoliqueCibleDialog.show(getSupportFragmentManager(), "Nouveau Pression Diastolique Ciblé");
                    }
                });
                modifCibleRes2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AjouterPressionSystoliqueCibleDialog ajouterPressionSystoliqueCibleDialog = new AjouterPressionSystoliqueCibleDialog();
                        ajouterPressionSystoliqueCibleDialog.show(getSupportFragmentManager(), "Nouveau Pression Systolique Ciblé");
                    }
                });
                break;
        }
    }

    private void hideCile2() {
        cibleStatResNom2.setVisibility(View.INVISIBLE);
        cibleStatResNbr2.setVisibility(View.INVISIBLE);
        modifCibleRes2.setVisibility(View.INVISIBLE);
        cibleText.setVisibility(View.INVISIBLE);
        cibleText2.setVisibility(View.INVISIBLE);
    }

    private void showCible2() {
        cibleStatResNom2.setVisibility(View.VISIBLE);
        cibleStatResNbr2.setVisibility(View.VISIBLE);
        modifCibleRes2.setVisibility(View.VISIBLE);
        cibleText.setVisibility(View.VISIBLE);
        cibleText2.setVisibility(View.VISIBLE);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
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

    private void invalidateAndClearChart(LineChart lineChart) {
        lineChart.clear();
        lineChart.invalidate();
    }

    private void retrievePoidsAndShowChart() {
        invalidateAndClearChart(lineChart2);
        invalidateAndClearChart(lineChart3);
        //No Chart Data Text
        lineChart1.setNoDataText("Veuillez entrer plus de données de poids");
        lineChart1.setNoDataTextColor(Color.RED);

        Query query = databaseReference.child("poids").orderByChild("datePoidRes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                ArrayList xAxisLabel = new ArrayList<>();
                LineDataSet d1;
                int i = 0;
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                        PoidsClasse poidsClasse = myDataSnapshot.getValue(PoidsClasse.class);
                        float x_points = i;
                        i++;
                        float y_points = poidsClasse.getPoidsRes();
                        dataVals.add(new Entry(x_points, y_points));
                        //String dateString = (poidsClasse.getDatePoidRes()).substring(5);
                        xAxisLabel.add(poidsClasse.getDatePoidRes());
                    }
                    d1 = new LineDataSet(dataVals, null);

                    chartCustomization(d1);

                    LineData lineData = new LineData(d1);
                    lineChart1.setData(lineData);
                    lineChart1.setVisibleXRangeMaximum(5);
                    lineChart1.setDragEnabled(true);
                    lineChart1.setScaleEnabled(false);
                    lineChart1.getXAxis().setLabelCount(i, false);
                    lineChart1.getXAxis().setGranularityEnabled(true);
                    lineChart1.getXAxis().setGranularity(1.0f);
                    lineChart1.getXAxis().setTextSize(8);
                    lineChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    //lineChart1.getXAxis().setLabelRotationAngle(90f);
                    lineChart1.getDescription().setEnabled(false);
                    lineChart1.getLegend().setEnabled(false);
                    lineChart1.getAxisRight().setDrawLabels(true);
                    lineChart1.setBackgroundColor(Color.WHITE);
                    lineChart1.setGridBackgroundColor(R.color.colorGray);
                    lineChart1.setBorderColor(Color.BLACK);
                    lineChart1.setBorderWidth(10f);
                    //lineChart1.getXAxis().setAvoidFirstLastClipping(true);
                    lineChart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

                    // do not forget to refresh the chart
                    lineChart1.invalidate();
                    //lineChart1.animateX(750);
                } else {
                    invalidateAndClearChart(lineChart1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveTauxGlycemiqueAndShowChart() {
        invalidateAndClearChart(lineChart1);
        invalidateAndClearChart(lineChart3);
        //No Chart Data Text
        lineChart2.setNoDataText("Veuillez entrer plus de données de taux glycemique");
        lineChart2.setNoDataTextColor(Color.RED);

        Query query = databaseReference.child("tauxGlycemique").orderByChild("dateTauxGlyceRes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
                ArrayList xAxisLabel = new ArrayList<>();
                LineDataSet d1;
                int i = 0;
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                        TauxGlycemiqueClasse tauxGlycemiqueClasse = myDataSnapshot.getValue(TauxGlycemiqueClasse.class);
                        float x_points = i;
                        i++;
                        float y_points = tauxGlycemiqueClasse.getTauxGlyceRes();
                        dataVals.add(new Entry(x_points, y_points));
                        //String dateString = (poidsClasse.getDatePoidRes()).substring(5);
                        xAxisLabel.add(tauxGlycemiqueClasse.getDateTauxGlyceRes());
                    }
                    d1 = new LineDataSet(dataVals, null);

                    chartCustomization(d1);

                    LineData lineData = new LineData(d1);
                    lineChart2.setData(lineData);
                    lineChart2.setVisibleXRangeMaximum(5);
                    lineChart2.setDragEnabled(true);
                    lineChart2.setScaleEnabled(false);
                    lineChart2.getXAxis().setLabelCount(i, false);
                    lineChart2.getXAxis().setGranularityEnabled(true);
                    lineChart2.getXAxis().setGranularity(1.0f);
                    lineChart2.getXAxis().setTextSize(8);
                    lineChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    //lineChart2.getXAxis().setLabelRotationAngle(90f);
                    lineChart2.getDescription().setEnabled(false);
                    lineChart2.getLegend().setEnabled(false);
                    lineChart2.getAxisRight().setDrawLabels(true);
                    lineChart2.setBackgroundColor(Color.WHITE);
                    lineChart2.setGridBackgroundColor(R.color.colorGray);
                    lineChart2.setBorderColor(Color.BLACK);
                    lineChart2.setBorderWidth(10f);
                    //lineChart2.getXAxis().setAvoidFirstLastClipping(true);
                    lineChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

                    // do not forget to refresh the chart
                    lineChart2.invalidate();
                    //lineChart2.animateX(750);
                } else {
                    invalidateAndClearChart(lineChart2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveTensionArterielleAndShowChart() {
        invalidateAndClearChart(lineChart2);
        invalidateAndClearChart(lineChart1);
        //No Chart Data Text
        lineChart3.setNoDataText("Veuillez entrer plus de données de tension arterielle");
        lineChart3.setNoDataTextColor(Color.RED);

        Query query = databaseReference.child("tensionArterielle").orderByChild("dateTensionArterielle");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataValsPresSystolique = new ArrayList<Entry>();
                ArrayList<Entry> dataValsPresDiatolique = new ArrayList<Entry>();
                ArrayList xAxisLabel = new ArrayList<>();
                LineDataSet d1, d2;
                int i = 0;
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren()) {
                        TensionArterielleClasse tensionArterielleClasse = myDataSnapshot.getValue(TensionArterielleClasse.class);
                        float x_points = i;
                        i++;
                        float y_points = tensionArterielleClasse.getPressionSystolique();
                        float z_points = tensionArterielleClasse.getPreessionDiatolique();
                        dataValsPresSystolique.add(new Entry(x_points, y_points));
                        dataValsPresDiatolique.add(new Entry(x_points, z_points));
                        //String dateString = (poidsClasse.getDatePoidRes()).substring(5);
                        xAxisLabel.add(tensionArterielleClasse.getDateTensionArterielle());
                    }
                    d1 = new LineDataSet(dataValsPresSystolique, "Pression Systolique");
                    d2 = new LineDataSet(dataValsPresDiatolique, "Pression Diatolique");


                    //Chart Lines Custumization
                    d1.setLineWidth(1.5f);
                    d1.setColor(Color.GREEN);
                    //Chart Values Custumization
                    d1.setDrawValues(true);
                    d1.setValueTextSize(10f);
                    d1.setValueTextColor(Color.GREEN);
                    //Chart Circles Custumization
                    d1.setCircleRadius(2.5f);
                    d1.setCircleColor(Color.GREEN);
                    d1.setDrawCircleHole(false);
                    //Chart Highlight Custumization
                    d1.setHighlightEnabled(true);
                    d1.setHighLightColor(R.color.colorBlack);

                    //Chart Lines Custumization
                    d2.setLineWidth(1.5f);
                    d2.setColor(Color.BLUE);
                    //Chart Values Custumization
                    d2.setDrawValues(true);
                    d2.setValueTextSize(10f);
                    d2.setValueTextColor(Color.BLUE);
                    //Chart Circles Custumization
                    d2.setCircleRadius(2.5f);
                    d2.setCircleColor(Color.BLUE);
                    d2.setDrawCircleHole(false);
                    //Chart Highlight Custumization
                    d2.setHighlightEnabled(true);
                    d2.setHighLightColor(R.color.colorBlack);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(d1);
                    dataSets.add(d2);

                    LineData lineData = new LineData(dataSets);
                    lineChart3.setData(lineData);
                    lineChart3.setVisibleXRangeMaximum(5);
                    lineChart3.setDragEnabled(true);
                    lineChart3.setScaleEnabled(false);
                    lineChart3.getXAxis().setLabelCount(i, false);
                    lineChart3.getXAxis().setGranularityEnabled(true);
                    lineChart3.getXAxis().setGranularity(1.0f);
                    lineChart3.getXAxis().setTextSize(8);
                    lineChart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    //lineChart3.getXAxis().setLabelRotationAngle(90f);
                    lineChart3.getDescription().setEnabled(false);
                    lineChart3.getLegend().setEnabled(true);
                    lineChart3.getAxisRight().setDrawLabels(true);
                    lineChart3.setBackgroundColor(Color.WHITE);
                    lineChart3.setGridBackgroundColor(R.color.colorGray);
                    lineChart3.setBorderColor(Color.BLACK);
                    lineChart3.setBorderWidth(10f);
                    //lineChart3.getXAxis().setAvoidFirstLastClipping(true);
                    lineChart3.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

                    // do not forget to refresh the chart
                    lineChart3.invalidate();
                    //lineChart3.animateX(750);
                } else {
                    invalidateAndClearChart(lineChart3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void applyNvTauxGlycemiqueCible(Float tauxCible) {
        FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemiqueCible").setValue(tauxCible);
        loadTauxGlycemiqueCible();
    }

    private void loadTauxGlycemiqueCible() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("tauxGlycemiqueCible");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float tauxCible = dataSnapshot.getValue(Float.class);
                if (tauxCible > 0) {
                    cibleStatResNbr.setText(String.valueOf(tauxCible));
                    cibleStatResNbr.setTextColor(Color.BLACK);
                    //initialize Lower Limit
                    LimitLine lower_limit = new LimitLine(tauxCible, "Taux Glycemique Ciblé");
                    lower_limit.setLineWidth(2f);
                    lower_limit.enableDashedLine(10f, 10f, 0);
                    lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                    lower_limit.setTextSize(5f);
                    lower_limit.setTextColor(Color.RED);

                    //Add Limits
                    YAxis leftAxs = lineChart2.getAxisLeft();
                    leftAxs.removeAllLimitLines();
                    leftAxs.addLimitLine(lower_limit);
                    leftAxs.setDrawLimitLinesBehindData(true);
                } else {
                    cibleStatResNbr.setText("pas encore spécifié");
                    cibleStatResNbr.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                if (poidsCibleFloat > 0) {
                    cibleStatResNbr.setText(String.valueOf(poidsCibleFloat));
                    cibleStatResNbr.setTextColor(Color.BLACK);
                    //initialize Lower Limit
                    LimitLine lower_limit = new LimitLine(poidsCibleFloat, "Poids Ciblé");
                    lower_limit.setLineWidth(2f);
                    lower_limit.enableDashedLine(10f, 10f, 0);
                    lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                    lower_limit.setTextSize(5f);
                    lower_limit.setTextColor(Color.RED);

                    //Add Limits
                    YAxis leftAxs = lineChart1.getAxisLeft();
                    leftAxs.removeAllLimitLines();
                    leftAxs.addLimitLine(lower_limit);
                    leftAxs.setDrawLimitLinesBehindData(true);
                } else {
                    cibleStatResNbr.setText("pas encore spécifié");
                    cibleStatResNbr.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void applyNvPressionDiastoliqueCible(Float tensionCible) {
        FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("pressionDiastoliqueCible").setValue(tensionCible);
        loadTensionArterielleCible();
    }

    @Override
    public void applyNvPressionSystoliqueCible(Float tensionCible) {
        FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("pressionSystoliqueCible").setValue(tensionCible);
        loadTensionArterielleCible();
    }

    private void loadTensionArterielleCible() {
        //Add Limits
        final YAxis leftAxs = lineChart3.getAxisLeft();
        leftAxs.removeAllLimitLines();
        leftAxs.setDrawLimitLinesBehindData(true);

        //Pression Diastonique
        cibleStatResNom.setText("Pression Diastolique");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("pressionDiastoliqueCible");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float tensionCible = dataSnapshot.getValue(Float.class);
                if (tensionCible > 0) {
                    cibleStatResNbr.setText(String.valueOf(tensionCible));
                    cibleStatResNbr.setTextColor(Color.BLACK);
                    //initialize Lower Limit
                    LimitLine lower_limit2 = new LimitLine(tensionCible, "Pression Diastolique Ciblé");
                    lower_limit2.setLineWidth(2f);
                    lower_limit2.enableDashedLine(10f, 10f, 0);
                    lower_limit2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                    lower_limit2.setTextSize(5f);
                    lower_limit2.setTextColor(Color.BLUE);
                    lower_limit2.setLineColor(Color.BLUE);
                    leftAxs.addLimitLine(lower_limit2);
                } else {
                    cibleStatResNbr.setText("pas encore spécifié");
                    cibleStatResNbr.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //Pression Systonique
        cibleStatResNom2.setText("Pression Systolique");
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("pressionSystoliqueCible");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Float tesionCible2 = dataSnapshot.getValue(Float.class);
                if (tesionCible2 > 0) {
                    cibleStatResNbr2.setText(String.valueOf(tesionCible2));
                    cibleStatResNbr2.setTextColor(Color.BLACK);
                    //initialize Lower Limit
                    LimitLine lower_limit = new LimitLine(tesionCible2, "Pression Systolique Ciblé");
                    lower_limit.setLineWidth(2f);
                    lower_limit.enableDashedLine(10f, 10f, 0);
                    lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                    lower_limit.setTextSize(5f);
                    lower_limit.setLineColor(Color.GREEN);
                    lower_limit.setTextColor(Color.GREEN);
                    leftAxs.addLimitLine(lower_limit);
                } else {
                    cibleStatResNbr2.setText("pas encore spécifié");
                    cibleStatResNbr2.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
