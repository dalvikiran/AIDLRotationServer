package com.kiran.aidlrotationserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    IAIDLRotationInterface rotationService;
    IAIDLRotationCallback rotationCallback;

    TextView pitchTextView;
    TextView rollTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pitchTextView = (TextView) findViewById(R.id.pitchTextView);
        rollTextView = (TextView) findViewById(R.id.rollTextView);

        bindToALService();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (rotationService != null)
            getStock(rollTextView);
    }

    void bindToALService(){
        Intent intent = new Intent(MainActivity.this, MyRotationService.class);

        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            rotationService = IAIDLRotationInterface.Stub.asInterface(service);
            getStock(rollTextView);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void getStock(View view){

        try {
            rotationCallback = new IAIDLRotationCallback() {

                @Override
                public void getRotationDetails(OrientationDetails orientation) throws RemoteException {
                    pitchTextView.setText(orientation.getPitch());
                    rollTextView.setText(orientation.getRoll());

                    Log.e("Coordinates", orientation.getPitch());
                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            };

            rotationService.getCoordinates(rotationCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            rotationService.stopCoordinates(rotationCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}