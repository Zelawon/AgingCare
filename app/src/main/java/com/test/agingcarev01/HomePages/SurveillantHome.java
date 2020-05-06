package com.test.agingcarev01.HomePages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsCommunes.ConsulterProfil;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

public class SurveillantHome extends AppCompatActivity implements View.OnClickListener {
    private Button consulterProfSurvBT,deconnecterSurveillantBT;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveillant_home);

        mAuth = FirebaseAuth.getInstance();

        consulterProfSurvBT=findViewById(R.id.consulterProfSurv);
        consulterProfSurvBT.setOnClickListener(this);

        deconnecterSurveillantBT=findViewById(R.id.deconnecterSurveillant);
        deconnecterSurveillantBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.consulterProfSurv){
            startActivity(new Intent(SurveillantHome.this, ConsulterProfil.class));
        }
        if(view.getId()==R.id.deconnecterSurveillant){
            mAuth.signOut();
            startActivity(new Intent(SurveillantHome.this, MainActivity.class));
            finishAffinity ();
        }
    }
}
