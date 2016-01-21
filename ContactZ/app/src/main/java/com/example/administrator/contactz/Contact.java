package com.example.administrator.contactz;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 1/7/2016.
 */
public class Contact implements Parcelable{
    private int id;
    private String name;
    private String phoneNumber;
    private int favorite = 1;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact(int id, String name, String phoneNumber) {
        this.id = id;

        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Contact(){}

    public Contact(int id, String name, String phoneNumber, int favorite) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }




    public String toString()
    {
        return name+"--"+phoneNumber+"";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeInt(favorite);
    }

    public static final Parcelable.Creator<Contact> CREATOR
            = new Parcelable.Creator<Contact>() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[0];
        }
    };

        private Contact(Parcel in) {
            id = in.readInt();
            name = in.readString();
            phoneNumber = in.readString();
            favorite = in.readInt();
        }
    }
