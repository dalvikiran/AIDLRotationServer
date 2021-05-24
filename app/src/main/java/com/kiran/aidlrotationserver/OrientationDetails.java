package com.kiran.aidlrotationserver;

import android.os.Parcel;
import android.os.Parcelable;

public class OrientationDetails implements Parcelable {
    private String pitch;
    private String roll;

    public OrientationDetails(){}

    protected OrientationDetails(Parcel in) {
        pitch = in.readString();
        roll = in.readString();
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public static final Creator<OrientationDetails> CREATOR = new Creator<OrientationDetails>() {
        @Override
        public OrientationDetails createFromParcel(Parcel in) {
            return new OrientationDetails(in);
        }

        @Override
        public OrientationDetails[] newArray(int size) {
            return new OrientationDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pitch);
        dest.writeString(roll);
    }
}
