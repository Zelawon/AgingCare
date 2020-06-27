package com.test.agingcarev01.FonctionsInfirmier.Bracelet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.test.agingcarev01.R;

public class ChoixCaptuer extends AppCompatActivity {

    public static String EXTRA_ADDRESS = "device_address";
    // Widgets
    public Button btnSendData, btnReceiveData;
    public String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newInt = getIntent();
        address = newInt.getStringExtra(DeviceList.EXTRA_ADDRESS); // Get the MAC address

        setContentView(R.layout.activity_choix_captuer);

        btnSendData = (Button) findViewById(R.id.BTN_receiveRytmeCardiaque);
        btnReceiveData = (Button) findViewById(R.id.BTN_receiveTemperature);

        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoixCaptuer.this, ReceiverRythmeCardiaque.class);

                i.putExtra(EXTRA_ADDRESS, address); // This will be received at PinControl (class) Activity
                startActivity(i);
            }
        });

        btnReceiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChoixCaptuer.this, ReceiverTemperature.class);

                i.putExtra(EXTRA_ADDRESS, address); // This will be received at Receiver (class) Activity
                startActivity(i);
            }
        });

    }

}
