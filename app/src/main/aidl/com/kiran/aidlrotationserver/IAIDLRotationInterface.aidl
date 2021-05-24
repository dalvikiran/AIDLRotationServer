// IAIDLRotationInterface.aidl
package com.kiran.aidlrotationserver;
import com.kiran.aidlrotationserver.IAIDLRotationCallback;

// Declare any non-default types here with import statements

interface IAIDLRotationInterface {
   void getCoordinates(IAIDLRotationCallback callback);
   void stopCoordinates(IAIDLRotationCallback callback);
}