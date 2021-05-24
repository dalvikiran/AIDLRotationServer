// IAIDLRotationCallback.aidl
package com.kiran.aidlrotationserver;
import com.kiran.aidlrotationserver.OrientationDetails;

// Declare any non-default types here with import statements

interface IAIDLRotationCallback {
  void getRotationDetails(in OrientationDetails orientation);
}