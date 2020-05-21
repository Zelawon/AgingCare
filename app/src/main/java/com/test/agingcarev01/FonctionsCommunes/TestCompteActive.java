package com.test.agingcarev01.FonctionsCommunes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.agingcarev01.R;

public class TestCompteActive extends AppCompatActivity {
    TextView txt1,txt2,txt3,txt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_compte_active);

        txt1=findViewById(R.id.d1);
        txt2=findViewById(R.id.d2);
        txt3=findViewById(R.id.d3);
        txt4=findViewById(R.id.d4);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            txt1.setText(name);
            txt2.setText(email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            txt3.setText(String.valueOf(emailVerified));

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            txt4.setText(uid);
        }
    }
}
