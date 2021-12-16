package loniya.org.bleadvertising;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private BackgroundPowerSaver backgroundPowerSaver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch s = (Switch) findViewById(R.id.start);
        String uniqueNamespace = Util.generateNamespace();
        String fixedNamespace = "60c0bf209a4100000000";

        // Création d'un beacon virtuel
        final Beacon beacon = new Beacon.Builder()
                .setId1("0x"+fixedNamespace)
                .setId2("0x0000000000")
                .setTxPower(-18)
                .setDataFields(Arrays.asList(new Long[] {0l}))
                .setBluetoothName("Matchmore")
                .setManufacturer(29)
                .build();

        Log.d("Matchmore", fixedNamespace);

        TextView namespace = findViewById(R.id.namespace);
        namespace.setText(fixedNamespace);

        // formatage des trames au format Eddystone UID
        BeaconParser beaconParser = new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19");
        //BeaconParser beaconParser = new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT);
        final BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getApplicationContext(), "Broadcast is on", Toast.LENGTH_LONG).show();
                    beaconTransmitter.startAdvertising(beacon);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Broadcast is off", Toast.LENGTH_LONG).show();
                    beaconTransmitter.stopAdvertising();
                }
            }
        });



    }
}

