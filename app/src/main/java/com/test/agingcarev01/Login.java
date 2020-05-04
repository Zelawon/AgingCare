package com.test.agingcarev01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        //if email existe dans database affiche le home correspendent.
        startActivity(new Intent(Login.this, AdminHome.class));
        finish();



//        //final Firebase ref = new Firebase("https://test.firebaseio.com/users");
//        //Query query = ref.orderByChild("username").equalTo("toto");
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//
//        DatabaseReference RootReference = firebaseDatabase.getReference("Employee");
//        String currentUserMail = user.getEmail();
//        Query query =RootReference.orderByChild("email").equalTo(currentUserMail);
//        //Toast.makeText(getApplicationContext(),currentUserMail,Toast.LENGTH_SHORT).show();
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //Toast.makeText(getApplicationContext(),"inside DataSnapshot",Toast.LENGTH_SHORT).show();
//                if (dataSnapshot.exists()) {
//                    // dataSnapshot is the node with all children with email= useremail
//                    Toast.makeText(getApplicationContext(),"inside DataSnapshot Exist",Toast.LENGTH_SHORT).show();
//                    for (DataSnapshot emailList : dataSnapshot.getChildren()) {
//                        // do something with the individual nodes?
//
//                        String roleUser = emailList.child("role").getValue(String.class);
//                        Toast.makeText(getApplicationContext(),roleUser,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"www",Toast.LENGTH_SHORT).show();
//
//                        //Case????
////                        if (Role = "Directeur"){
////                            startActivity(??);
////                        }else if( = "surveillant"){
////                             startActivity(??);
////                        }else if ( = "infirmier"){
////
////                        }else {
////                             7kaya
////                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
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
