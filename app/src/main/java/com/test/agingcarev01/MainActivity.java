package com.test.agingcarev01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.test.agingcarev01.FonctionsAdmin.TestCompteActive;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBT,quitterBT,logoutBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth.getInstance().signOut();

        loginBT=findViewById(R.id.login);
        loginBT.setOnClickListener(this);
        quitterBT=findViewById(R.id.quit);
        quitterBT.setOnClickListener(this);
        logoutBT=findViewById(R.id.testCompActive);
        logoutBT.setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.login){
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        if(view.getId()==R.id.quit){
            finish();
        }
        if(view.getId()==R.id.testCompActive){
            startActivity(new Intent(MainActivity.this, TestCompteActive.class));
        }
    }
}
