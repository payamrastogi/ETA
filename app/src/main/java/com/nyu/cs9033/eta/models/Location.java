package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by payamrastogi on 2/27/16.
 */
public class Location implements Parcelable
{
    private String name;
    private String longitude;
    private String latitude;
    /**
     * Parcelable creator. Do not modify this function.
     */
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel p) {
            return new Person(p);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {

    }

    public Location(Parcel in)
    {

    }

    public Location(String name)
    {
        this.name = name;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLongitude()
    {
        return this.longitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLatitude()
    {
        return this.latitude;
    }
}
