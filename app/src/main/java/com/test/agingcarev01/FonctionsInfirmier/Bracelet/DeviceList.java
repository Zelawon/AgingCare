package com.test.agingcarev01.FonctionsInfirmier.Bracelet;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.agingcarev01.R;

import java.util.ArrayList;
import java.util.Set;

import es.dmoral.toasty.Toasty;

public class DeviceList extends AppCompatActivity {

    public static String EXTRA_ADDRESS = "device_address";
    // Widgets
    Button btnPaired;
    ListView devicelist;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    // When a paired device is clicked
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // MAC address are last 17 characters of the textview clicked
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent i = new Intent(DeviceList.this, ReceiverRythmeCardiaque.class);

            // Send to next activity the MAC address of the chosen device
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        // Initialize widgets
        btnPaired = (Button) findViewById(R.id.paired_dev_btn);
        devicelist = (ListView) findViewById(R.id.paired_dev_listview);

        // Initialize bluetooth adapter
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) { // This device has not bluetooth adapter
            Toasty.error(getApplicationContext(), "Adaptateur Bluetooth non disponible", Toast.LENGTH_LONG).show();
            finish();
        } else if (!myBluetooth.isEnabled()) { // This device has bluetooth adapter but turned off
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1); // Intent to turn on bluetooth adapter
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });

    }

    // List with paired bluetooth devices
    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) // ArrayList with name and MAC address of paired devices
            for (BluetoothDevice bt : pairedDevices)
                list.add(bt.getName() + "\n" + bt.getAddress());
        else
            Toasty.info(getApplicationContext(), "Aucun périphérique Bluetooth couplé trouvé.", Toast.LENGTH_LONG).show();


        // Display paired devices in the listview
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener);

    }

}