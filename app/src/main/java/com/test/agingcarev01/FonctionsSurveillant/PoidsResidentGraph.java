package com.test.agingcarev01.FonctionsSurveillant;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.Calendar;

public class PoidsResidentGraph extends AppCompatActivity implements View.OnClickListener,
        AjouterPoidsCibleDialog.AjouterVideDialogListner, DatePickerDialog.OnDateSetListener{

    private TextView poidResCible,poidsRes,textViewPoids,textViewDatePoids,DatePoids;
    private Button ajouterNvPoidsBT,retourFrPoidsResBT;
    private ImageView editPoidResCibleBT,ajouterNvPoidsOKBT,ajouterNvPoidsAnnulerBT;

    private LineChart lineChart;

    private Float poidsCibleFloat;
    private String poidsChk="";
    private String keyResident,date="";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poids_resident_graph);

        poidResCible=findViewById(R.id.poidResCible);
        poidsRes=findViewById(R.id.poidsRes);
        DatePoids=findViewById(R.id.DatePoidsTxt);
        DatePoids.setOnClickListener(this);
        textViewPoids=findViewById(R.id.textViewPoids);
        textViewDatePoids=findViewById(R.id.textViewDatePoids);

        ajouterNvPoidsBT=findViewById(R.id.ajouterNvPoids);
        ajouterNvPoidsBT.setOnClickListener(this);
        retourFrPoidsResBT=findViewById(R.id.retourFrPoidsRes);
        retourFrPoidsResBT.setOnClickListener(this);
        ajouterNvPoidsOKBT=findViewById(R.id.ajouterNvPoidsOK);
        ajouterNvPoidsOKBT.setOnClickListener(this);
        ajouterNvPoidsAnnulerBT=findViewById(R.id.ajouterNvPoidsAnnuler);
        ajouterNvPoidsAnnulerBT.setOnClickListener(this);

        editPoidResCibleBT=findViewById(R.id.editPoidResCible);
        editPoidResCibleBT.setOnClickListener(this);

        //fetch Poids
        keyResident =getIntent().getStringExtra("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poids");

        lineChart = findViewById(R.id.lineChartPoids);
        hideDatePickerDailog();
        loadPoidCible();
        retrieveDataAndShowChart();
    }

    private void ShowAjouterPoids() {
        poidsRes.setVisibility(View.VISIBLE);
        DatePoids.setVisibility(View.VISIBLE);
        textViewPoids.setVisibility(View.VISIBLE);
        textViewDatePoids.setVisibility(View.VISIBLE);
        ajouterNvPoidsOKBT.setVisibility(View.VISIBLE);
        ajouterNvPoidsAnnulerBT.setVisibility(View.VISIBLE);
    }

    private void hideDatePickerDailog() {
        poidsRes.setVisibility(View.INVISIBLE);
        poidsRes.setText("");
        DatePoids.setVisibility(View.INVISIBLE);
        DatePoids.setText("AAAA/MM/JJ");
        date="";
        poidsChk="";
        textViewPoids.setVisibility(View.INVISIBLE);
        textViewDatePoids.setVisibility(View.INVISIBLE);
        ajouterNvPoidsOKBT.setVisibility(View.INVISIBLE);
        ajouterNvPoidsAnnulerBT.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ajouterNvPoids){
            ShowAjouterPoids();
        }
        if(view.getId()==R.id.retourFrPoidsRes){
            finish();
        }
        if(view.getId()==R.id.editPoidResCible){
            EditPoidCible();
        }
        if (view.getId()==R.id.ajouterNvPoidsOK){
            AjouterPoids();
        }
        if (view.getId()==R.id.DatePoidsTxt){
            showDatePickerDailog();
        }
        if (view.getId()==R.id.ajouterNvPoidsAnnuler){
            hideDatePickerDailog();
        }
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //janvier = month 0
        month++;
        String monthString = ""+month;
        String dayString = ""+day;

        if (month<10){
            monthString = "0"+month;
        }
        if (day<10){
            dayString = "0"+day;
        }
        date = year + "/" + monthString + "/" + dayString;
        DatePoids.setText(date);

    }

    private void showDatePickerDailog(){
        DatePickerDialog datePickerDialog =new DatePickerDialog(
                PoidsResidentGraph.this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void AjouterPoids() {
        poidsChk = poidsRes.getText().toString();
        if (poidsChk.isEmpty()) {
            Toast.makeText(PoidsResidentGraph.this, "Champ Poids Vide", Toast.LENGTH_SHORT).show();
        }else if (date.isEmpty()) {
            Toast.makeText(PoidsResidentGraph.this, "Champ Date Vide", Toast.LENGTH_SHORT).show();
        }  else{
            Float poids=Float.valueOf(poidsRes.getText().toString());
            if ( poids<=0) {
                Toast.makeText(PoidsResidentGraph.this, "Poids doit être supérieur à 0", Toast.LENGTH_SHORT).show();
            }else if ( poids>0 ) {
                PoidsClasse poidsClasse = new PoidsClasse(poids, date);
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Resident").child(keyResident).child("poids").push();
                myRef.setValue(poidsClasse);
                retrieveDataAndShowChart();
                hideDatePickerDailog();
            }
        }
    }

    private void retrieveDataAndShowChart() {
        //No Chart Data Text
        lineChart.setNoDataText("Graphe non disponible, veuillez entrer plus de données de poids");
        lineChart.setNoDataTextColor(Color.RED);

        Query query = databaseReference.orderByChild("datePoidRes");
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
                        float y_points =poidsClasse.getPoidsRes();
                        dataVals.add(new Entry(x_points,y_points));
//                        String dateString = (poidsClasse.getDatePoidRes()).substring(5);
                        xAxisLabel.add(poidsClasse.getDatePoidRes());
                    }

                    d1 = new LineDataSet(dataVals, null);
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
//                    lineChart.getXAxis().setLabelRotationAngle(90f);
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

    private void EditPoidCible() {
        AjouterPoidsCibleDialog ajouterMaladieDialog = new AjouterPoidsCibleDialog();
        ajouterMaladieDialog.show(getSupportFragmentManager(),"Nouveau Poids Ciblé");
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
                poidsCibleFloat = dataSnapshot.getValue(Float.class);
                if (poidsCibleFloat>0) {
                    poidResCible.setText(String.valueOf(poidsCibleFloat));
                    poidResCible.setTextColor(Color.BLACK);
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
                    poidResCible.setText("pas encore spécifié");
                    poidResCible.setTextColor(Color.RED);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
