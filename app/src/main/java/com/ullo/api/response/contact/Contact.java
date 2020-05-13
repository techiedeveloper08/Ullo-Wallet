package com.ullo.api.response.contact;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contact")
public class Contact implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public Integer pid;


    @SerializedName("user_id")
    public String userId;
    @SerializedName("full_name")
    public String fullName;
    @SerializedName("image")
    public String image;
    @SerializedName("country_code")
    public String countryCode;
    @SerializedName("phone_number")
    public String phoneNumber;


    public final static Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return (new Contact[size]);
        }

    };

    protected Contact(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.countryCode = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Contact() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(fullName);
        dest.writeValue(image);
        dest.writeValue(countryCode);
        dest.writeValue(phoneNumber);
    }

    public int describeContents() {
        return 0;
    }

}