package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by payamrastogi on 2/27/16.
 */
public class Location implements Parcelable
{
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    /**
     * Parcelable creator. Do not modify this function.
     */
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel p) {
            return new Location(p);
        }

        public Location[] newArray(int size) {
            return new Location[size];
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

    public Location()
    {
        //no argument
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

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
