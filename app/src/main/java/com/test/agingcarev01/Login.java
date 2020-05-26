package com.test.agingcarev01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.test.agingcarev01.HomePages.HomeAdmin;
import com.test.agingcarev01.HomePages.HomeDirectuer;
import com.test.agingcarev01.HomePages.HomeInfirmier;
import com.test.agingcarev01.HomePages.HomeSurveillant;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button logInBT, retourBT;
    private EditText email,mdp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInBT=findViewById(R.id.SignIn);
        retourBT =findViewById(R.id.retourLogin);
        email=findViewById(R.id.emaillog);
        mdp=findViewById(R.id.passDirec);

        mAuth = FirebaseAuth.getInstance();

        logInBT.setOnClickListener(this);
        retourBT.setOnClickListener(this);
    }
    private void singIn(final String mail, final String password) {
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    updateUI();
                }
                else{
                    Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateUI() {
        //if email existe dans database affiche le home correspendent au role du user.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot roleSnapshot : dataSnapshot.getChildren()) {
                        String currentRole = roleSnapshot.child("role").getValue(String.class);
                        String currentstatutEtRole = roleSnapshot.child("statutEtRole").getValue(String.class);
                        if (currentRole.equals("Admin")) {
                            Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeAdmin.class));
                            finishAffinity();
                        } else if (currentstatutEtRole.equals("Directeur_0")) {
                            Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeDirectuer.class));
                            finishAffinity();
                        }else if (currentstatutEtRole.equals("Surveillant_0")) {
                            Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeSurveillant.class));
                            finishAffinity();
                        }else if (currentstatutEtRole.equals("Infirmier_0")) {
                            Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeInfirmier.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Connection Impossible, Compte Archivee", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Erreur Login", Toast.LENGTH_LONG).show();
                    Log.e("TAG Erreur : ", "Login No Email");
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void onClick(View view) {
        String password = mdp.getText().toString();
        String emails = email.getText().toString();
        if(view.getId()==R.id.SignIn){
            if (emails.isEmpty()){
                Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }else if (password.isEmpty()){
                Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT).show();
                mdp.requestFocus();
            }else {
                singIn(emails,password);
            }
        }
        if (view.getId()==R.id.retourLogin){
            finish();
        }
    }
}
