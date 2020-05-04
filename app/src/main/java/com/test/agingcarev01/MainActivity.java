package com.test.agingcarev01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.AdminFonction.CreerCompteDirecteur;
import com.test.agingcarev01.AdminFonction.testApresLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBT,creeBT,quitterBT,logoutBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth.getInstance().signOut();

        creeBT=findViewById(R.id.creeAd);
        creeBT.setOnClickListener(this);
        loginBT=findViewById(R.id.login);
        loginBT.setOnClickListener(this);
        quitterBT=findViewById(R.id.quit);
        quitterBT.setOnClickListener(this);
        logoutBT=findViewById(R.id.logoutTest);
        logoutBT.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login){
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
        if(view.getId()==R.id.creeAd){
            startActivity(new Intent(MainActivity.this, CreerCompteDirecteur.class));
            finish();
        }
        if(view.getId()==R.id.quit){
            finish();
        }
        if(view.getId()==R.id.logoutTest){
            startActivity(new Intent(MainActivity.this, testApresLogin.class));
            finish();
        }
    }
}
