package com.demo.medical.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/12/2016 12 November.
 * Everything is possible in programming.
 */

public class Patient implements Parcelable {

    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel source) {
            return new Patient(source);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
    private String Id;
    private String doctor_id;
    private String name;
    private String disease;
    private boolean admit;
    private String condition;
    private long admitDate;
    private long dischargeDate;

    public Patient() {
    }

    public Patient(String id, String doctor_id, String name, String disease, boolean admit, String condition, long admitDate, long dischargeDate) {
        Id = id;
        this.doctor_id = doctor_id;
        this.name = name;
        this.disease = disease;
        this.admit = admit;
        this.condition = condition;
        this.admitDate = admitDate;
        this.dischargeDate = dischargeDate;
    }

    protected Patient(Parcel in) {
        this.Id = in.readString();
        this.doctor_id = in.readString();
        this.name = in.readString();
        this.disease = in.readString();
        this.admit = in.readByte() != 0;
        this.condition = in.readString();
        this.admitDate = in.readLong();
        this.dischargeDate = in.readLong();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public boolean isAdmit() {
        return admit;
    }

    public void setAdmit(boolean admit) {
        this.admit = admit;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(long admitDate) {
        this.admitDate = admitDate;
    }

    public long getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(long dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.doctor_id);
        dest.writeString(this.name);
        dest.writeString(this.disease);
        dest.writeByte(this.admit ? (byte) 1 : (byte) 0);
        dest.writeString(this.condition);
        dest.writeLong(this.admitDate);
        dest.writeLong(this.dischargeDate);
    }
}
