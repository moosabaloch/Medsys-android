package com.demo.medical.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/12/2016 12 November.
 * Everything is possible in programming.
 */

public class AppUser implements Parcelable {

    private String userID;
    private String userName;
    private String email;
    private ArrayList<String> patientIds;
    private ArrayList<String> specialization;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(ArrayList<String> patientIds) {
        this.patientIds = patientIds;
    }

    public ArrayList<String> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(ArrayList<String> specialization) {
        this.specialization = specialization;
    }

    public AppUser() {
    }

    public AppUser(String userID, String userName, String email, ArrayList<String> patientIds, ArrayList<String> specialization) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.patientIds = patientIds;
        this.specialization = specialization;
    }
    /*

            {
                "_id":"5826469fc5e53f3b975e09bd",
                -//"uid":"101",
                "name":"Sushan Kumar",
                "email":"shahid@gmail.com",
                -//"password":"12345",
                -//"is_admin":false,
                -//"__v":0,
                "patients":[],
                "specialization":[]
            }

          */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userID);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeStringList(this.patientIds);
        dest.writeStringList(this.specialization);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", patientIds=" + patientIds.toString() +
                ", specialization=" + specialization.toString() +
                '}';
    }

    protected AppUser(Parcel in) {
        this.userID = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.patientIds = in.createStringArrayList();
        this.specialization = in.createStringArrayList();
    }

    public static final Parcelable.Creator<AppUser> CREATOR = new Parcelable.Creator<AppUser>() {
        @Override
        public AppUser createFromParcel(Parcel source) {
            return new AppUser(source);
        }

        @Override
        public AppUser[] newArray(int size) {
            return new AppUser[size];
        }
    };
}
