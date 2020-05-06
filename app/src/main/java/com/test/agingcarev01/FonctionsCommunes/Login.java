package com.test.agingcarev01.FonctionsCommunes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.test.agingcarev01.HomePages.AdminHome;
import com.test.agingcarev01.HomePages.DirectuerHome;
import com.test.agingcarev01.HomePages.InfirmierHome;
import com.test.agingcarev01.HomePages.SurveillantHome;
import com.test.agingcarev01.MainActivity;
import com.test.agingcarev01.R;

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
                    //Toast.makeText(Login.this, "Connection Réussite.", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
                else{
                    if (mail.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Email vide", Toast.LENGTH_SHORT).show();
                    }else if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Mot de Passe vide", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void updateUI() {
//        //if email existe dans database affiche le home correspendent au role du user.

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();
        //Toast.makeText(getApplicationContext(), currentUserEmail, Toast.LENGTH_LONG).show();

        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReference("Employee");
        Query emailQuery = employeeRef.orderByChild("email").equalTo(currentUserEmail);
        emailQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot roleSnapshot : dataSnapshot.getChildren()) {
                        //Log.i("TAG", roleSnapshot.child("role").getValue(String.class));
                        //Toast.makeText(getApplicationContext(), roleSnapshot.child("role").getValue(String.class), Toast.LENGTH_LONG).show();
                        String currentRole = roleSnapshot.child("role").getValue(String.class);
                        if (currentRole.equals("Admin")) {
                            //Toast.makeText(getApplicationContext(), "Role: Admin", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, AdminHome.class));
                            finish();
                        } else if (currentRole.equals("Directeur")) {
                            //Toast.makeText(getApplicationContext(), "Role: Directeur", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, DirectuerHome.class));
                            finish();
                        }else if (currentRole.equals("Surveillant")) {
                            //Toast.makeText(getApplicationContext(), "Role: Surveillant", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, SurveillantHome.class));
                            finish();
                        }else if (currentRole.equals("Infirmier")) {
                            //Toast.makeText(getApplicationContext(), "Role: Infirmier", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, InfirmierHome.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Role Assigned", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Account Not Found", Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }
}
