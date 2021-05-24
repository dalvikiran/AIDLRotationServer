package com.kiran.aidlrotationserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;

public class MyRotationService extends Service implements Orientation.Listener {

    private ArrayList<IAIDLRotationCallback> mRemoteCallbacks;
    private Orientation mOrientation;

    public MyRotationService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mRemoteCallbacks = new ArrayList<>();
        mOrientation = Orientation.getInstance(this );
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Stub implementation for Remote service
     */
    IAIDLRotationInterface.Stub mBinder = new IAIDLRotationInterface.Stub() {

        @Override
        public void getCoordinates(IAIDLRotationCallback callback) throws RemoteException {
            mRemoteCallbacks.add(callback);
            mOrientation.startListening(MyRotationService.this);
        }

        @Override
        public void stopCoordinates(IAIDLRotationCallback callback) throws RemoteException {
            mRemoteCallbacks.remove(callback);
            mOrientation.stopListening();
        }
    };


    @Override
    public void onOrientationChanged(float pitch, float roll) {

        int callbackIndex = mRemoteCallbacks.size() - 1;
        OrientationDetails orientationDetails = new OrientationDetails();
        orientationDetails.setPitch(String.valueOf(pitch));
        orientationDetails.setRoll(String.valueOf(roll));

        try {
            mRemoteCallbacks.get(callbackIndex).getRotationDetails(orientationDetails);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOrientation.stopListening();
    }
}